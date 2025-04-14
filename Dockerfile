## Étape 1 : Utiliser l'image officielle OpenJDK 17 comme base
#FROM openjdk:17
#
## Étape 2 : Définir le répertoire de travail dans le conteneur
#WORKDIR /odc
#
## Étape 3 : Copier le fichier JAR dans le conteneur
## Assurez-vous que le fichier JAR est bien généré avant de construire l'image
#COPY ./target/OdcFormation-0.0.1-SNAPSHOT.jar /odc/activity.jar
#
## Étape 4 : Exposer le port 8080 pour l'accès à l'application
#EXPOSE 8080
#
## Étape 5 : Démarrer l'application Spring Boot
#ENTRYPOINT ["java", "-jar", "/odc/activity.jar"]

# Étape 1 : Build du projet avec Maven
FROM maven:3.9.4-eclipse-temurin-17 AS builder

WORKDIR /app

# Copier les fichiers du projet dans le conteneur
COPY . .

# Lancer le build (tu peux retirer -DskipTests si tu veux lancer les tests)
RUN mvn clean package -DskipTests

# Étape 2 : Image d'exécution avec Java seulement (plus légère)
FROM openjdk:17

WORKDIR /odc

# Copier uniquement le .jar généré depuis l'étape précédente
COPY --from=builder /app/target/OdcFormation-0.0.1-SNAPSHOT.jar activity.jar

# Exposer le port Spring Boot
EXPOSE 8080

# Commande de lancement
ENTRYPOINT ["java", "-jar", "activity.jar"]
