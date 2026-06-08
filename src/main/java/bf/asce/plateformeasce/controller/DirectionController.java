package bf.asce.plateformeasce.controller;

import bf.asce.plateformeasce.entity.Direction;
import bf.asce.plateformeasce.service.DirectionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/directions")
public class DirectionController {

    private final DirectionService directionService;

    public DirectionController(DirectionService directionService) {
        this.directionService = directionService;
    }

    // GET /api/directions → liste toutes les directions
    @GetMapping
    public List<Direction> getAllDirections() {
        return directionService.getAllDirections();
    }

    // GET /api/directions/1 → une direction par son id
    @GetMapping("/{id}")
    public ResponseEntity<Direction> getDirectionById(@PathVariable Long id) {
        return directionService.getDirectionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/directions → créer une direction
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Direction createDirection(@RequestBody Direction direction,
                                     @AuthenticationPrincipal Jwt jwt,
                                     HttpServletRequest request) {
        String email = jwt.getClaim("email");
        String ip = request.getRemoteAddr();
        return directionService.saveDirection(direction, email, ip);
    }

    // DELETE /api/directions/1 → supprimer une direction
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteDirection(@PathVariable Long id,
                                                @AuthenticationPrincipal Jwt jwt,
                                                HttpServletRequest request) {
        String email = jwt.getClaim("email");
        String ip = request.getRemoteAddr();
        directionService.deleteDirection(id, email, ip);
        return ResponseEntity.noContent().build();
    }
}