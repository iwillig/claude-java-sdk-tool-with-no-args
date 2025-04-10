# Anthropic Java SDK Error example.

This is an example of an error we are seeing when we define a tool without any arguments.

## Prerequisites

- Java 17 or higher
- Maven
- Anthropic API Key

## Setup

1. Set your Anthropic API key as an environment variable:

```bash
export ANTHROPIC_API_KEY=your_api_key_here
```

## Building the Project

```bash
mvn clean compile
```

## Running the Application

```bash
 mvn exec:java -Dexec.mainClass="com.example.App"
```

## Project Structure

- `src/main/java/com/example/App.java`: Main application that demonstrates how to use the Anthropic Java SDK
- `pom.xml`: Maven configuration file with dependencies
