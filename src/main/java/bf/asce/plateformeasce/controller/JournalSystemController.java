package bf.asce.plateformeasce.controller;

import bf.asce.plateformeasce.entity.JournalSystem;
import bf.asce.plateformeasce.service.JournalSystemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journal")
public class JournalSystemController {

    private final JournalSystemService journalSystemService;

    public JournalSystemController(JournalSystemService journalSystemService) {
        this.journalSystemService = journalSystemService;
    }

    // Tous les journaux — admin seulement
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<JournalSystem> findAll() {
        return journalSystemService.findAll();
    }

    // Journaux d'un utilisateur — admin seulement
    @GetMapping("/utilisateur/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<JournalSystem> findByUtilisateur(@PathVariable Long id) {
        return journalSystemService.findByUtilisateur(id);
    }
}