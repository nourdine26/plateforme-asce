package bf.asce.plateformeasce.controller;

import bf.asce.plateformeasce.service.DashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CHEF_DIRECTION','ROLE_RESPONSABLE_ASCE')")
    public Map<String, Object> getDashboard() {
        return dashboardService.getDashboard();
    }
}