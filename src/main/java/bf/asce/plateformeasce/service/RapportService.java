package bf.asce.plateformeasce.service;

import bf.asce.plateformeasce.entity.Rapport;
import bf.asce.plateformeasce.repository.RapportRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RapportService {

    private final RapportRepository rapportRepository;

    public RapportService(RapportRepository rapportRepository) {
        this.rapportRepository = rapportRepository;
    }

    public List<Rapport> getAllRapports() {
        return rapportRepository.findAll();
    }

    public List<Rapport> getRapportsByActivite(Long activiteId) {
        return rapportRepository.findByActiviteId(activiteId);
    }

    public Optional<Rapport> getRapportById(Long id) {
        return rapportRepository.findById(id);
    }

    public Rapport saveRapport(Rapport rapport) {
        return rapportRepository.save(rapport);
    }

    public void deleteRapport(Long id) {
        rapportRepository.deleteById(id);
    }
}