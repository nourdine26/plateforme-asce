package bf.asce.plateformeasce.scheduler;

import bf.asce.plateformeasce.entity.Activite;
import bf.asce.plateformeasce.entity.Alerte;
import bf.asce.plateformeasce.entity.Direction;
import bf.asce.plateformeasce.entity.Notification;
import bf.asce.plateformeasce.entity.Kpi;
import bf.asce.plateformeasce.enums.StatutActivite;
import bf.asce.plateformeasce.enums.NiveauAlerte;
import bf.asce.plateformeasce.enums.TypeNotification;
import bf.asce.plateformeasce.repository.ActiviteRepository;
import bf.asce.plateformeasce.repository.AlerteRepository;
import bf.asce.plateformeasce.repository.DirectionRepository;
import bf.asce.plateformeasce.repository.NotificationRepository;
import bf.asce.plateformeasce.repository.KpiRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import jakarta.annotation.PostConstruct;
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
    private final DirectionRepository directionRepository;

    public AutomatismeScheduler(ActiviteRepository activiteRepository,
                                AlerteRepository alerteRepository,
                                NotificationRepository notificationRepository,
                                KpiRepository kpiRepository,
                                DirectionRepository directionRepository) {
        this.activiteRepository = activiteRepository;
        this.alerteRepository = alerteRepository;
        this.notificationRepository = notificationRepository;
        this.kpiRepository = kpiRepository;
        this.directionRepository = directionRepository;
    }

    @PostConstruct
    public void calculerAuDemarrage() {
        log.info("=== Calcul initial des KPI au démarrage ===");
        calculerKpi();
    }

    @Scheduled(fixedRate = 3600000)
    public void detecterRetards() {
        log.info("=== Détection des retards ===");
        List<Activite> activites = activiteRepository.findAll();
        LocalDate aujourdhui = LocalDate.now();

        for (Activite activite : activites) {
            if (activite.getDateFinPrevue() != null
                    && activite.getDateFinPrevue().isBefore(aujourdhui)
                    && activite.getStatut() != StatutActivite.TERMINEE) {

                Alerte alerte = new Alerte();
                alerte.setMessage("Retard détecté : activité '" + activite.getTitre() + "' dépassée.");
                alerte.setDateAlerte(LocalDateTime.now());
                alerte.setNiveau(NiveauAlerte.MOYEN);
                alerte.setActivite(activite);
                alerteRepository.save(alerte);

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

    @Scheduled(cron = "0 0 * * * *")
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

        Kpi kpiGlobal = new Kpi();
        kpiGlobal.setTauxExecutionGlobal(tauxExecution);
        kpiGlobal.setTauxRespectDelais(100 - tauxRetard);
        kpiGlobal.setNbActivitesTerminees((int) terminees);
        kpiGlobal.setNbActivitesRetard((int) enRetard);
        kpiGlobal.setNbActivitesTotal((int) total);
        kpiRepository.save(kpiGlobal);

        log.info("KPI global : {}% exécution, {} en retard", tauxExecution, enRetard);

        List<Direction> directions = directionRepository.findAll();
        for (Direction direction : directions) {
            List<Activite> activitesDirection = toutes.stream()
                    .filter(a -> a.getDirection() != null
                            && a.getDirection().getId().equals(direction.getId()))
                    .toList();

            long totalDir = activitesDirection.size();
            if (totalDir == 0) continue;

            long termineesDir = activitesDirection.stream()
                    .filter(a -> a.getStatut() == StatutActivite.TERMINEE)
                    .count();

            long enRetardDir = activitesDirection.stream()
                    .filter(a -> a.getDateFinPrevue() != null
                            && a.getDateFinPrevue().isBefore(LocalDate.now())
                            && a.getStatut() != StatutActivite.TERMINEE)
                    .count();

            float tauxExecutionDir = (float) termineesDir / totalDir * 100;
            float tauxRetardDir = (float) enRetardDir / totalDir * 100;

            Kpi kpiDir = new Kpi();
            kpiDir.setTauxExecutionGlobal(tauxExecutionDir);
            kpiDir.setTauxRespectDelais(100 - tauxRetardDir);
            kpiDir.setNbActivitesTerminees((int) termineesDir);
            kpiDir.setNbActivitesRetard((int) enRetardDir);
            kpiDir.setNbActivitesTotal((int) totalDir);
            kpiDir.setDirection(direction);
            kpiRepository.save(kpiDir);

            log.info("KPI direction {} : {}% exécution", direction.getNom(), tauxExecutionDir);
        }
    }
}
