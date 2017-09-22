package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.dropwizard.jackson.Discoverable;
import java.security.PrivateKey;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public interface PrivateKeyConfiguration extends Discoverable {
    PrivateKey getPrivateKey();
}
