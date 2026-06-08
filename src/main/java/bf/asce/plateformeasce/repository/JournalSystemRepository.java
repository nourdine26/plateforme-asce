package bf.asce.plateformeasce.repository;

import bf.asce.plateformeasce.entity.JournalSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalSystemRepository extends JpaRepository<JournalSystem, Long> {
    List<JournalSystem> findByUtilisateurId(Long utilisateurId);
    List<JournalSystem> findAllByOrderByDateActionDesc();
}