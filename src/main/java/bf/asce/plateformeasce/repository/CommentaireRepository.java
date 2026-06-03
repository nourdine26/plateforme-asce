package bf.asce.plateformeasce.repository;

import bf.asce.plateformeasce.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {

    List<Commentaire> findByActiviteId(Long activiteId);
}