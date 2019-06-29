FROM adoptopenjdk/openjdk8-openj9:alpine-slim
VOLUME /tmp
add common-code-generate-web-0.0.1-SNAPSHOT-assembly.zip /common-code-generate-web-0.0.1-SNAPSHOT-assembly.zip
cd /
unzip common-code-generate-web-0.0.1-SNAPSHOT-assembly.zip

cd common-code-generate-web-0.0.1-SNAPSHOT

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/common-code-generate-web-0.0.1-SNAPSHOT/common-code-generate-web-0.0.1-SNAPSHOT.jar"]
