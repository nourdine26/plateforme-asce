package bf.asce.plateformeasce.repository;

import bf.asce.plateformeasce.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {

    Optional<Direction> findByCode(String code);
}