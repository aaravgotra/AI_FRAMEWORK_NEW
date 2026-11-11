package com.poc.jars.layer4;

/**
 * Holds layer1 (publisher) and layer2 (db) values for one environment.
 */
public class EnvLayerValues {

    private final String layer1Value;
    private final String layer2Value;

    public EnvLayerValues(String layer1Value, String layer2Value) {
        this.layer1Value = layer1Value;
        this.layer2Value = layer2Value;
    }

    public String getLayer1Value() {
        return layer1Value;
    }

    public String getLayer2Value() {
        return layer2Value;
    }
}
