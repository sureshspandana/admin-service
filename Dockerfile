FROM openjdk:8
EXPOSE 8100
ADD target/admin-service.jar admin-service.jar
ENTRYPOINT ["java","-jar","admin-service.jar"]