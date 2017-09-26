package uk.gov.ida.common.shared.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.security.spec.InvalidKeySpecException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.any;

public class PrivateKeyFileConfigurationTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void should_loadPrivateKeyFromJSON() throws Exception {
        String path = getClass().getClassLoader().getResource("private_key.pk8").getPath();
        PrivateKeyFileConfiguration privateKeyFileConfiguration = objectMapper.readValue("{\"keyFile\": \"" + path + "\"}", PrivateKeyFileConfiguration.class);

        assertThat(privateKeyFileConfiguration.getPrivateKey().getAlgorithm()).isEqualTo("RSA");
    }

    @Test(expected = FileNotFoundException.class)
    public void should_ThrowFooExceptionWhenFileDoesNotExist() throws Exception {
        objectMapper.readValue("{\"keyFile\": \"/foo/bar\"}", PrivateKeyFileConfiguration.class);
    }

    @Test
    public void should_ThrowFooExceptionWhenFileDoesNotContainAPrivateKey() throws Exception {
        thrown.expect(RuntimeException.class);
        thrown.expectCause(any(InvalidKeySpecException.class));

        String path = getClass().getClassLoader().getResource("empty_file").getPath();
        objectMapper.readValue("{\"keyFile\": \"" + path + "\"}", PrivateKeyFileConfiguration.class);
    }

    @Test(expected = PrivateKeyFileDeserializer.PrivateKeyPathNotSpecifiedException.class)
    public void should_throwAnExceptionWhenIncorrectKeySpecified() throws Exception {
        String path = getClass().getClassLoader().getResource("empty_file").getPath();
        objectMapper.readValue("{\"privateKeyFoo\": \"" + path + "\"}", PrivateKeyFileConfiguration.class);
    }
}
