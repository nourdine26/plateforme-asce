package bf.asce.plateformeasce.service;

import bf.asce.plateformeasce.entity.Activite;
import bf.asce.plateformeasce.entity.Alerte;
import bf.asce.plateformeasce.entity.Kpi;
import bf.asce.plateformeasce.enums.StatutActivite;
import bf.asce.plateformeasce.repository.ActiviteRepository;
import bf.asce.plateformeasce.repository.AlerteRepository;
import bf.asce.plateformeasce.repository.KpiRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DashboardService {

    private final ActiviteRepository activiteRepository;
    private final AlerteRepository alerteRepository;
    private final KpiRepository kpiRepository;

    public DashboardService(ActiviteRepository activiteRepository,
                            AlerteRepository alerteRepository,
                            KpiRepository kpiRepository) {
        this.activiteRepository = activiteRepository;
        this.alerteRepository = alerteRepository;
        this.kpiRepository = kpiRepository;
    }

    public Map<String, Object> getDashboard() {
        List<Activite> toutes = activiteRepository.findAll();
        long total = toutes.size();

        long terminees = toutes.stream()
                .filter(a -> a.getStatut() == StatutActivite.TERMINEE)
                .count();

        long enCours = toutes.stream()
                .filter(a -> a.getStatut() == StatutActivite.EN_COURS)
                .count();

        long nonDemarrees = toutes.stream()
                .filter(a -> a.getStatut() == StatutActivite.NON_DEMARREE)
                .count();

        long bloquees = toutes.stream()
                .filter(a -> a.getStatut() == StatutActivite.BLOQUEE)
                .count();

        long enRetard = toutes.stream()
                .filter(a -> a.getDateFinPrevue() != null
                        && a.getDateFinPrevue().isBefore(LocalDate.now())
                        && a.getStatut() != StatutActivite.TERMINEE)
                .count();

        float tauxExecution = total == 0 ? 0 : (float) terminees / total * 100;
        float tauxRetard = total == 0 ? 0 : (float) enRetard / total * 100;

        // Dernier KPI calculé
        List<Kpi> kpis = kpiRepository.findAll();
        Optional<Kpi> dernierKpi = kpis.isEmpty()
                ? Optional.empty()
                : Optional.of(kpis.get(kpis.size() - 1));

        // Nombre d'alertes
        long nbAlertes = alerteRepository.count();

        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("totalActivites", total);
        dashboard.put("activitesTerminees", terminees);
        dashboard.put("activitesEnCours", enCours);
        dashboard.put("activitesNonDemarrees", nonDemarrees);
        dashboard.put("activitesBloquees", bloquees);
        dashboard.put("activitesEnRetard", enRetard);
        dashboard.put("tauxExecutionGlobal", Math.round(tauxExecution * 10.0) / 10.0);
        dashboard.put("tauxRetard", Math.round(tauxRetard * 10.0) / 10.0);
        dashboard.put("nbAlertes", nbAlertes);
        dernierKpi.ifPresent(k -> dashboard.put("dernierKpi", k));

        return dashboard;
    }
}