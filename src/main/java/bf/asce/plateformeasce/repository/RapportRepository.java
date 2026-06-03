package bf.asce.plateformeasce.repository;

import bf.asce.plateformeasce.entity.Rapport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RapportRepository extends JpaRepository<Rapport, Long> {

    // Récupère tous les rapports d'une activité donnée
    List<Rapport> findByActiviteId(Long activiteId);
}