FROM openjdk:17

WORKDIR /odc

COPY target/OdcFormation-0.0.1-SNAPSHOT.jar /odc/activity.jar

ENTRYPOINT ["java", "-jar", "/odc/activity.jar"]
