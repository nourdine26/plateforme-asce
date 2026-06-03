package bf.asce.plateformeasce.controller;

import bf.asce.plateformeasce.entity.Programme;
import bf.asce.plateformeasce.service.ProgrammeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/programmes")
public class ProgrammeController {

    private final ProgrammeService programmeService;

    public ProgrammeController(ProgrammeService programmeService) {
        this.programmeService = programmeService;
    }

    @GetMapping
    public List<Programme> getAllProgrammes() {
        return programmeService.getAllProgrammes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Programme> getProgrammeById(@PathVariable Long id) {
        return programmeService.getProgrammeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Programme createProgramme(@RequestBody Programme programme) {
        return programmeService.saveProgramme(programme);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgramme(@PathVariable Long id) {
        programmeService.deleteProgramme(id);
        return ResponseEntity.noContent().build();
    }
}