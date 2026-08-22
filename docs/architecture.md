# Framework Architecture

## Overview
This framework is built using industry best practices for REST API automation testing. It combines **REST Assured**, **TestNG**, **Cucumber BDD**, **Jackson**, **Allure Reporting**, and **SLF4J**.

## Architecture Diagram

```
+-------------------------------------------------------------------+
|                        Test Layer                                 |
|  +---------------------------+   +-----------------------------+  |
|  |     TestNG API Tests      |   |    Cucumber BDD Scenarios   |  |
|  |  (User, Post, Comment,    |   |     (Feature Files &        |  |
|  |       Negative)           |   |     Step Definitions)       |  |
|  +---------------------------+   +-----------------------------+  |
+---------------------------------+---------------------------------+
                                  |
                                  v
+-------------------------------------------------------------------+
|                       Client & Config Layer                       |
|  +---------------------------+   +-----------------------------+  |
|  |        ApiClient          |   |          ApiConfig          |  |
|  |  (RequestSpecBuilder,     |   |   (System Props, Env Vars,  |  |
|  |   Allure Filter, Logs)    |   |     config.properties)      |  |
|  +---------------------------+   +-----------------------------+  |
+---------------------------------+---------------------------------+
                                  |
                                  v
+-------------------------------------------------------------------+
|                      Models & Validation Layer                    |
|  +---------------------------+   +-----------------------------+  |
|  |        POJO Models        |   |    JSON Schema Validator    |  |
|  |  (User, Post, Comment,    |   |   (user-schema, post-schema,|  |
|  |   Address, Geo, Company)  |   |        comment-schema)      |  |
|  +---------------------------+   +-----------------------------+  |
+---------------------------------+---------------------------------+
                                  |
                                  v
+-------------------------------------------------------------------+
|                       Reporting & CI/CD Layer                     |
|  +---------------------------+   +-----------------------------+  |
|  |      Allure Reports       |   |       GitHub Actions        |  |
|  |  (Attachments, Visuals)   |   |        CI Pipeline          |  |
|  +---------------------------+   +-----------------------------+  |
+-------------------------------------------------------------------+
```

## Key Components

1. **`ApiClient`**: Centralized HTTP client providing reusable methods (`get`, `post`, `put`, `patch`, `delete`), base URI resolution, common headers, logging, and Allure reporting filters.
2. **`ApiConfig`**: Multi-tiered configuration system resolving base URL and environment parameters across System Properties, Environment Variables, and `config.properties`.
3. **POJO Models**: Strongly typed data models using Jackson annotations for clean serialization and deserialization.
4. **JSON Schema Validation**: Strict schema enforcement against JSON draft-07 schemas using `matchesJsonSchemaInClasspath`.
5. **TestNG & Cucumber BDD**: Hybrid approach supporting both structured technical test cases and human-readable Gherkin feature files.
