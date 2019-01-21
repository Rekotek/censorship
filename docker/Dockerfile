FROM openjdk:8

EXPOSE 8080

ENV CENSOR_APP_ROOT=/
ENV TZ=Europe/Kiev

COPY censorship-app.jar censorship-app.jar

ENTRYPOINT ["java", "-jar", "censorship-app.jar"]