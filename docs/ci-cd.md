# Continuous Integration & CD

## GitHub Actions CI Pipeline
The automated test pipeline is configured via `.github/workflows/api-tests.yml`.

### Pipeline Trigger Conditions
- Push to `main` or `master` branch
- Pull request targeted at `main` or `master`
- Manual execution via GitHub `workflow_dispatch`

### Pipeline Stages
1. **Repository Checkout**: Clones code using `actions/checkout@v4`.
2. **JDK Setup**: Sets up Eclipse Temurin JDK 17 with Maven dependency caching via `actions/setup-java@v4`.
3. **Test Execution**: Executes `mvn -B clean test`.
4. **Artifact Preservation**: Collects Surefire reports, Cucumber HTML reports, and Allure results in `target/` and uploads them via `actions/upload-artifact@v4`.
