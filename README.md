# Advanced REST Assured API Automation Framework

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?logo=apachemaven)
![REST Assured](https://img.shields.io/badge/REST%20Assured-5.5.0-green)
![TestNG](https://img.shields.io/badge/TestNG-7.10.2-red)
![Cucumber](https://img.shields.io/badge/Cucumber%20BDD-7.18.1-brightgreen?logo=cucumber)
![Allure](https://img.shields.io/badge/Allure-2.29.0-blue?logo=qameta)
![CI Pipeline](https://img.shields.io/badge/CI-GitHub%20Actions-2088FF?logo=githubactions)
![License](https://img.shields.io/badge/License-MIT-yellow)

Professional API Automation Framework using Java, REST Assured, TestNG, Cucumber BDD, Jackson, JSON Schema Validation, Allure, and GitHub Actions.

---

## 🌟 Framework Highlights

- **Clean Layered Architecture**: Clear separation of concern across Configuration, Client, Models (POJOs), Step Definitions, and Test Suites.
- **Hybrid Test Orchestration**: Supports both developer-friendly **TestNG** suites and business-readable **Cucumber BDD** Gherkin features.
- **Robust POJO Architecture**: Strongly typed Jackson data models (`User`, `Address`, `Geo`, `Company`, `Post`, `Comment`) for serialization and deserialization.
- **Strict JSON Schema Validation**: Validates API response payloads against JSON Draft-07 schemas (`matchesJsonSchemaInClasspath`).
- **Comprehensive API Coverage**: Full CRUD lifecycle testing (GET, POST, PUT, PATCH, DELETE), query parameter filtering, and negative 404 error testing.
- **Dynamic Configuration**: Multi-tiered configuration lookup prioritizing System Properties (`-DbaseUrl=...`), Environment Variables (`BASE_URL`), and `config.properties`.
- **Rich Allure Reporting**: Integrated `AllureRestAssured` filter capturing all request/response details, headers, payloads, attachments, and execution history.
- **Zero Logging Noise**: Configured with `slf4j-simple` for clean, professional console logs without SLF4J warnings.
- **Automated CI/CD**: Pre-configured GitHub Actions pipeline (`.github/workflows/api-tests.yml`) executing tests and capturing artifacts on every push/PR.

---

## 🏛️ Architecture

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

---

## 🛠️ Technology Stack

| Technology | Purpose | Version |
| :--- | :--- | :--- |
| **Java** | Programming Language | 17 (LTS) |
| **Apache Maven** | Build & Dependency Management | 3.8+ |
| **REST Assured** | REST API Test Automation Library | 5.5.0 |
| **TestNG** | Test Execution & Grouping Framework | 7.10.2 |
| **Cucumber BDD** | Behavior-Driven Development Framework | 7.18.1 |
| **Jackson** | JSON Data Binding & Serialization | 2.18.1 |
| **JSON Schema Validator**| Response Structure & Contract Validation | 5.5.0 |
| **Allure Framework** | Interactive Test Reporting & Attachments | 2.29.0 |
| **SLF4J Simple** | Clean Structured Logging | 2.0.16 |
| **GitHub Actions** | Automated Continuous Integration | v4 Actions |

---

## 📂 Project Structure

```
api-automation-restassured/
├── .github/
│   └── workflows/
│       └── api-tests.yml             # GitHub Actions CI pipeline
├── docs/
│   ├── architecture.md               # Architecture details
│   ├── execution.md                  # Test execution reference
│   └── ci-cd.md                      # CI/CD documentation
├── src/
│   ├── main/
│   │   ├── java/com/vijaychavan/
│   │   │   ├── client/
│   │   │   │   └── ApiClient.java    # Reusable REST Assured client
│   │   │   ├── config/
│   │   │   │   └── ApiConfig.java    # Configuration manager
│   │   │   └── models/               # Strongly typed Jackson POJOs
│   │   │       ├── Address.java
│   │   │       ├── Comment.java
│   │   │       ├── Company.java
│   │   │       ├── Geo.java
│   │   │       ├── Post.java
│   │   │       └── User.java
│   │   └── resources/
│   │       └── config.properties     # Environment configuration
│   └── test/
│       ├── java/com/vijaychavan/
│       │   ├── runner/
│       │   │   └── CucumberTestRunner.java  # Cucumber TestNG runner
│       │   ├── steps/
│       │   │   └── ApiSteps.java            # Unified Cucumber step definitions
│       │   └── tests/                       # TestNG test classes
│       │       ├── CommentApiTest.java
│       │       ├── NegativeApiTest.java
│       │       ├── PostApiTest.java
│       │       └── UserApiTest.java
│       └── resources/
│           ├── features/                    # 5 Executable Cucumber Feature files
│           │   ├── create-user.feature
│           │   ├── negative-api.feature
│           │   ├── posts.feature
│           │   ├── update-user.feature
│           │   └── users.feature
│           ├── schemas/                     # Strict JSON Schema definitions
│           │   ├── comment-schema.json
│           │   ├── post-schema.json
│           │   └── user-schema.json
│           └── simplelogger.properties      # SLF4J logging configuration
├── .gitignore                               # Git ignored files & directories
├── LICENSE                                  # MIT License
├── pom.xml                                  # Maven project configuration
├── README.md                                # Portfolio documentation
└── testng.xml                               # TestNG suite configuration
```

---

## 🎯 Test Coverage

### API Endpoints Tested (JSONPlaceholder Public API)

| HTTP Method | Endpoint | Description | Validation Performed |
| :--- | :--- | :--- | :--- |
| `GET` | `/users` | Get all users | HTTP 200, non-empty list, JSON content type, latency check |
| `GET` | `/users/{id}` | Get user by ID | HTTP 200, POJO deserialization, JSON schema compliance |
| `POST` | `/users` | Create user | HTTP 201, User POJO serialization, generated ID check |
| `PUT` | `/users/{id}` | Update user | HTTP 200, full update verification |
| `PATCH` | `/users/{id}` | Partial update | HTTP 200, targeted field verification |
| `DELETE`| `/users/{id}` | Delete user | HTTP 200, resource removal response |
| `GET` | `/posts` | Get all posts | HTTP 200, list size assertions |
| `GET` | `/posts/{id}` | Get post by ID | HTTP 200, Post POJO matching, JSON schema compliance |
| `GET` | `/posts?userId={id}`| Query parameter filter | HTTP 200, all items filtered by userId |
| `POST` | `/posts` | Create post | HTTP 201, Post POJO serialization |
| `DELETE`| `/posts/{id}` | Delete post | HTTP 200 response |
| `GET` | `/comments?postId={id}`| Query comments | HTTP 200, all items filtered by postId |
| `GET` | `/comments/{id}` | Get comment by ID | HTTP 200, Comment JSON schema compliance |
| `POST` | `/comments` | Create comment | HTTP 201, Comment POJO serialization |
| `GET` | `/users/999999` | Non-existent user | HTTP 404 Not Found |
| `GET` | `/posts/999999` | Non-existent post | HTTP 404 Not Found |
| `GET` | `/comments/999999` | Non-existent comment | HTTP 404 Not Found |
| `GET` | `/invalid-endpoint` | Invalid resource route | HTTP 404 Not Found |

### Cucumber Feature Files (5 Files)

1. **`users.feature`**: Tests fetching all users, single user retrieval, JSON schema matching, and Scenario Outlines across multiple user IDs.
2. **`posts.feature`**: Tests post lists, single post schema validation, and Scenario Outlines filtering by query parameters.
3. **`create-user.feature`**: Tests user creation via POST with single payloads and Scenario Outlines with diverse profile datasets.
4. **`update-user.feature`**: Tests PUT full updates, PATCH partial updates, and DELETE operations.
5. **`negative-api.feature`**: Tests 404 error responses for invalid IDs, non-existent endpoints, and parameterized negative scenarios.

---

## 🚀 Getting Started

### Prerequisites
- **JDK 17** or higher installed and configured in `JAVA_HOME`.
- **Apache Maven 3.8+** installed and configured in `PATH`.

### Installation
Clone the repository:
```bash
git clone https://github.com/vijaychavn08-hue/api-automation-restassured.git
cd api-automation-restassured
```

---

## 💻 Test Execution

### 1. Run All Tests (TestNG + Cucumber)
```bash
mvn clean test
```

### 2. Run Smoke Suite
```bash
mvn clean test -Dgroups=smoke
```

### 3. Run Regression Suite
```bash
mvn clean test -Dgroups=regression
```

### 4. Run Against Custom Environment / Base URL
```bash
mvn clean test -DbaseUrl=https://jsonplaceholder.typicode.com
```

---

## 📊 Allure Test Reporting

Generate and open the interactive Allure HTML report:
```bash
# Generate static HTML report in target/site/allure-maven-plugin/
mvn io.qameta.allure:allure-maven:report

# Or serve live interactive report directly in browser
mvn io.qameta.allure:allure-maven:serve

# Or using Allure CLI (if installed)
allure serve target/allure-results
```

Allure results are automatically generated in `target/allure-results/` with full REST request/response body, headers, and status code attachments.

---

## 🔄 CI/CD Pipeline (GitHub Actions)

This repository includes an automated GitHub Actions workflow in `.github/workflows/api-tests.yml`.
On every **push** or **pull request** to `main` or `master`:
1. Checks out the repository.
2. Configures JDK 17 with Maven caching.
3. Executes `mvn -B clean test`.
4. Uploads Surefire test reports, Cucumber HTML reports, and Allure results as build artifacts.

---

## ⚠️ Portfolio Disclaimer

This framework executes automated tests against the public [JSONPlaceholder](https://jsonplaceholder.typicode.com) mock API. JSONPlaceholder returns realistic HTTP responses and simulates mutations (POST, PUT, PATCH, DELETE) without persisting changes to a backend database. All tests are designed to validate actual documented HTTP contracts and behaviors.

---

## 👤 Author

**Vijay Chavan**
- **Role**: Senior Automation QA Engineer | SDET | Automation Lead
- **GitHub**: [github.com/vijaychavn08-hue](https://github.com/vijaychavn08-hue)
- **LinkedIn**: [linkedin.com/in/vijaychavhan08](https://www.linkedin.com/in/vijaychavhan08/)
