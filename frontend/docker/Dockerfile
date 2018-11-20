FROM openjdk:8

EXPOSE 8080
ENV CENSOR_APP_ROOT = .

ENTRYPOINT ["java", "-jar", "censorship-app.jar"]

ADD ../target/censorship-app.jar censorship-app.jar

