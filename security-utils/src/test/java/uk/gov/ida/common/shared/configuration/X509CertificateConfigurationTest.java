package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.nio.file.Files;
import java.security.cert.CertificateException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsInstanceOf.any;

public class X509CertificateConfigurationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_loadPublicKeyFromJSON() throws Exception {
        String path = Resources.getResource("public_key.crt").getFile();
        byte[] cert = Files.readAllBytes(new File(path).toPath());
        String fullCert = new String(cert);
        String strippedCert = fullCert
                .replace("-----BEGIN CERTIFICATE-----\n", "")
                .replace("\n-----END CERTIFICATE-----", "")
                .replace("\n", "")
                .trim();
        String jsonConfig = "{\"type\": \"x509\", \"x509\": \"" + strippedCert + "\", \"name\": \"someId\"}";
        X509CertificateConfiguration config = objectMapper.readValue(jsonConfig, X509CertificateConfiguration.class);

        assertThat(config.getPublicKey().getAlgorithm()).isEqualTo("RSA");
    }

    @Test
    public void should_ThrowExceptionWhenStringDoesNotContainAPublicKey() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectCause(any(CertificateException.class));
        String path = Resources.getResource("private_key.pk8").getFile();
        byte[] key = Files.readAllBytes(new File(path).toPath());
        String encodedKey = Base64.getEncoder().encodeToString(key);
        objectMapper.readValue("{\"type\": \"x509\", \"x509\": \"" + encodedKey + "\", \"name\": \"someId\"}", X509CertificateConfiguration.class);
    }

    @Test
    public void should_ThrowExceptionWhenStringIsNotBase64Encoded() throws Exception {
        thrown.expect(IllegalArgumentException.class);

        objectMapper.readValue("{\"type\": \"base64\", \"cert\": \"" + "FOOBARBAZ" + "\", \"name\": \"someId\"}", EncodedCertificateConfiguration.class);
    }

    @Test(expected = IllegalStateException.class)
    public void should_ThrowExceptionWhenIncorrectKeySpecified() throws Exception {
        String path = getClass().getClassLoader().getResource("empty_file").getPath();
        String jsonConfig = "{\"type\": \"base64\", \"certFileFoo\": \"" + path + "\", \"name\": \"someId\"}";
        objectMapper.readValue(jsonConfig, EncodedCertificateConfiguration.class);
    }
}