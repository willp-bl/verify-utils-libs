package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.dropwizard.jackson.Discoverable;

import java.security.PrivateKey;

@JsonDeserialize(using=PrivateKeyConfigurationDeserializer.class)
public interface PrivateKeyConfiguration extends Discoverable {
    PrivateKey getPrivateKey();
}
