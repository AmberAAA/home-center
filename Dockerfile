FROM openjdk:8u312-jdk-oracle

COPY ./build/libs /usr/src/myapp

WORKDIR /usr/src/myapp

CMD ["java", "-jar", "home-0.0.1-SNAPSHOT.jar"]
