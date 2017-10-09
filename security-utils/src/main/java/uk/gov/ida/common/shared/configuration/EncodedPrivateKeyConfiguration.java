package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.PrivateKey;

@SuppressWarnings("unused")
@JsonDeserialize(using=EncodedPrivateKeyDeserializer.class)
public class EncodedPrivateKeyConfiguration implements PrivateKeyConfiguration {

    public EncodedPrivateKeyConfiguration(PrivateKey privateKey, String key) {
        this.privateKey = privateKey;
        this.key = key;
    }

    private PrivateKey privateKey;

    @Valid
    @NotNull
    @Size(min = 1)
    @JsonProperty
    private String key;

    public PrivateKey getPrivateKey() {
        return privateKey;
    }
}
