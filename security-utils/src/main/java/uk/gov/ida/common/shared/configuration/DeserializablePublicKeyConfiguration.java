package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.dropwizard.jackson.Discoverable;

import java.security.PublicKey;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.*;

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type", defaultImpl = PublicKeyFileConfiguration.class)
public interface DeserializablePublicKeyConfiguration extends Discoverable {
    PublicKey getPublicKey();

    String getName();

    String getCert();
}
