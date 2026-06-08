package bf.asce.plateformeasce.service;

import bf.asce.plateformeasce.entity.Kpi;
import bf.asce.plateformeasce.repository.KpiRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KpiService {

    private final KpiRepository kpiRepository;

    public KpiService(KpiRepository kpiRepository) {
        this.kpiRepository = kpiRepository;
    }

    // Récupérer tous les KPI
    public List<Kpi> findAll() {
        return kpiRepository.findAll();
    }

    // Récupérer le dernier KPI calculé
    public Optional<Kpi> findDernier() {
        List<Kpi> kpis = kpiRepository.findAll();
        if (kpis.isEmpty()) return Optional.empty();
        return Optional.of(kpis.get(kpis.size() - 1));
    }

    // Récupérer un KPI par id
    public Optional<Kpi> findById(Long id) {
        return kpiRepository.findById(id);
    }
}