FROM azul/zulu-openjdk-alpine:11

EXPOSE 8080

ENV CENSOR_APP_ROOT=/
ENV TZ=Europe/Kiev

RUN echo 'http://nl.alpinelinux.org/alpine/edge/community' >> /etc/apk/repositories
RUN apk --update add --no-cache tomcat-native=1.2.21-r1

COPY censorship-app.jar censorship-app.jar

# https://blog.longyb.com/2019/06/09/tomcat_hang_creation_of_securerandom_instance_for_sessionid_english/
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.jmx.enabled=false", "-jar", "censorship-app.jar"]
