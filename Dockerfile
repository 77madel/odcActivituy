# Étape 1 : Utiliser l'image officielle OpenJDK 17 comme base
FROM openjdk:17

# Étape 2 : Définir le répertoire de travail dans le conteneur
WORKDIR /odc

# Étape 3 : Copier le fichier JAR dans le conteneur
# Assurez-vous que le fichier JAR est bien généré avant de construire l'image
COPY ./target/OdcFormation-0.0.1-SNAPSHOT.jar /odc/activity.jar

# Étape 4 : Exposer le port 8080 pour l'accès à l'application
EXPOSE 8080

# Étape 5 : Démarrer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/odc/activity.jar"]
