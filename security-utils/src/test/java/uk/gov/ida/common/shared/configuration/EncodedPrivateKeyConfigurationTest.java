package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.nio.file.Files;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsInstanceOf.any;

public class EncodedPrivateKeyConfigurationTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_loadPrivateKeyFromJSON() throws Exception {
        String path = Resources.getResource("private_key.pk8").getFile();
        byte[] key = Files.readAllBytes(new File(path).toPath());
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String jsonConfig = "{\"key\": \"" + encodedKey + "\"}";
        EncodedPrivateKeyConfiguration configuration = objectMapper.readValue(jsonConfig, EncodedPrivateKeyConfiguration.class);
        assertThat(configuration.getPrivateKey().getAlgorithm()).isEqualTo("RSA");
    }

    @Test
    public void should_ThrowFooExceptionWhenKeyIsNotAPrivateKey() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectCause(any(InvalidKeySpecException.class));

        String key = "";
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false).readValue("{\"key\": \"" + key + "\"}", EncodedPrivateKeyConfiguration.class);
    }

    @Test(expected = EncodedPrivateKeyDeserializer.PrivateKeyNotSpecifiedException.class)
    public void should_throwAnExceptionWhenIncorrectFieldSpecified() throws Exception {
        objectMapper.readValue("{\"privateKeyFoo\": \"" + "foobar" + "\"}", EncodedPrivateKeyConfiguration.class);
    }
}