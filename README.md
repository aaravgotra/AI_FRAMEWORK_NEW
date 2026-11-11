# JAR Layers POC

Four independent JARs with config flowing from Team1 YAML through Layer 4 → 3 → 1/2.

## Modules

| Module | Artifact | Role |
|--------|----------|------|
| `layer1-publisher` | independent JAR | Generate dummy JSON + publish to `<publisher configuration>` |
| `layer2-db` | independent JAR | Print comparison done + `<db connection>` |
| `layer3-config` | depends on 1 + 2 | Pass publisher + DB values into Layer 1 and Layer 2 |
| `layer4-robot` | depends on 3 | Robot keywords + YAML loader |
| `team1-consumer` | depends on **4 only** | QA/DEV/UAT YAML + demo |

## Dependency chain

```
team1-consumer
  └── layer4-robot
        └── layer3-config
              ├── layer1-publisher
              └── layer2-db
```

Team1 declares **only** `layer4-robot` in its POM. Layers 3, 2, and 1 come in transitively.

## YAML → runtime flow (QA example)

```yaml
qa:
  layer1:
    - layer1_qa_connection
  layer2:
    - layer2_qa_db
```

1. Team1 calls Layer 4 keyword `initializeForEnvironment(yaml, "qa")`
2. Layer 4 reads YAML and passes `layer1_qa_connection` + `layer2_qa_db` to Layer 3
3. Keyword **Connect With Layer 1** → Layer 3 passes `layer1_qa_connection` to Layer 1 JAR
4. Keyword **Connect With DB Layer** → Layer 3 passes `layer2_qa_db` to Layer 2 JAR

## Build & run

```bash
# install all JARs into local Maven repo
mvn clean install

# run Team1 demo for QA (default)
mvn -pl team1-consumer exec:java

# run for UAT / DEV
mvn -pl team1-consumer exec:java -Dexec.args=uat
mvn -pl team1-consumer exec:java -Dexec.args=dev
```

## Team1 local POM usage

```xml
<dependency>
    <groupId>com.poc.jars</groupId>
    <artifactId>layer4-robot</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```
