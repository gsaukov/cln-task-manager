# Cln-task-manager - Async task manager, challenge application

Application is intended to allow users create and manage various tasks, including asynchronous.  
Task-manager was deeply analysed and reengineered from an existing platform, inheriting all its core features and interfaces expanding it with new capabilities and technologies.  

Those technology features are:
* Gradle - Much faster and leaner than previously used tool.
* Latest Spring boot framework that gives overwhelming integration capabilities with modern libraries and solutions.
* Robust, modern and easy to maintain/deploy/container setup based on application properties exposure. Which allows application to be ran locally, on premise or in cluster without any redesign with all settings inversed as environment variables.
* Reactive, resilient and transparent REST API with SSE and Classic Controllers enhanced by Swagger.
* Spring context based testing that covers system with realistic tests on all layers from controllers to repositories.

## Development Tasks:

### Task 1. 
Was to analyse or reverse engineer existing application with multiple malfunctions, poor architecture, lack of tests and necessary resources.  
Key take away from here was applications bussiness logic, API, and some naming ideas. Bussiness logic was recognised as follows: 
Application allows users CRUD operations over projectGenerationTasks.  
Another feature is to let users execute these tasks, that would generate a new file from source file taken from the classpath resource. Newly generated file should be returned to the user and persisted in the local tempfolder. Path to newly generated file must be populated in task's field `storageLocation`.
Finally, application was brought to maintainable, homogenous, working state, keeping in mind existing bussiness logic and API limitations.


## Back end components:
* Gradle - Automates build.
* Java 11 - Core programming language.
* Spring Boot - Backend core.
* Spring REST - Rest API.
* Spring Validation - Data validity.
* Spring Data - ORM Hibernate/JPA/Projections.
* Java Concurrent - Multi-threading and asynchronous tasking.
* Liquibase - Database schema control.
* Swagger - Rest API design and integration.
* H2 DB - In memory Database .
* Docker - Containerization platform.

### Build/Run steps

You will need JAVA non less than 11.  
You can import it into your IDE as gradle project, so you can review source code build/test it using IDE.  
Or build/run it manually:

```sh
You can clean build this server by executing:
./gradlew clean build
This will generate build folder with ./cln-task-manager/build/libs/cln-task-manager.jar
To run:
java -jar cln-task-manager.jar
```
This will start the server on port 80.
REST API Swagger available at http://localhost/swagger-ui/index.html#/

### Containerization

Application is fully compliant with AWS cloud and its infrastructure, therefore it fully supports containerization using **Docker** platform.

To containerize application manually:

Clean build this server by executing: `./gradlew clean build` This will generate build folder with ./cln-task-manager/build/libs/cln-task-manager.jar  
To build docker image run: `docker build --tag cln-task-manager:1.0`  
To run docker image in container using composer:`docker-compose -f ./docker-compose.yml up -d cln-task-manager`

### Testing
Full testing coverage is achieved by layers:

* **Unit** - Covers particular Service or class. Example: 
* **Component** - Aimed to test group of components in defined spring context. Database, controllers and external REST API is mocked. 
* **Integration (E2E)** - Integration or end-to-end test based on custom build framework that can interact with REST API, Databases, Message brokers and other supported programming interfaces. Applicable for testing real back end applications staged on UAT, Staging or any other PROD simulating environment. Example: 
