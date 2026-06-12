package bf.asce.plateformeasce.repository;

import bf.asce.plateformeasce.entity.Kpi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface KpiRepository extends JpaRepository<Kpi, Long> {

    List<Kpi> findByDirectionId(Long directionId);

    @Query("SELECT k FROM Kpi k WHERE k.direction.id = :directionId ORDER BY k.id DESC LIMIT 1")
    Optional<Kpi> findDernierByDirectionId(@Param("directionId") Long directionId);
}
