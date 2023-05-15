FROM openjdk:17

ADD target/strong_backend_test_assignment.jar strong_backend_test_assignment.jar

ENTRYPOINT ["java", "-jar", "strong_backend_test_assignment.jar"]

EXPOSE 8080