package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.dropwizard.jackson.Discoverable;

import java.security.PublicKey;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public interface DeserializablePublicKeyConfiguration extends Discoverable {
    PublicKey getPublicKey();

    String getName();

    String getCert();
}
