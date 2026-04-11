# Java Converters

Reference implementations of Java Conversion APIs. This project provides unified, easy-to-use wrapping converters for 
some of the most popular data conversion libraries in the Java ecosystem.

> [!WARNING]
> Under active development 🚧

## Features

- **Unified API**: Consistent converter interfaces for various data formats (JSON, XML, CSV), making it easy to swap out underlying libraries without changing your business logic.
- **Type-Safe Collections**: Built-in support and optimized type resolution for converting data arrays/objects directly into `List<T>`, `Set<T>`, and `Map<K, V>`.
- **Performance Optimized**: Implementations utilize `ConcurrentHashMap` to cache resolved parameterized types (like `List<MyObject>`), significantly reducing the overhead of reflection during repetitive conversions.
- **Library Delegation**: Need library-specific features? You can easily access the underlying configured library instance using the `delegate()` method.

## JSON conversions

Currently, the `json-converter` module provides three reference implementations:

### 1. Gson (`GsonJsonConverter`)
Backed by Google's `gson` library. It efficiently caches `TypeToken` parameterizations for collections to ensure high performance.

### 2. Jackson (`JacksonJsonConverter`)
Backed by `tools.jackson.core:jackson-databind` (Jackson 3.x). It utilizes `TypeFactory` to construct and handle collection-like and map-like types seamlessly.

### 3. Moshi (`MoshiJsonConverter`)
Backed by Square's `moshi` library. It maintains an internal cache for both resolved `Type` parameterizations and `JsonAdapter` instances to provide extremely fast serialization and deserialization.

## Usage

### Add to your project
#### Import the bom
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.petromirdzhunev</groupId>
            <artifactId>java-converters-bom</artifactId>
            <version>${java_conversions_apis_bom_version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```
replace `${java_comersions_apis_bom_version}` with the last version.

#### Add specific dependency
Once bom is in place, add the conversion API of choice
```xml
<dependencies>
    <dependency>
        <groupId>com.petromirdzhunev</groupId>
        <artifactId>json-converter</artifactId>
    </dependency>
</dependencies>
```

## Development

### Prerequisites
1. [Install SDKMAN](https://sdkman.io/install)
2. Initialize SDKMAN environment
```shell
sdk env install
```
Check [.sdkmanrc](.sdkmanrc) for all the tools installed with this command.

### Build and publish `SNAPSHOT` versions to local repository
Change the version by adding a `-SNAPSHOT` suffix in the [pom.xml](pom.xml) file and then execute:
```shell
 mvnd -B clean install
```

### Pointing to a local version
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.petromirdzhunev</groupId>
            <artifactId>java-converters-bom</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```