package bf.asce.plateformeasce.repository;

import bf.asce.plateformeasce.entity.Alerte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlerteRepository extends JpaRepository<Alerte, Long> {
}