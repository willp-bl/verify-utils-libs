package uk.gov.ida.common.shared.configuration;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.gov.ida.common.shared.security.PrivateKeyFactory;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.Base64;

public class EncodedPrivateKeyDeserializer extends JsonDeserializer<EncodedPrivateKeyConfiguration> {
    @Override
    public EncodedPrivateKeyConfiguration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        PrivateKeyFactory privateKeyFactory = new PrivateKeyFactory();
        // Setting the Codec explicitly is needed when this executes with the YAMLParser
        // for example, when our Dropwizard apps start. The codec doesn't need to be set
        // when the JsonParser implementation is used.
        p.setCodec(new ObjectMapper());
        JsonNode node = p.getCodec().readTree(p);
        JsonNode privateKeyNode = node.get("key");
        if (null==privateKeyNode) {
            throw new PrivateKeyNotSpecifiedException("key not specified.");
        }
        String key = privateKeyNode.asText();
        byte[] keyBytes = Base64.getDecoder().decode(key);
        PrivateKey privateKey = privateKeyFactory.createPrivateKey(keyBytes);
        return new EncodedPrivateKeyConfiguration(privateKey, key);
    }

    class PrivateKeyNotSpecifiedException extends JsonProcessingException {
        protected PrivateKeyNotSpecifiedException(String msg) {
            super(msg);
        }
    }
}
