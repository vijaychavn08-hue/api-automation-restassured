# Test Execution Guide

## Prerequisites
- Java Development Kit (JDK) 17 or higher
- Apache Maven 3.8+

## Commands

### 1. Execute All Tests (TestNG + Cucumber)
```bash
mvn clean test
```

### 2. Execute Smoke Suite Only
```bash
mvn clean test -Dgroups=smoke
```

### 3. Execute Regression Suite Only
```bash
mvn clean test -Dgroups=regression
```

### 4. Override Base URL via Command Line
```bash
mvn clean test -DbaseUrl=https://jsonplaceholder.typicode.com
```

### 5. Generate and Serve Allure HTML Report
```bash
mvn allure:serve
```

### 6. Generate Standalone Allure Report
```bash
mvn allure:report
```
The report will be generated at `target/site/allure-maven-plugin/index.html`.
