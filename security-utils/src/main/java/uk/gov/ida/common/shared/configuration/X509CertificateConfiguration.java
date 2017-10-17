package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.security.PublicKey;

@JsonDeserialize(using=X509CertificateDeserializer.class)
@JsonTypeName("x509")
public class X509CertificateConfiguration implements DeserializablePublicKeyConfiguration {
    private PublicKey publicKey;
    private String cert;

    @Valid
    @NotNull
    @Size(min = 1)
    @JsonProperty("x509")
    private String x509;

    @Valid
    @NotNull
    @Size(min = 1)
    @JsonProperty
    private String name;

    public X509CertificateConfiguration(PublicKey publicKey, String x509, String name, String cert) {
        this.publicKey = publicKey;
        this.name = name;
        this.cert = cert;
        this.x509 = x509;
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

    public String getX509() {
        return x509;
    }
}