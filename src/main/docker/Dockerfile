FROM adoptopenjdk/openjdk8-openj9:alpine-slim
VOLUME /tmp
ADD commoncodegenerateweb-0.0.1-SNAPSHOT.jar /commoncodegenerateweb-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/commoncodegenerateweb-0.0.1-SNAPSHOT.jar"]
