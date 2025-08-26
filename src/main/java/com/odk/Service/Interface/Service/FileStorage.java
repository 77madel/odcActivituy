package com.odk.Service.Interface.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileStorage {

    // Dossiers de destination pour les fichiers
    private final String images = "images/";
    //private final String lien = "http://localhost:8080";  // URL publique pour le frontend
    private final String lien = "https://odc-api.onrender.com";  // URL publique pour le frontend

    public String saveImage(MultipartFile fichier) throws IOException {
        return saveFile(fichier, images, "image");
    }

    // Méthode générique pour sauvegarder un fichier
    private String saveFile (MultipartFile fichier, String dossier, String type) throws IOException {
        // Vérifie si le dossier de destination existe, sinon le créer
        Path cheminDossier = Paths.get(dossier);
        if (!Files.exists(cheminDossier)) {
            Files.createDirectories(cheminDossier);
        }

        // Vérifie que le fichier n'est pas vide et a un nom valide
        String nomFichier = fichier.getOriginalFilename();
        if (nomFichier == null || nomFichier.isEmpty()) {
            throw new IOException("Le nom du fichier est invalide.");
        }

        if (type.equals("image") && !isValidImageFile(nomFichier)) {
            throw new IOException("Le fichier image n'est pas valide.");
        }

        // Génère un nom de fichier unique pour éviter les conflits
        String extension = getFileExtension(nomFichier);
        String nomFichierUnique = UUID.randomUUID().toString() + "." + extension;
        Path cheminFichier = cheminDossier.resolve(nomFichierUnique);

        // Sauvegarde le fichier sur le disque
        Files.copy(fichier.getInputStream(), cheminFichier);

        // Retourne l'URL du fichier sauvegardé pour le frontend
        return lien + "/" + dossier + nomFichierUnique;
    }


    // Supprimer un fichier (basé sur l'URL sauvegardée)
    public void deleteFileByUrl(String fileUrl) throws IOException {
        // Supprime le préfixe de l'URL pour obtenir le chemin relatif
        String relativePath = fileUrl.replace(lien + "/", "");
        Path cheminFichier = Paths.get(relativePath);

        // Supprimer le fichier si présent
        Files.deleteIfExists(cheminFichier);
    }

    // Vérifie si le fichier image a une extension valide
    private boolean isValidImageFile(String nomFichier) {
        String extension = getFileExtension(nomFichier).toLowerCase();
        return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png");
    }

    // Extrait l'extension du fichier
    private String getFileExtension(String nomFichier) {
        int lastIndexOfDot = nomFichier.lastIndexOf('.');
        if (lastIndexOfDot == -1) {
            throw new IllegalArgumentException("Le fichier ne semble pas avoir d'extension.");
        }
        return nomFichier.substring(lastIndexOfDot + 1);
    }
}
