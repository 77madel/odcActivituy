package com.odk.Controller;

import com.odk.Entity.Activite;
import com.odk.Entity.Etape;
import com.odk.Enum.Statut;
import com.odk.Repository.ActiviteRepository;
import com.odk.Repository.EtapeRepository;
import com.odk.Service.Interface.Service.ActiviteService;
import com.odk.dto.ActiviteDTO;
import com.odk.dto.ParticipantDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping("/activite")
@CrossOrigin(origins = "http://localhost:4200")
public class ActiviteController {

    private final ActiviteRepository activiteRepository;
    private ActiviteService activiteService;
    private EtapeRepository etapeRepository;

    @PostMapping
    @PreAuthorize("hasRole('PERSONNEL')")
    public Activite ajouter(@RequestBody Activite activite) {
        try {
            return activiteService.add(activite);
        } catch (ResponseStatusException e) {
            throw e; // Laissez passer l'exception si elle provient de la méthode add
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de l'ajout de l'activité", e);
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public List<ActiviteDTO> listerActivite() {
        return activiteService.List().stream()
                .map(activite -> {
                    List<Etape> etapes = (List<Etape>) activite.getEtapes();
                    List<ParticipantDTO> listeDebutDTO = new ArrayList<>();
                    List<ParticipantDTO> listeResultatDTO = new ArrayList<>();

                    if (etapes != null) {
                        for (Etape etape : etapes) {
                            // Récupération de listeDebut
                            listeDebutDTO.addAll(etape.getListeDebut().stream()
                                    .map(participant -> new ParticipantDTO(participant.getId(), participant.getNom()))
                                    .collect(Collectors.toList()));

                            // Récupération de listeResultat
                            listeResultatDTO.addAll(etape.getListeResultat().stream()
                                    .map(participant -> new ParticipantDTO(participant.getId(), participant.getNom()))
                                    .collect(Collectors.toList()));

                            // Log de débogage
                            System.out.println("Étape: " + etape.getNom() + " Liste Résultat: " + listeResultatDTO);
                        }
                    }

                    return new ActiviteDTO(
                            activite.getId(),
                            activite.getNom(),
                            activite.getTitre(),
                            activite.getDateDebut(),
                            activite.getDateFin(),
                            activite.getStatut(),
                            activite.getLieu(),
                            activite.getDescription(),
                            activite.getObjectifParticipation(),
                            etapes != null && !etapes.isEmpty() ? etapes.get(0) : null, // Prend la première étape
                            activite.getEntite(),
                            activite.getSalleId(),
                            activite.getTypeActivite(),
                            listeDebutDTO,
                            listeResultatDTO
                    );
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Activite> getActiviteParId(@PathVariable Long id) {
        try {
            return activiteService.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erreur lors de la récupération de l'activité par ID", e);
        }
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('PERSONNEL')")
    @ResponseStatus(HttpStatus.OK)
    public Activite modifier(@PathVariable Long id, @RequestBody Activite activite) {
            return activiteService.update(activite, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteActivite(@PathVariable Long id) {
        activiteService.delete(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Activité supprimée avec succès");

        return ResponseEntity.ok(response);
    }
   /* public void deleteActivite(@PathVariable Long id) {
        activiteService.delete(id);
    }*/


    @GetMapping("/enCours")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    public List<ActiviteDTO> listerActiviteEncours() {
        return activiteService.List().stream()
                .map(activite -> {
                    System.out.println("Traitement de l'activité: " + activite.getNom());

                    List<ParticipantDTO> listeDebutDTO = new ArrayList<>();
                    List<ParticipantDTO> listeResultatDTO = new ArrayList<>();

                    // Filtrer les étapes en cours et remplir les listes de participants uniquement si l'étape est en cours
                    boolean hasEtapeEnCours = activite.getEtapes().stream()
                            .filter(etape -> Statut.En_Cours.equals(etape.getStatut()))
                            .peek(etape -> {
                                System.out.println("Étape valide en cours trouvée : " + etape.getNom());
                                listeDebutDTO.addAll(etape.getListeDebut().stream()
                                        .map(participant -> new ParticipantDTO(participant.getId(), participant.getNom()))
                                        .toList());
                                listeResultatDTO.addAll(etape.getListeResultat().stream()
                                        .map(participant -> new ParticipantDTO(participant.getId(), participant.getNom()))
                                        .toList());
                            })
                            .findAny()
                            .isPresent();

                    // Retourner l'ActiviteDTO seulement si une étape en cours est présente
                    if (hasEtapeEnCours) {
                        System.out.println("Activité avec étape EN_COURS trouvée: " + activite.getNom());
                        return new ActiviteDTO(
                                activite.getId(),
                                activite.getNom(),
                                activite.getTitre(),
                                activite.getDateDebut(),
                                activite.getDateFin(),
                                activite.getStatut(),
                                activite.getLieu(),
                                activite.getDescription(),
                                activite.getObjectifParticipation(),
                                activite.getEtapes().stream()
                                        .filter(etape -> Statut.En_Cours.equals(etape.getStatut()))
                                        .findFirst() // Prend la première étape en cours, s'il y en a
                                        .orElse(null),
                                activite.getEntite(),
                                activite.getSalleId(),
                                activite.getTypeActivite(),
                                listeDebutDTO,
                                listeResultatDTO
                        );
                    }
                    System.out.println("Aucune étape EN_COURS pour l'activité: " + activite.getNom());
                    return null;
                })
                .filter(Objects::nonNull) // Supprimer les ActiviteDTO null (sans étape en cours)
                .collect(Collectors.toList());
    }

    @GetMapping("/nombre") // Pas de paramètres
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    public ResponseEntity<Long> getNombreActivite() {
        long count = activiteRepository.count();
        return ResponseEntity.ok(count); // Retourne le nombre d'utilisateurs
    }

    @GetMapping("/nombreActivitesEncours")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    public ResponseEntity<Long> getNombreActivitesEncours() {
        long count = activiteRepository.countByStatut(Statut.En_Cours); // Compte les activités avec statut "En_Cours"
        return ResponseEntity.ok(count); // Retourne le nombre d'activités
    }

    @GetMapping("/nombreActivitesEnAttente")
    public ResponseEntity<Long> getNombreActivitesEnAttente() {
        long count = activiteRepository.countByStatut(Statut.En_Attente); // Compte les activités avec statut "En_Cours"
        return ResponseEntity.ok(count); // Retourne le nombre d'activités
    }

    @GetMapping("/nombreActivitesTerminer")
    @PreAuthorize("hasRole('PERSONNEL') or hasRole('SUPERADMIN')")
    public ResponseEntity<Long> getNombreActivitesTerminer() {
        long count = activiteRepository.countByStatut(Statut.Termine); // Compte les activités avec statut "En_Cours"
        return ResponseEntity.ok(count); // Retourne le nombre d'activités
    }
}

