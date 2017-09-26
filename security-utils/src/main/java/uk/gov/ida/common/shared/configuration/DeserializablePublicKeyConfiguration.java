package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.dropwizard.jackson.Discoverable;

import java.security.PublicKey;

@JsonDeserialize(using=DeserializablePublicKeyConfigurationDeserializer.class)
public interface DeserializablePublicKeyConfiguration extends Discoverable {
    PublicKey getPublicKey();

    String getName();

    String getCert();
}
