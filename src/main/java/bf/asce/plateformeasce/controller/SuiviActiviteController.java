package bf.asce.plateformeasce.controller;

import bf.asce.plateformeasce.entity.SuiviActivite;
import bf.asce.plateformeasce.service.SuiviActiviteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/suivis")
public class SuiviActiviteController {

    private final SuiviActiviteService suiviActiviteService;

    public SuiviActiviteController(SuiviActiviteService suiviActiviteService) {
        this.suiviActiviteService = suiviActiviteService;
    }

    @GetMapping
    public List<SuiviActivite> getAllSuivis() {
        return suiviActiviteService.getAllSuivis();
    }

    @GetMapping("/activite/{activiteId}")
    public List<SuiviActivite> getSuivisByActivite(@PathVariable Long activiteId) {
        return suiviActiviteService.getSuivisByActivite(activiteId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuiviActivite> getSuiviById(@PathVariable Long id) {
        return suiviActiviteService.getSuiviById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SuiviActivite createSuivi(@RequestBody SuiviActivite suivi) {
        return suiviActiviteService.saveSuivi(suivi);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuivi(@PathVariable Long id) {
        suiviActiviteService.deleteSuivi(id);
        return ResponseEntity.noContent().build();
    }
}