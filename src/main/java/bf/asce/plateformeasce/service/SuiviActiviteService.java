package bf.asce.plateformeasce.service;

import bf.asce.plateformeasce.entity.SuiviActivite;
import bf.asce.plateformeasce.repository.SuiviActiviteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SuiviActiviteService {

    private final SuiviActiviteRepository suiviActiviteRepository;

    public SuiviActiviteService(SuiviActiviteRepository suiviActiviteRepository) {
        this.suiviActiviteRepository = suiviActiviteRepository;
    }

    public List<SuiviActivite> getAllSuivis() {
        return suiviActiviteRepository.findAll();
    }

    public List<SuiviActivite> getSuivisByActivite(Long activiteId) {
        return suiviActiviteRepository.findByActiviteId(activiteId);
    }

    public Optional<SuiviActivite> getSuiviById(Long id) {
        return suiviActiviteRepository.findById(id);
    }

    public SuiviActivite saveSuivi(SuiviActivite suivi) {
        return suiviActiviteRepository.save(suivi);
    }

    public void deleteSuivi(Long id) {
        suiviActiviteRepository.deleteById(id);
    }
}