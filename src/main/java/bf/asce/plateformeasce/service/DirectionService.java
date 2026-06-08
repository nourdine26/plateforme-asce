package bf.asce.plateformeasce.service;

import bf.asce.plateformeasce.entity.Direction;
import bf.asce.plateformeasce.repository.DirectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DirectionService {

    private final DirectionRepository directionRepository;
    private final JournalSystemService journalSystemService;

    public DirectionService(DirectionRepository directionRepository,
                            JournalSystemService journalSystemService) {
        this.directionRepository = directionRepository;
        this.journalSystemService = journalSystemService;
    }

    // Récupérer toutes les directions
    public List<Direction> getAllDirections() {
        return directionRepository.findAll();
    }

    // Récupérer une direction par son id
    public Optional<Direction> getDirectionById(Long id) {
        return directionRepository.findById(id);
    }

    // Créer ou mettre à jour une direction
    public Direction saveDirection(Direction direction, String email, String ip) {
        Direction saved = directionRepository.save(direction);
        journalSystemService.enregistrer(
                "Création/modification direction : " + saved.getNom(),
                ip,
                email
        );
        return saved;
    }

    // Supprimer une direction par son id
    public void deleteDirection(Long id, String email, String ip) {
        directionRepository.deleteById(id);
        journalSystemService.enregistrer(
                "Suppression direction id : " + id,
                ip,
                email
        );
    }
}