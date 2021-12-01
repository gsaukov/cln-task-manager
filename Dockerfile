FROM amazoncorretto:11
EXPOSE 80
ARG JAR_FILE=./build/libs/CLN-TASK-MANAGER.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
