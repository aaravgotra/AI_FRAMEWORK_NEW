package com.poc.jars.layer4;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Loads env-specific layer1 / layer2 values from a YAML file.
 *
 * Expected shape:
 * qa:
 *   layer1:
 *     - layer1_qa_connection
 *   layer2:
 *     - layer2_qa_db
 */
public class EnvConfigLoader {

    @SuppressWarnings("unchecked")
    public EnvLayerValues load(Path yamlPath, String environment) {
        try (InputStream in = Files.newInputStream(yamlPath)) {
            Yaml yaml = new Yaml();
            Map<String, Object> root = yaml.load(in);

            if (root == null || !root.containsKey(environment)) {
                throw new IllegalArgumentException("Environment not found in YAML: " + environment);
            }

            Map<String, Object> env = (Map<String, Object>) root.get(environment);
            String layer1 = firstValue(env, "layer1");
            String layer2 = firstValue(env, "layer2");

            System.out.println("[Layer4] Loaded YAML for env='" + environment + "'");
            System.out.println("[Layer4] layer1 value: " + layer1);
            System.out.println("[Layer4] layer2 value: " + layer2);

            return new EnvLayerValues(layer1, layer2);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Failed to load YAML from " + yamlPath, e);
        }
    }

    @SuppressWarnings("unchecked")
    private String firstValue(Map<String, Object> env, String key) {
        Object raw = env.get(key);
        if (raw instanceof List) {
            List<Object> list = (List<Object>) raw;
            if (list.isEmpty() || list.get(0) == null) {
                throw new IllegalArgumentException("Missing value for key: " + key);
            }
            return String.valueOf(list.get(0));
        }
        if (raw == null) {
            throw new IllegalArgumentException("Missing key: " + key);
        }
        return String.valueOf(raw);
    }
}
