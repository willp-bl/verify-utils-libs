package uk.gov.ida.common.shared.configuration;

import io.dropwizard.jackson.Discoverable;

import java.security.PublicKey;

public interface DeserializablePublicKeyConfiguration extends Discoverable {
    PublicKey getPublicKey();

    String getName();

    String getCert();
}
