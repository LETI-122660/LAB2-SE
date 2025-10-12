# App README

## Project Structure

The sources of your App have the following structure:

```
src
├── main/frontend
│   └── themes
│       └── default
│           ├── styles.css
│           └── theme.json
├── main/java
│   └── [application package]
│       ├── base
│       │   └── ui
│       │       ├── component
│       │       │   └── ViewToolbar.java
│       │       ├── MainErrorHandler.java
│       │       └── MainLayout.java
│       ├── examplefeature
│       │   ├── ui
│       │   │   └── TaskListView.java
│       │   ├── Task.java
│       │   ├── TaskRepository.java
│       │   └── TaskService.java                
│       └── Application.java       
└── test/java
    └── [application package]
        └── examplefeature
           └── TaskServiceTest.java                 
```

The main entry point into the application is `Application.java`. This class contains the `main()` method that start up 
the Spring Boot application.

The skeleton follows a *feature-based package structure*, organizing code by *functional units* rather than traditional 
architectural layers. It includes two feature packages: `base` and `examplefeature`.

* The `base` package contains classes meant for reuse across different features, either through composition or 
  inheritance. You can use them as-is, tweak them to your needs, or remove them.
* The `examplefeature` package is an example feature package that demonstrates the structure. It represents a 
  *self-contained unit of functionality*, including UI components, business logic, data access, and an integration test.
  Once you create your own features, *you'll remove this package*.

The `src/main/frontend` directory contains an empty theme called `default`, based on the Lumo theme. It is activated in
the `Application` class, using the `@Theme` annotation.

## Starting in Development Mode

To start the application in development mode, import it into your IDE and run the `Application` class. 
You can also start the application from the command line by running: 

```bash
./mvnw
```

## Building for Production

To build the application in production mode, run:

```bash
./mvnw -Pproduction package
```

To build a Docker image, run:

```bash
docker build -t my-application:latest .
```

If you use commercial components, pass the license key as a build secret:

```bash
docker build --secret id=proKey,src=$HOME/.vaadin/proKey .
```

## Getting Started

The [Getting Started](https://vaadin.com/docs/latest/getting-started) guide will quickly familiarize you with your new
App implementation. You'll learn how to set up your development environment, understand the project 
structure, and find resources to help you add muscles to your skeleton — transforming it into a fully-featured 
application.

## CI/CD Pipeline (GitHub Actions)

This project includes a GitHub Actions workflow that automatically builds and publishes the `.jar` artifact whenever code is pushed to the `main` branch.

### Pipeline Overview

- **Trigger:** Runs automatically on every `push` to the `main` branch.
- **Main tasks:**
    1. Check out the repository code.
    2. Set up JDK 21 with Maven cache.
    3. Build the project using `mvn clean package`.
    4. Copy the generated `.jar` file to the repository root in the workflow runner.
    5. Upload the `.jar` file as a build artifact, available for download on GitHub.

### Example Workflow (`.github/workflows/build.yml`)

```yaml
name: Java CI - Build and Package

on:
  push:
    branches:
      - main

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout repository
        uses: actions/checkout@v4

      - name: Set up JDK 21
        uses: actions/setup-java@v5
        with:
          java-version: '21'
          distribution: 'temurin'
          cache: 'maven'

      - name: Build with Maven
        run: mvn clean package

      - name: Copy jar to repository root
        run: cp target/*.jar .

      - name: Upload jar artifact
        uses: actions/upload-artifact@v4
        with:
          name: application-jar
          path: '*.jar'
```
Accessing the Build Artifact

After each successful workflow run, the .jar file will be available for download from the Actions tab:

👉 [Download latest build artifact](https://github.com/LETI-122660/LAB2-SE/actions/workflows/build.yml)

💡 Note: The .jar is available inl the Actions interface as a downloadable artifact, but it is not automatically committed to the main branch.