package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Base64;

public class EncodedCertificateDeserializer extends JsonDeserializer<EncodedCertificateConfiguration> {
    @Override
    public EncodedCertificateConfiguration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // Setting the Codec explicitly is needed when this executes with the YAMLParser
        // for example, when our Dropwizard apps start. The codec doesn't need to be set
        // when the JsonParser implementation is used.
        p.setCodec(new ObjectMapper());
        JsonNode node = p.getCodec().readTree(p);

        JsonNode certNode = node.get("cert");
        Preconditions.checkState(certNode != null, "cert not specified.");
        String encodedCert = certNode.asText();

        JsonNode nameNode = node.get("name");
        Preconditions.checkState(nameNode != null, "name not specified.");
        String name = nameNode.asText();

        String cert = new String(Base64.getDecoder().decode(encodedCert));
        CertificateFactory certificateFactory = getCertificateFactory();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(cert.getBytes(StandardCharsets.UTF_8));
        Certificate certificate = null;
        try {
            certificate = certificateFactory.generateCertificate(byteArrayInputStream);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }

        PublicKey publicKey = certificate.getPublicKey();

        return new EncodedCertificateConfiguration(publicKey, encodedCert, name, cert);
    }

    private CertificateFactory getCertificateFactory() {
        try {
            return CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        }
    }
}
