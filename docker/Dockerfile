FROM bellsoft/liberica-openjdk-alpine-musl:17.0.1-12

EXPOSE 8080
ENV TZ=Europe/Kiev

ENV CENSOR_APP_ROOT=/

COPY censorship-app.jar censorship-app.jar

# https://blog.longyb.com/2019/06/09/tomcat_hang_creation_of_securerandom_instance_for_sessionid_english/
# https://stackoverflow.com/questions/6110395/sqlrecoverableexception-i-o-exception-connection-reset
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-Dspring.jmx.enabled=false", "-Dsecurerandom.source=file:/dev/./urandom", "-jar", "censorship-app.jar"]
