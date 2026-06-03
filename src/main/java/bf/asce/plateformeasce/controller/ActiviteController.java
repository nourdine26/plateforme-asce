package bf.asce.plateformeasce.controller;

import bf.asce.plateformeasce.dto.ActiviteDTO;
import bf.asce.plateformeasce.dto.ActiviteMapper;
import bf.asce.plateformeasce.entity.Activite;
import bf.asce.plateformeasce.service.ActiviteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/activites")
public class ActiviteController {

    private final ActiviteService activiteService;
    private final ActiviteMapper activiteMapper;

    public ActiviteController(ActiviteService activiteService, ActiviteMapper activiteMapper) {
        this.activiteService = activiteService;
        this.activiteMapper = activiteMapper;
    }

    @GetMapping
    public List<ActiviteDTO> getAllActivites() {
        return activiteService.getAllActivites()
                .stream()
                .map(activiteMapper::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ActiviteDTO> getActiviteById(@PathVariable Long id) {
        return activiteService.getActiviteById(id)
                .map(activiteMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // La création garde l'entité (on envoie une vraie Activite à enregistrer)
    @PostMapping
    public Activite createActivite(@RequestBody Activite activite) {
        return activiteService.saveActivite(activite);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivite(@PathVariable Long id) {
        activiteService.deleteActivite(id);
        return ResponseEntity.noContent().build();
    }
}