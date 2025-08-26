package com.odk.Service.Interface.Service;

import com.odk.Entity.Etape;
import com.odk.Entity.Activite;
import com.odk.Repository.ActiviteRepository;
import com.odk.Repository.EtapeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EtapeActiviteSchedulerService {

    private final EtapeRepository etapeRepository;
    private final ActiviteRepository activiteRepository;


    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void mettreAJourStatutEtapesActivitesToutesLesHeures() {
        log.info("Début de la mise à jour automatique des statuts d'étapes et activités (horaire)");
        mettreAJourTousLesStatutsEtapes();
        mettreAJourTousLesStatutsActivites();
    }



    //@Scheduled(cron = "0 */30 * * * *")
    //@Transactional
   /* public void mettreAJourStatutEtapesActivitesToutesLes30Minutes() {
        log.info("Début de la mise à jour automatique des statuts d'étapes et activités (30 minutes)");
        mettreAJourTousLesStatutsEtapes();
        mettreAJourTousLesStatutsActivites();
    }*/


   // @Scheduled(cron = "0 */10 * * * *")
    //@Transactional
    /*public void mettreAJourStatutEtapesActivitesToutesLes10Minutes() {
        log.info("Début de la mise à jour automatique des statuts d'étapes et activités (10 minutes)");
        mettreAJourTousLesStatutsEtapes();
        mettreAJourTousLesStatutsActivites();
    }*/

    /**
     * Alternative avec fixedRate : exécute toutes les 5 minutes (300000 ms)
     * Commence 1 minute après le démarrage de l'application
     */
   // @Scheduled(fixedRate = 300000, initialDelay = 60000)
    //@Transactional
   /* public void mettreAJourStatutEtapesActivitesToutesLes5Minutes() {
        log.info("Début de la mise à jour automatique des statuts d'étapes et activités (5 minutes - fixedRate)");
        mettreAJourTousLesStatutsEtapes();
        mettreAJourTousLesStatutsActivites();
    }*/

    /**
     * Méthode privée qui effectue la mise à jour de tous les statuts des étapes
     */
    private void mettreAJourTousLesStatutsEtapes() {
        try {
            List<Etape> etapes = etapeRepository.findAll();
            int compteurMisAJour = 0;

            for (Etape etape : etapes) {
                if (etape.getDateDebut() != null && etape.getDateFin() != null) {
                    try {
                        etape.mettreAJourStatut();
                        etapeRepository.save(etape);
                        compteurMisAJour++;
                    } catch (Exception e) {
                        log.error("Erreur lors de la mise à jour du statut de l'étape ID: {}, Nom: {}",
                                etape.getId(), etape.getNom(), e);
                    }
                } else {
                    log.warn("Étape ID: {} - Nom: {} n'a pas de dates définies, statut non mis à jour",
                            etape.getId(), etape.getNom());
                }
            }

            log.info("Mise à jour des étapes terminée : {}/{} étapes mises à jour", compteurMisAJour, etapes.size());
        } catch (Exception e) {
            log.error("Erreur générale lors de la mise à jour des statuts d'étapes", e);
        }
    }

    /**
     * Méthode privée qui effectue la mise à jour de tous les statuts des activités
     */
    private void mettreAJourTousLesStatutsActivites() {
        try {
            List<Activite> activites = activiteRepository.findAll();
            int compteurMisAJour = 0;

            for (Activite activite : activites) {
                if (activite.getDateDebut() != null && activite.getDateFin() != null) {
                    try {
                        activite.mettreAJourStatut();
                        activiteRepository.save(activite);
                        compteurMisAJour++;
                    } catch (Exception e) {
                        log.error("Erreur lors de la mise à jour du statut de l'activité ID: {}, Nom: {}",
                                activite.getId(), activite.getNom(), e);
                    }
                } else {
                    log.warn("Activité ID: {} - Nom: {} n'a pas de dates définies, statut non mis à jour",
                            activite.getId(), activite.getNom());
                }
            }

            log.info("Mise à jour des activités terminée : {}/{} activités mises à jour", compteurMisAJour, activites.size());
        } catch (Exception e) {
            log.error("Erreur générale lors de la mise à jour des statuts d'activités", e);
        }
    }

    /**
     * Méthode pour forcer une mise à jour manuelle (utile pour les tests ou actions admin)
     */
    public void forcerMiseAJourStatutsEtapes() {
        log.info("Mise à jour manuelle forcée des statuts d'étapes");
        mettreAJourTousLesStatutsEtapes();
    }

    /**
     * Méthode pour forcer une mise à jour manuelle des activités
     */
    public void forcerMiseAJourStatutsActivites() {
        log.info("Mise à jour manuelle forcée des statuts d'activités");
        mettreAJourTousLesStatutsActivites();
    }

    /**
     * Méthode pour forcer une mise à jour manuelle de tous les statuts
     */
    public void forcerMiseAJourTousLesStatuts() {
        log.info("Mise à jour manuelle forcée de tous les statuts (étapes et activités)");
        mettreAJourTousLesStatutsEtapes();
        mettreAJourTousLesStatutsActivites();
    }
}