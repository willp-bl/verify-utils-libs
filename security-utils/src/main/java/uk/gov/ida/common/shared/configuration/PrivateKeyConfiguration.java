package uk.gov.ida.common.shared.configuration;

import io.dropwizard.jackson.Discoverable;
import java.security.PrivateKey;

public interface PrivateKeyConfiguration extends Discoverable {
    PrivateKey getPrivateKey();
}
