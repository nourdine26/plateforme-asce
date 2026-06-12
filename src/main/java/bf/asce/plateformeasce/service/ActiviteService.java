package bf.asce.plateformeasce.service;

import bf.asce.plateformeasce.entity.Activite;
import bf.asce.plateformeasce.repository.ActiviteRepository;
import bf.asce.plateformeasce.repository.AlerteRepository;
import bf.asce.plateformeasce.repository.SuiviActiviteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ActiviteService {

    private final ActiviteRepository activiteRepository;
    private final AlerteRepository alerteRepository;
    private final SuiviActiviteRepository suiviActiviteRepository;

    public ActiviteService(ActiviteRepository activiteRepository,
                           AlerteRepository alerteRepository,
                           SuiviActiviteRepository suiviActiviteRepository) {
        this.activiteRepository = activiteRepository;
        this.alerteRepository = alerteRepository;
        this.suiviActiviteRepository = suiviActiviteRepository;
    }

    public List<Activite> getAllActivites() {
        return activiteRepository.findAll();
    }

    public Optional<Activite> getActiviteById(Long id) {
        return activiteRepository.findById(id);
    }

    public Activite saveActivite(Activite activite) {
        return activiteRepository.save(activite);
    }

    @Transactional
    public void deleteActivite(Long id) {
        alerteRepository.deleteByActiviteId(id);
        suiviActiviteRepository.deleteByActiviteId(id);
        activiteRepository.deleteById(id);
    }
}