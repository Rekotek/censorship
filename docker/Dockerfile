FROM azul/zulu-openjdk-alpine:11

EXPOSE 8080

ENV CENSOR_APP_ROOT=/
ENV TZ=Europe/Kiev

COPY censorship-app.jar censorship-app.jar

RUN echo 'http://nl.alpinelinux.org/alpine/edge/community' >> /etc/apk/repositories
RUN apk --update add --no-cache tomcat-native=1.2.21-r1

ENTRYPOINT ["java", "-jar", "censorship-app.jar"]