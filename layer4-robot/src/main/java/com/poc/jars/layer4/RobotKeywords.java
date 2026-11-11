package com.poc.jars.layer4;

import com.poc.jars.layer3.ConfigBridge;

import java.nio.file.Path;

/**
 * Layer 4 JAR: Robot-style keyword library.
 * Team1 depends only on this JAR. It loads YAML, passes values to Layer 3,
 * and exposes keywords that connect with Layer 1 and the DB layer.
 */
public class RobotKeywords {

    private final EnvConfigLoader configLoader = new EnvConfigLoader();
    private final ConfigBridge configBridge = new ConfigBridge();
    private boolean initialized;

    /**
     * Keyword: initialize framework for an environment using the YAML file.
     * Flow: YAML -> Layer4 -> Layer3 (layer1 + layer2 values)
     */
    public void initializeForEnvironment(String yamlPath, String environment) {
        EnvLayerValues values = configLoader.load(Path.of(yamlPath), environment);

        System.out.println("[Layer4] Passing layer1 + layer2 values to Layer3");
        configBridge.configure(values.getLayer1Value(), values.getLayer2Value());
        initialized = true;
    }

    /**
     * Keyword: Connect With Layer 1
     * Layer4 -> Layer3 -> Layer1 (publisher configuration)
     */
    public String connectWithLayer1() {
        ensureInitialized();
        System.out.println("[Layer4] Keyword: Connect With Layer 1");
        return configBridge.connectWithLayer1();
    }

    /**
     * Keyword: Connect With DB Layer
     * Layer4 -> Layer3 -> Layer2 (db connection)
     */
    public boolean connectWithDbLayer() {
        ensureInitialized();
        System.out.println("[Layer4] Keyword: Connect With DB Layer");
        return configBridge.connectWithDbLayer();
    }

    private void ensureInitialized() {
        if (!initialized) {
            throw new IllegalStateException(
                    "Call initializeForEnvironment(yamlPath, environment) before using keywords.");
        }
    }
}
