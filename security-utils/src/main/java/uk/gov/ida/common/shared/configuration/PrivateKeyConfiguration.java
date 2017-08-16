package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.dropwizard.jackson.Discoverable;

import java.security.PrivateKey;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.*;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type", defaultImpl = PrivateKeyFileConfiguration.class)
public interface PrivateKeyConfiguration extends Discoverable {
    PrivateKey getPrivateKey();
}
