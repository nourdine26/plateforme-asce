package bf.asce.plateformeasce.service;

import bf.asce.plateformeasce.dto.KpiActiviteDTO;
import bf.asce.plateformeasce.entity.Activite;
import bf.asce.plateformeasce.entity.Kpi;
import bf.asce.plateformeasce.enums.StatutActivite;
import bf.asce.plateformeasce.repository.ActiviteRepository;
import bf.asce.plateformeasce.repository.KpiRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class KpiService {

    private final KpiRepository kpiRepository;
    private final ActiviteRepository activiteRepository;

    public KpiService(KpiRepository kpiRepository, ActiviteRepository activiteRepository) {
        this.kpiRepository = kpiRepository;
        this.activiteRepository = activiteRepository;
    }

    public List<Kpi> findAll() {
        return kpiRepository.findAll();
    }

    public Optional<Kpi> findDernier() {
        List<Kpi> kpis = kpiRepository.findAll();
        if (kpis.isEmpty()) return Optional.empty();
        return Optional.of(kpis.get(kpis.size() - 1));
    }

    public Optional<Kpi> findById(Long id) {
        return kpiRepository.findById(id);
    }

    public Optional<Kpi> findDernierKpiParDirection(Long directionId) {
        return kpiRepository.findDernierByDirectionId(directionId);
    }

    public KpiActiviteDTO calculerKpiActivite(Long activiteId) {
        Optional<Activite> opt = activiteRepository.findById(activiteId);
        if (opt.isEmpty()) return null;

        Activite activite = opt.get();

        float tauxAvancement = activite.getTauxExecution() != null ? activite.getTauxExecution() : 0f;

        boolean enRetard = activite.getDateFinPrevue() != null
                && java.time.LocalDate.now().isAfter(activite.getDateFinPrevue())
                && activite.getStatut() != StatutActivite.TERMINEE;
        float tauxRetard = enRetard ? 100f : 0f;

        float tauxRespect;
        if (activite.getStatut() == StatutActivite.TERMINEE) {
            if (activite.getDateFinReelle() != null && activite.getDateFinPrevue() != null
                    && !activite.getDateFinReelle().isAfter(activite.getDateFinPrevue())) {
                tauxRespect = 100f;
            } else {
                tauxRespect = 0f;
            }
        } else if (enRetard) {
            tauxRespect = 0f;
        } else {
            tauxRespect = 100f;
        }

        return new KpiActiviteDTO(tauxAvancement, tauxRetard, tauxRespect, activite.getStatut().name());
    }
}
