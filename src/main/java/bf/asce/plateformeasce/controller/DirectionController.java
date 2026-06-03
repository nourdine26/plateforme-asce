package bf.asce.plateformeasce.controller;

import bf.asce.plateformeasce.entity.Direction;
import bf.asce.plateformeasce.service.DirectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/directions")
public class DirectionController {

    private final DirectionService directionService;

    public DirectionController(DirectionService directionService) {
        this.directionService = directionService;
    }

    // GET /api/directions  -> liste toutes les directions
    @GetMapping
    public List<Direction> getAllDirections() {
        return directionService.getAllDirections();
    }

    // GET /api/directions/1  -> une direction par son id
    @GetMapping("/{id}")
    public ResponseEntity<Direction> getDirectionById(@PathVariable Long id) {
        return directionService.getDirectionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/directions  -> créer une direction
    @PostMapping
    public Direction createDirection(@RequestBody Direction direction) {
        return directionService.saveDirection(direction);
    }

    // DELETE /api/directions/1  -> supprimer une direction
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDirection(@PathVariable Long id) {
        directionService.deleteDirection(id);
        return ResponseEntity.noContent().build();
    }
}