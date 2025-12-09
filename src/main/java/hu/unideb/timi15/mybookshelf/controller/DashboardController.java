package hu.unideb.timi15.mybookshelf.controller;

import hu.unideb.timi15.mybookshelf.service.DashboardService;
import hu.unideb.timi15.mybookshelf.service.dto.dashboard.DashboardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/mybookshelf/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/{year}")
    public DashboardDTO getDashboard(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer year
    ) {
        return dashboardService.getDashboard(year, token);
    }
}
