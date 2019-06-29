FROM adoptopenjdk/openjdk8-openj9:alpine-slim
VOLUME /tmp
RUN mkdir app
RUN mkdir -p /app/config
RUN mkdir -p /app/lib

ADD common-code-generate-web-0.0.1-SNAPSHOT.jar /app/common-code-generate-web-0.0.1-SNAPSHOT.jar
COPY config/* /app/config/
COPY config/generator.properties /app/
COPY lib/*.jar /app/lib/

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/common-code-generate-web-0.0.1-SNAPSHOT.jar"]
