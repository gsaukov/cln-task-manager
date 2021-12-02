# Cln-task-manager - Asynchronous task manager, challenge application.

Task-manager in an application that is intended to allow users create, easily manage and execute tasks of two kind: ProjectGeneration and Counter.  
Task-manager was deeply analysed and reengineered from an existing platform, inheriting all its core features and interfaces expanding it with new capabilities and technologies.  
Application was developed under influence of following factors: 
* Clean, homogenous, maintainable code<sup>*</sup>.
* Full testing coverage.
* Cloud deployment readiness.

Technology features that were introduced are:
* Gradle - Much faster and leaner than previously used tool.
* Latest Spring boot framework that gives overwhelming integration capabilities with modern libraries and solutions.
* Robust, modern and easy to maintain/deploy/container setup based on application properties exposure. It allows application to be ran locally, on premise or in cluster without any redesign with all settings inversed as environment variables.
* Reactive, resilient and transparent REST API with SSE and Classic Controllers enhanced by Swagger.
* Spring context based testing that covers system with realistic tests on all layers from controllers to repositories.

## Development Tasks:

### Task 1. 
Was to analyse or reverse engineer existing application with multiple malfunctions, poor architecture, lack of tests and necessary resources.  
Key take away from here was application's bussiness logic, API, and some naming ideas. Bussiness logic was recognised as follows: 
Application allows users CRUD operations over projectGenerationTasks.  
Another feature is to let users execute these tasks, that would generate a new file from source file taken from the classpath resource. Newly generated file should be returned to the user and persisted in the local tempfolder. Path to newly generated file must be populated in task's field `storageLocation`.
Finally, application was brought to maintainable, homogenous, working state, keeping in mind existing bussiness logic and API limitations.
Worse to mention that projectGenerationTasks lacks database(optimistic locking) or application based concurrency(locks, CAS) management, allowing user overwrite results during uncontrolled parallel executions.

### Task 2.
Was implemented inline with given business requirements to instrument CounterTask. New task has following features:
* Task Cannot be Reexecuted after it has been stopped, deleted, or finished.
* Task executionState **is Singleton**. (i.e. only one execution can occur at a time in application, you cannot invoke multiple parallel executions of the same counter).
* Singleton counter can be incremented **by single thread only**.
* Task state is exposed via SSE and Synchronized with DB.
* **In Memory** Task execution concurrency management is non blocking and achived via Concurent Map and AtomicReference CAS operations.
* **In Database** state synchronization management is achieved via Optimistic Locking. (There is at least one scenario of optimistic locking known<sup>**</sup>)

### Task 3.
The most straight forward task. `createdDaysAgo` tasks are deleted via scheduled cron job. 

## Back end anatomy:
* Gradle - Automates build.
* Java 11 - Core programming language.
* Spring Boot - Backend core.
* Spring REST - Rest API.
* Spring Validation - Data validity.
* Spring Data - ORM Hibernate/JPA/Projections.
* Java Concurrent - Multi-threading and asynchronous tasking.
* Liquibase - Database schema control.
* Swagger - Rest API design and integration.
* H2 DB - In memory Database.
* Docker - Containerization platform.
* JUnit - Developer side testing framework.
* Spring Boot Starter Test - solid testing infrastructure and capabilities.

### Build/Run steps

You will need JAVA non less than 11.  
You can import it into your IDE as gradle project, so you can review source code build/test it using IDE.  
Or build/run it manually:

```sh
You can clean build this server by executing:
./gradlew clean build
This will generate build folder with ./cln-task-manager/build/libs/CLN-TASK-MANAGER.jar
To run:
java -jar CLN-TASK-MANAGER.jar
```
This will start the server on port 80.
REST API Swagger available at http://localhost/swagger-ui.html  
It will also start H2 database server `CLN_TM_H2_ENABLED` property, with user sa and connection string: jdbc:h2:tcp://localhost:9090/file:./data/db/clntaskmanager

### Containerization

Application is fully compliant with AWS cloud and its infrastructure, therefore it fully supports containerization using **Docker** platform.

To containerize application manually:

Clean build this server by executing: `./gradlew clean build` This will generate build folder with ./cln-task-manager/build/libs/CLN-TASK-MANAGER.jar  
To build docker image run: `docker build --tag cln-task-manager:1.0 .`  
To run docker image in container using composer:`docker-compose -f ./docker-compose.yml up -d cln-task-manager`

## Testing
Testing scope is divided in 4 functional domains with which each one of them split by deeper functional detalization:
* security
* countertask
* maintenance
* projectgenerationtask 
 
Test are written using spring boot testing tools that grants context and running conditions equivalent to real.  
SpringBootTest/WebMvcTest/DataJpaTest test configurations allow limitless influence and condition simulations over application with unrestricted access to system's internals. Full testing coverage could be achieved by expanding existing test library with additional tests.

## Environment

Application is developed in "cloud ready" concepts and exposes all operational parameters as environment variables. This parameters has to be managed and injected into the container via cluster config and secret maps.

### Active Environment variables list

| Environment variable name | Default value | Description |
| :--- | :--- | :--- |
|CLN_TM_DATASOURCE_URL|jdbc:h2:file:./data/db/clntaskmanager; MODE=PostgreSQL| Application DB connection string|
|CLN_TM_DATASOURCE_USER|sa|Application DB user name|
|CLN_TM_DATASOURCE_PASSWORD| |Application DB password|
|CLN_TM_DATASOURCE_DRIVER|org.h2.Driver|Database driver (`org.postgresql.Driver` and `org.h2.Driver` are allowed)|
|CLN_TM_CONNECTIONPOOL_SIZE_MIN|2| Hikari pool min active DB connections|
|CLN_TM_CONNECTIONPOOL_SIZE_MAX|6| Max active DB connections, must be inlined with DB instance capacity|
|CLN_TM_CONNECTIONPOOL_IDLE_TIMEOUT|30000|How long to keep alive idle connection|
|CLN_TM_CONNECTIONPOOL_MAX_LIFE_TIME|2000000|Max connection lifetime before reconnect|
|CLN_TM_CONNECTION_TIMEOUT|30000|How long client will wait for a connection|
|CLN_TM_THREAD_POOL_CORE_SIZE|8|@Async configuration bottom line|
|CLN_TM_THREAD_POOL_MAX_SIZE|128|@Async configuration bottom max must be aligned with node resources|
|CLN_TM_SERVER_PORT|80| Unsecure HTTP traffic |
|CLN_TM_H2_ENABLED|true| Enables in memory h2 database, only for local development and testing|
|CLN_TM_CORS_ALLOWED_ORIGIN|"*"| CORS configuration setup, single host|
|CLN_TM_AUTH_HEADER_DISABLED|true|Disable custom auth header filter, disabled for local setup|
|CLN_TM_COUNTER_TASK_TIMEOUT_MS|1000| Counter task increment period|
|CLN_TM_COUNTER_TASK_EMITTER_DURATION_MS|60000| Life time period for user connection to consume task execution SSE events|
|CLN_TM_COUNTER_TASK_EMITTER_STEP_MS|1000| How often execution SSE events are sent (Back pressure control)|
|CLN_TM_COUNTER_TASK_CLEANUP_ENABLED|true|Disabled in testing context, to not to interfere with tests and introduce flickering behaviour|
|CLN_TM_COUNTER_TASK_CLEANUP_SCHEDULE|0 0 02 * * *| Tasks cleanup schedule cron job every day at 2am|
|CLN_TM_COUNTER_TASK_CLEANUP_CREATED_DAYS|10| Amount of days that should pass when task considered to be cleaned|

Georgy Saukov,  
Munich, December 2021.

<sup>*</sup> *I have thought of reworking and adding more things like: Add pageable to controllers, Unify entities, in memory event queue, single generic task service interface and so on. But that I assume would add additional pressure on reviewers, and finally there should be some rational stop.*

<sup>**</sup> *Optimistic locking is possible in case user simultaneously executes and deletes active counterTask. This can be fixed by decoupling `CounterTaskExecutionStateSynchronizer` with the rest the of the services by introducing `ConcurrentLinkedQueue<CounterTaskExecutionState>` that would order incoming DB updates. It was decided not to introduce this fix in order to not to overload existing already complex implementation.*
