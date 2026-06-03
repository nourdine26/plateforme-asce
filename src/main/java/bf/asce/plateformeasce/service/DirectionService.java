package bf.asce.plateformeasce.service;

import bf.asce.plateformeasce.entity.Direction;
import bf.asce.plateformeasce.repository.DirectionRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DirectionService {

    private final DirectionRepository directionRepository;

    // Injection de dépendances : Spring fournit le repository automatiquement
    public DirectionService(DirectionRepository directionRepository) {
        this.directionRepository = directionRepository;
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
    public Direction saveDirection(Direction direction) {
        return directionRepository.save(direction);
    }

    // Supprimer une direction par son id
    public void deleteDirection(Long id) {
        directionRepository.deleteById(id);
    }
}