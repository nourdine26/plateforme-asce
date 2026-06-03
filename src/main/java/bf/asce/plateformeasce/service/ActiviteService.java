package bf.asce.plateformeasce.service;

import bf.asce.plateformeasce.entity.Activite;
import bf.asce.plateformeasce.repository.ActiviteRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ActiviteService {

    private final ActiviteRepository activiteRepository;

    public ActiviteService(ActiviteRepository activiteRepository) {
        this.activiteRepository = activiteRepository;
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

    public void deleteActivite(Long id) {
        activiteRepository.deleteById(id);
    }
}