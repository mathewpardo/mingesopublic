FROM openjdk:17
ARG JAR_FILE=target/MyWebApp1.jar
COPY ${JAR_FILE} MyWebApp1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/MyWebApp1.jar"]