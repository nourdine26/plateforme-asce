package bf.asce.plateformeasce.repository;

import bf.asce.plateformeasce.entity.Programme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgrammeRepository extends JpaRepository<Programme, Long> {
}