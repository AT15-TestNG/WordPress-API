[![WordPress Pipeline](https://github.com/AT15-TestNG/WordPress-API/actions/workflows/github-actions-pipeline.yml/badge.svg)](https://github.com/AT15-TestNG/WordPress-API/actions/workflows/github-actions-pipeline.yml)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=wordpress-testng&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=wordpress-testng)
# WordPress API v2 Testing Framework

This framework was implemented to test the WordPress API v2.

The following dependencies were used:
* [Rest Assured](https://rest-assured.io/)
* [TestNG](https://testng.org/)
* [Cucumber](https://cucumber.io/)
* [SonarQube](https://www.sonarqube.org/)
* [Log4j](https://logging.apache.org/log4j/)
* [Rest Assured](https://rest-assured.io/)
* [slf4j](https://www.slf4j.org/)

## Getting Started

Before to run this project you need the following tools installed on your machine:
* [Git](https://git-scm.com/)
* [Java](https://www.java.com/) _version >= 16_
* An IDE (e.g. [IntelliJ IDEA](https://www.jetbrains.com/idea/))

Clone the repository and open it in your IDE.

### Commands

To run the tests with a specific tag, you can use the following command:
```
$ gradle clean executeFeatures -PenvId="QA01" -PcucumberOptions="@Tag"
```

You can also run the tests with a specific tag and a specific feature:
```
$ gradle clean executeFeatures -PenvId="QA01" -PcucumberOptions="@Tag and @Feature"
```

Or you can run the tests with a specific tag and a specific scenario:
```
$ gradle clean executeFeatures -PenvId="QA01" -PcucumberOptions="@Tag and @Scenario"
```


Run the following command to run regression tests:
```
$ gradle clean executeFeatures -PenvId="QA01" -PcucumberOptions="@Regression and not @Bug"
```


### Code Inspection
To ensure the quality of the code, It was used a static analysis tool [Sonarcloud](https://sonarcloud.io/code-quality).
This is part of the [Continuous Integration process](https://en.wikipedia.org/wiki/Continuous_integration), and it is 
run every time new code is pushed in any branch of this
repository.
If you want to run sonarqube analysis locally, firstly you need to configure **SONAR_TOKEN** as an environment variable
in your system. After that, run the following command:
```
$ ./gradlew sonarqube
```

### Continuous Integration Pipeline
CI pipeline is written in GitHub Actions, and it is used to run the tests, analyze the code quality in Sonarcloud, 
generate the reports and publish the reports on GitHub pages.

## About
This framework was created with educative purposes and is part of AT15 API Testing subject, and was implemented by:
* [Adrian Oviedo](adrian.oviedo@fundacion-jala.org)
* [Agustin Mediotti](agustin.mediotti@fundacion-jala.org)
* [Jimy Tastaca](jimy.tastaca@fundacion-jala.org)
* [Saul Fuentes](saul.fuentes@fundacion-jala.org)
* [Sergio Mendieta](sergio.mendieta@fundacion-jala.org)
