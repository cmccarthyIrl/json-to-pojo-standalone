# JSON 2 POJO Converter

> A modern standalone JavaFX application for converting JSON/YAML to Java/Scala POJOs with configurable annotations

## Features

- 🔄 Convert JSON, JSON Schema, YAML, or YAML Schema to POJOs
- ☕ Generate Java or Scala classes
- 🏷️ Support for multiple annotation styles (Jackson 1.x/2.x, Gson, Moshi, or None)
- ✨ Auto-generates builders, toString(), hashCode(), and equals() methods
- 🎨 Clean, intuitive user interface
- ⚡ Real-time validation and error feedback

## User Interface

<img src="https://github.com/cmccarthyIrl/json-to-pojo-standalone/blob/master/src/main/resources/demo/gui.png" height="400px"/>

## Requirements

- Java 17 or higher
- Maven 3.6+

## Building the Application

### Cross-Platform Builds

Build platform-specific executable JARs for your target platform:

**For Mac (Apple Silicon - M1/M2/M3):**
```bash
mvn clean package -Pbuild-mac-aarch64
```

**For Mac (Intel):**
```bash
mvn clean package -Pbuild-mac
```

**For Windows:**
```bash
mvn clean package -Pbuild-windows
```

**For Linux:**
```bash
mvn clean package -Pbuild-linux
```

The platform-specific JAR will be created in the `target` folder as:
- `json-to-pojo-mac-aarch64.jar` (Mac Apple Silicon)
- `json-to-pojo-mac.jar` (Mac Intel)
- `json-to-pojo-windows.jar` (Windows)
- `json-to-pojo-linux.jar` (Linux)

### Auto-Detect Build

To build for your current platform automatically:

```bash
mvn clean install
```

## Running the Application

### Option 1: Run the Platform-Specific JAR

```bash
# Mac Apple Silicon
java -jar target/json-to-pojo-mac-aarch64.jar

# Mac Intel
java -jar target/json-to-pojo-mac.jar

# Windows
java -jar target/json-to-pojo-windows.jar

# Linux
java -jar target/json-to-pojo-linux.jar
```

### Option 2: Run with Maven (Recommended - Auto-detects platform)

```bash
mvn javafx:run
```

## Usage

1. **Select Configuration Options:**
   - **Target Language:** Choose Java or Scala
   - **Source Language:** Choose JSON, JSON Schema, YAML Schema, or YAML
   - **Annotation Style:** Choose Jackson 1.x, Jackson 2.x, Gson, Moshi, or None

2. **Input Your Data:**
   - Paste your JSON/YAML in the left text area

3. **Generate POJOs:**
   - Click the "Generate POJO" button
   - View the generated classes in the right text area

# Troubleshooting

- Execute the following commands to resolve any dependency issues
    1. `cd ~/install directory path/json-to-pojo-standalone`
    2. `mvn clean install`

# Contributing

Spotted a mistake? Questions? Suggestions?

[Open an Issue](https://github.com/cmccarthyIrl/spring-cucumber-test-harness/issues)

#  JSON2POJO Repository

[joelittlejohn - JSON2Pojo](https://github.com/joelittlejohn/jsonschema2pojo)


