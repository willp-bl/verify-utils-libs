package uk.gov.ida.common.shared.configuration;

import com.google.common.io.Files;
import uk.gov.ida.common.shared.security.PrivateKeyFactory;

import java.io.File;
import java.io.IOException;
import java.security.PrivateKey;

public class PrivateKeyConfigurationBuilder {
    private String keyUri = "private key";

    public static PrivateKeyConfigurationBuilder aPrivateKeyConfiguration() {
        return new PrivateKeyConfigurationBuilder();
    }
    public PrivateKeyConfigurationBuilder withKeyUri(String uri) {
        this.keyUri = uri;
        return this;
    }

    public PrivateKeyFileConfiguration build() throws IOException {
        PrivateKey privateKey = new PrivateKeyFactory().createPrivateKey(Files.toByteArray(new File(keyUri)));
        return new TestPrivateKeyFileConfiguration(privateKey, keyUri);
    }

    private static class TestPrivateKeyFileConfiguration extends PrivateKeyFileConfiguration {

        public TestPrivateKeyFileConfiguration(PrivateKey privateKey, String keyUri) {
            super(privateKey, keyUri);
        }
    }
}
