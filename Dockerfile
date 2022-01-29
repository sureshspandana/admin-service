FROM openjdk:8
EXPOSE 8081
ADD target/admin-service.jar admin-service.jar
ENTRYPOINT ["java","-jar","/admin-service.jar"]