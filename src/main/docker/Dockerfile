FROM adoptopenjdk/openjdk8-openj9:alpine-slim
VOLUME /tmp
ADD common-code-generate-web-0.0.1-SNAPSHOT.jar /common-code-generate-web-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/common-code-generate-web-0.0.1-SNAPSHOT.jar"]
