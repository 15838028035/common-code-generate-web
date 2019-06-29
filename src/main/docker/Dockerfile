FROM adoptopenjdk/openjdk8-openj9:alpine-slim
VOLUME /tmp

ADD common-code-generate-web-0.0.1-SNAPSHOT.jar /common-code-generate-web-0.0.1-SNAPSHOT.jar
RUN mkdir lib
RUN mkdir config
ADD lib /lib
ADD config /config
ADD config/generator.properties /

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/common-code-generate-web-0.0.1-SNAPSHOT.jar"]
