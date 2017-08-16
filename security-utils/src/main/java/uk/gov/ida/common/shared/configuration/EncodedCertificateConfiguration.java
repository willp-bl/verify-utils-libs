package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.PublicKey;

@JsonDeserialize(using=EncodedCertificateDeserializer.class)
@JsonTypeName("base64")
public class EncodedCertificateConfiguration implements DeserializablePublicKeyConfiguration {
    private PublicKey publicKey;
    private String cert;

    @Valid
    @NotNull
    @Size(min = 1)
    @JsonProperty("cert")
    private String encodedCert;

    @Valid
    @NotNull
    @Size(min = 1)
    @JsonProperty
    private String name;

    public EncodedCertificateConfiguration(PublicKey publicKey, String encodedCert, String name, String cert) {
        this.publicKey = publicKey;
        this.name = name;
        this.cert = cert;
        this.encodedCert = encodedCert;
    }

    @Override
    public PublicKey getPublicKey() {
        return publicKey;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCert() {
        return cert;
    }

    public String getEncodedCert() {
        return encodedCert;
    }
}