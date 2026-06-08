package bf.asce.plateformeasce.scheduler;

import bf.asce.plateformeasce.entity.Activite;
import bf.asce.plateformeasce.entity.Alerte;
import bf.asce.plateformeasce.entity.Notification;
import bf.asce.plateformeasce.entity.Kpi;
import bf.asce.plateformeasce.enums.StatutActivite;
import bf.asce.plateformeasce.enums.NiveauAlerte;
import bf.asce.plateformeasce.enums.TypeNotification;
import bf.asce.plateformeasce.repository.ActiviteRepository;
import bf.asce.plateformeasce.repository.AlerteRepository;
import bf.asce.plateformeasce.repository.NotificationRepository;
import bf.asce.plateformeasce.repository.KpiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class AutomatismeScheduler {

    private static final Logger log = LoggerFactory.getLogger(AutomatismeScheduler.class);

    private final ActiviteRepository activiteRepository;
    private final AlerteRepository alerteRepository;
    private final NotificationRepository notificationRepository;
    private final KpiRepository kpiRepository;

    public AutomatismeScheduler(ActiviteRepository activiteRepository,
                                AlerteRepository alerteRepository,
                                NotificationRepository notificationRepository,
                                KpiRepository kpiRepository) {
        this.activiteRepository = activiteRepository;
        this.alerteRepository = alerteRepository;
        this.notificationRepository = notificationRepository;
        this.kpiRepository = kpiRepository;
    }

    // Toutes les heures : détecter les activités en retard
    @Scheduled(fixedRate = 3600000)
    public void detecterRetards() {
        log.info("=== Détection des retards ===");
        List<Activite> activites = activiteRepository.findAll();
        LocalDate aujourdhui = LocalDate.now();

        for (Activite activite : activites) {
            if (activite.getDateFinPrevue() != null
                    && activite.getDateFinPrevue().isBefore(aujourdhui)
                    && activite.getStatut() != StatutActivite.TERMINEE) {

                // Créer une alerte
                Alerte alerte = new Alerte();
                alerte.setMessage("Retard détecté : activité '" + activite.getTitre() + "' dépassée.");
                alerte.setDateAlerte(LocalDateTime.now());
                alerte.setNiveau(NiveauAlerte.MOYEN);
                alerte.setActivite(activite);
                alerteRepository.save(alerte);

                // Créer une notification
                Notification notif = new Notification();
                notif.setMessage("L'activité '" + activite.getTitre() + "' est en retard.");
                notif.setDateEnvoi(LocalDateTime.now());
                notif.setLu(false);
                notif.setType(TypeNotification.ALERTE);
                notificationRepository.save(notif);

                log.info("Alerte créée pour : {}", activite.getTitre());
            }
        }
    }

    // Toutes les nuits à minuit : calculer les KPI
    @Scheduled(cron = "0 0 0 * * *")
    public void calculerKpi() {
        log.info("=== Calcul des KPI ===");
        List<Activite> toutes = activiteRepository.findAll();
        long total = toutes.size();

        if (total == 0) {
            log.info("Aucune activité en base, KPI ignorés.");
            return;
        }

        long terminees = toutes.stream()
                .filter(a -> a.getStatut() == StatutActivite.TERMINEE)
                .count();

        long enRetard = toutes.stream()
                .filter(a -> a.getDateFinPrevue() != null
                        && a.getDateFinPrevue().isBefore(LocalDate.now())
                        && a.getStatut() != StatutActivite.TERMINEE)
                .count();

        float tauxExecution = (float) terminees / total * 100;
        float tauxRetard = (float) enRetard / total * 100;

        Kpi kpi = new Kpi();
        kpi.setTauxExecutionGlobal(tauxExecution);
        kpi.setTauxRespectDelais(100 - tauxRetard);
        kpi.setNbActivitesTerminees((int) terminees);
        kpi.setNbActivitesRetard((int) enRetard);
        kpi.setNbActivitesTotal((int) total);
        kpiRepository.save(kpi);

        log.info("KPI calculés : {}% exécution, {} en retard", tauxExecution, enRetard);
    }
}