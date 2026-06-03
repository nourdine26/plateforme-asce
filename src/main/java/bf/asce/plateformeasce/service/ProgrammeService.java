package bf.asce.plateformeasce.service;

import bf.asce.plateformeasce.entity.Programme;
import bf.asce.plateformeasce.repository.ProgrammeRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProgrammeService {

    private final ProgrammeRepository programmeRepository;

    public ProgrammeService(ProgrammeRepository programmeRepository) {
        this.programmeRepository = programmeRepository;
    }

    public List<Programme> getAllProgrammes() {
        return programmeRepository.findAll();
    }

    public Optional<Programme> getProgrammeById(Long id) {
        return programmeRepository.findById(id);
    }

    public Programme saveProgramme(Programme programme) {
        return programmeRepository.save(programme);
    }

    public void deleteProgramme(Long id) {
        programmeRepository.deleteById(id);
    }
}