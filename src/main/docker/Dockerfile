FROM adoptopenjdk/openjdk8-openj9:alpine-slim
VOLUME /tmp

add common-code-generate-web-0.0.1-SNAPSHOT.jar /common-code-generate-web-0.0.1-SNAPSHOT.jar
add lib /lib
add config /config
add config/generator.properties /

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/common-code-generate-web-0.0.1-SNAPSHOT.jar"]
