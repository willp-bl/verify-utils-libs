package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.nio.file.NoSuchFileException;
import java.security.cert.CertificateException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;

public class PublicKeyFileConfigurationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_loadPublicKeyFromJSON() throws Exception {
        String path = getClass().getClassLoader().getResource("public_key.crt").getPath();
        PublicKeyFileConfiguration publicKeyConfiguration = objectMapper.readValue("{\"certFile\": \"" + path + "\", \"name\": \"someId\"}", PublicKeyFileConfiguration.class);

        assertThat(publicKeyConfiguration.getPublicKey().getAlgorithm()).isEqualTo("RSA");
    }

    @Test(expected = NoSuchFileException.class)
    public void should_ThrowExceptionWhenFileDoesNotExist() throws Exception {
        objectMapper.readValue("{\"certFile\": \"/foo/bar\", \"name\": \"someId\"}", PublicKeyFileConfiguration.class);
    }

    @Test
    public void should_ThrowExceptionWhenFileDoesNotContainAPublicKey() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectCause(any(CertificateException.class));

        String path = getClass().getClassLoader().getResource("empty_file").getPath();
        objectMapper.readValue("{\"certFile\": \"" + path + "\", \"name\": \"someId\"}", PublicKeyFileConfiguration.class);
    }

    @Test(expected = IllegalStateException.class)
    public void should_ThrowExceptionWhenIncorrectKeySpecified() throws Exception {
        String path = getClass().getClassLoader().getResource("empty_file").getPath();
        objectMapper.readValue("{\"certFileFoo\": \"" + path + "\", \"name\": \"someId\"}", PublicKeyFileConfiguration.class);
    }
}