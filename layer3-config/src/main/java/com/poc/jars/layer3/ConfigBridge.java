package com.poc.jars.layer3;

import com.poc.jars.layer1.PublisherService;
import com.poc.jars.layer2.DbService;

/**
 * Layer 3 JAR: configuration bridge.
 * Receives publisher + DB values from Layer 4 and forwards them
 * to Layer 1 and Layer 2 respectively.
 */
public class ConfigBridge {

    private final PublisherService publisherService = new PublisherService();
    private final DbService dbService = new DbService();

    private String publisherConfiguration;
    private String dbConnection;

    /**
     * Accept configuration values from Layer 4 (sourced from env YAML).
     */
    public void configure(String publisherConfiguration, String dbConnection) {
        this.publisherConfiguration = publisherConfiguration;
        this.dbConnection = dbConnection;
        System.out.println("[Layer3] Received publisher configuration: <" + publisherConfiguration + ">");
        System.out.println("[Layer3] Received db connection: <" + dbConnection + ">");
    }

    /**
     * Pass Layer 1 value to Layer 1 JAR and publish.
     */
    public String connectWithLayer1() {
        requireConfigured();
        System.out.println("[Layer3] Passing layer1 value to Layer1 JAR: <" + publisherConfiguration + ">");
        return publisherService.generateAndPublish(publisherConfiguration);
    }

    /**
     * Pass Layer 2 value to Layer 2 JAR and compare.
     */
    public boolean connectWithDbLayer() {
        requireConfigured();
        System.out.println("[Layer3] Passing layer2 value to Layer2 JAR: <" + dbConnection + ">");
        return dbService.compare(dbConnection);
    }

    private void requireConfigured() {
        if (publisherConfiguration == null || dbConnection == null) {
            throw new IllegalStateException("ConfigBridge not configured. Call configure() first.");
        }
    }
}
