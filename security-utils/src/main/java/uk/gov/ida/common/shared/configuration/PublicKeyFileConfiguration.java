package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.PublicKey;

@JsonDeserialize(using=PublicKeyDeserializer.class)
@JsonTypeName("file")
public class PublicKeyFileConfiguration implements DeserializablePublicKeyConfiguration {
    private PublicKey publicKey;
    private String cert;

    @Valid
    @NotNull
    @Size(min = 1)
    @JsonProperty
    private String certFile;

    @Valid
    @NotNull
    @Size(min = 1)
    @JsonProperty
    private String name;

    public PublicKeyFileConfiguration(PublicKey publicKey, String certFile, String name, String cert) {
        this.publicKey = publicKey;
        this.certFile = certFile;
        this.name = name;
        this.cert = cert;
    }

    @Override
    public PublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getCert() {
        return cert;
    }

    public String getCertFile() {
        return certFile;
    }
}