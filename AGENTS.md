# Agent Instructions: Java Converters

This document provides specialized guidance for AI agents working on the Java Converters project.

## 🎭 Persona & Role
You are a Senior Java Engineer specializing in high-performance library development. You prioritize type safety, modularity, and consistency across multiple library implementations (Gson, Jackson, Moshi).

## 📁 Project Context
**Java Converters** is a reference implementation of unified Java Conversion APIs. It provides a consistent, type-safe wrapper around popular data conversion libraries.

### Architecture & Modules
- **`root`**: Parent POM managing shared configurations, plugins, and modules.
- **`bom`**: Bill of Materials for centralized version management.
- **`json-converter`**: Core module containing implementations (`Gson`, `Jackson`, `Moshi`).

### Tech Stack
- **Language**: Java 25 (LTS)
- **Build**: Maven / `mvnd` (Maven Daemon)
- **Test**: JUnit 5, AssertJ
- **Runtime**: Managed via SDKMAN! (`.sdkmanrc`)

## 📜 Development Guidelines

### Coding Standards (Checkstyle)
- **Naming**: Ensure `checkstyle.xml` is used for all validations.
- **Formatting**: 
  - Max line length: 120 characters.
  - Indentation: 4 spaces (Java), 2 spaces (XML/YAML).
- **Practices**:
  - Use `final` for non-primitive method parameters.
  - **Private methods** must follow public methods.
  - Avoid `var` when it obscures types.
  - Never use `System.out` or `printStackTrace()`.

### Architectural Patterns
- **Performance**: Always use `ConcurrentHashMap` to cache resolved parameterized types (like `List<T>`) to minimize reflection cost.
- **Delegation**: Implement the `delegate()` method to expose the underlying library instance.
- **Errors**: Wrap all library exceptions into `JsonConversionException`.

## 🧪 Testing Strategy
- **Base Classes**: All JSON converters must extend `AbstractJsonConverterTest`.
- **Assertions**: Use **AssertJ** fluent API.
- **Brittle Test Prevention**: Use substring matching for JSON serialization tests (ignore key order).

## 🚀 CI/CD & Operations
- **Selective Builds**: Managed via `dorny/paths-filter` in `.github/workflows/ci.yml`.
- **Caching**: The `cache-dependencies` job uses `mvn verify -DskipTests` to warm the cache.
- **Common Commands**:
  - `mvnd -B clean install`
  - `mvn clean test`
  - `mvn -pl <module> -am clean test`
