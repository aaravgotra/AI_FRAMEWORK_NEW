package com.poc.jars.team1;

import com.poc.jars.layer4.RobotKeywords;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Team1 entry point.
 * Depends only on layer4-robot in the POM.
 * YAML values flow: Team1 -> Layer4 -> Layer3 -> Layer1 / Layer2
 */
public class Team1Demo {

    public static void main(String[] args) throws Exception {
        String env = args.length > 0 ? args[0] : "qa";
        Path yaml = resolveYamlPath(args.length > 1 ? args[1] : null);

        System.out.println("========== Team1 POC start (env=" + env + ") ==========");
        System.out.println("[Team1] Using YAML: " + yaml.toAbsolutePath());

        RobotKeywords keywords = new RobotKeywords();
        keywords.initializeForEnvironment(yaml.toString(), env);

        System.out.println("--- running keyword: Connect With Layer 1 ---");
        keywords.connectWithLayer1();

        System.out.println("--- running keyword: Connect With DB Layer ---");
        keywords.connectWithDbLayer();

        System.out.println("========== Team1 POC done ==========");
    }

    private static Path resolveYamlPath(String explicitPath) throws Exception {
        if (explicitPath != null) {
            return Path.of(explicitPath);
        }

        Path local = Path.of("src/main/resources/env-config.yaml");
        if (Files.exists(local)) {
            return local.toAbsolutePath();
        }

        // When run via exec:java from reactor root, load from classpath and copy to temp.
        try (InputStream in = Team1Demo.class.getClassLoader().getResourceAsStream("env-config.yaml")) {
            if (in == null) {
                throw new IllegalStateException("env-config.yaml not found on classpath or filesystem");
            }
            Path temp = Files.createTempFile("env-config-", ".yaml");
            Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);
            temp.toFile().deleteOnExit();
            return temp;
        }
    }
}
