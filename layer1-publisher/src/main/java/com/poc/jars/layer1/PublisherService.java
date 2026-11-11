package com.poc.jars.layer1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.time.Instant;
import java.util.UUID;

/**
 * Layer 1 JAR: generates dummy JSON test data and "publishes" it
 * using the publisher configuration received from Layer 3.
 */
public class PublisherService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Generate dummy JSON and publish to the given publisher configuration.
     *
     * @param publisherConfiguration e.g. layer1_qa_connection
     * @return the generated JSON payload as a string
     */
    public String generateAndPublish(String publisherConfiguration) {
        try {
            ObjectNode payload = objectMapper.createObjectNode();
            payload.put("id", UUID.randomUUID().toString());
            payload.put("event", "order.created");
            payload.put("amount", 99.50);
            payload.put("currency", "USD");
            payload.put("timestamp", Instant.now().toString());
            payload.put("source", "layer1-publisher");

            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(payload);

            System.out.println("[Layer1] Generated dummy JSON:");
            System.out.println(json);
            System.out.println("[Layer1] Data published to <" + publisherConfiguration + ">");

            return json;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate/publish test data", e);
        }
    }
}
