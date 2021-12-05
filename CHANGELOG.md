## 1.1
 - [TASK-5] - Ctm-client front end challenge, CORS fix, doc updates

## 1.0
 - [TASK-4] - Containerization, readme, more tests, release version. 

## 0.3.0
 - [TASK-3] - The API can be used to create tasks, but the user is not required to execute those tasks.
   The tasks that are not executed after an extended period (e.g. a week) should be periodically cleaned up (deleted).

## 0.2.0
 - [TASK-2] - The task is to extend the current functionality of the backend by
    - implementing a new task type
    - showing the progress of the task execution
    - implementing a task cancellation mechanism.

   The new task type is a simple counter which is configured with two input parameters, `x` and `y` of type `integer`.
   When the task is executed, counter should start in the background and progress should be monitored.
   Counting should start from `x` and get increased by one every second.
   When counting reaches `y`, the task should finish successfully.

   The progress of the task should be exposed via the API so that a web client can monitor it.
   Canceling a task that is being executed should be possible, in which case the execution should stop.

## 0.1.0 
 - [TASK-1] - The project you received fails to start correctly due to a problem in the dependency injection.
   Identify that problem and fix it.
