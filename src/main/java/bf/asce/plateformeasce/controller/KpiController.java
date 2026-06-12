package bf.asce.plateformeasce.controller;

import bf.asce.plateformeasce.dto.KpiActiviteDTO;
import bf.asce.plateformeasce.entity.Kpi;
import bf.asce.plateformeasce.service.KpiService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/kpi")
public class KpiController {

    private final KpiService kpiService;

    public KpiController(KpiService kpiService) {
        this.kpiService = kpiService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CHEF_DIRECTION','ROLE_RESPONSABLE_ASCE')")
    public List<Kpi> findAll() {
        return kpiService.findAll();
    }

    @GetMapping("/dernier")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CHEF_DIRECTION','ROLE_RESPONSABLE_ASCE')")
    public ResponseEntity<Kpi> findDernier() {
        return kpiService.findDernier()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CHEF_DIRECTION','ROLE_RESPONSABLE_ASCE')")
    public ResponseEntity<Kpi> findById(@PathVariable Long id) {
        return kpiService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/direction/{directionId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CHEF_DIRECTION','ROLE_RESPONSABLE_ASCE')")
    public ResponseEntity<Kpi> findKpiParDirection(@PathVariable Long directionId) {
        return kpiService.findDernierKpiParDirection(directionId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/activite/{activiteId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CHEF_DIRECTION','ROLE_RESPONSABLE_ASCE')")
    public ResponseEntity<KpiActiviteDTO> findKpiParActivite(@PathVariable Long activiteId) {
        KpiActiviteDTO kpi = kpiService.calculerKpiActivite(activiteId);
        if (kpi == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(kpi);
    }
}
