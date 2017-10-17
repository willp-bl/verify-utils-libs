package uk.gov.ida.common.shared.configuration;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class DeserializablePublicKeyConfigurationDeserializer extends JsonDeserializer<DeserializablePublicKeyConfiguration> {
    @Override
    public DeserializablePublicKeyConfiguration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // Setting the Codec explicitly is needed when this executes with the YAMLParser
        // for example, when our Dropwizard apps start. The codec doesn't need to be set
        // when the JsonParser implementation is used.
        p.setCodec(new ObjectMapper());
        JsonNode node = p.getCodec().readTree(p);

        JsonNode certFileNode = node.get("certFile");
        if(certFileNode != null) {
            return p.getCodec().treeToValue(node, PublicKeyFileConfiguration.class);
        }
        JsonNode x509Node = node.get("x509");
        if(x509Node != null) {
            return p.getCodec().treeToValue(node, X509CertificateConfiguration.class);
        }
        return p.getCodec().treeToValue(node, EncodedCertificateConfiguration.class);
    }
}
