package bf.asce.plateformeasce.repository;

import bf.asce.plateformeasce.entity.SuiviActivite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SuiviActiviteRepository extends JpaRepository<SuiviActivite, Long> {

    List<SuiviActivite> findByActiviteId(Long activiteId);
}