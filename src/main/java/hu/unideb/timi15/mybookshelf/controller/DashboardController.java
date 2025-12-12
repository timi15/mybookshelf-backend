package hu.unideb.timi15.mybookshelf.controller;

import hu.unideb.timi15.mybookshelf.service.DashboardService;
import hu.unideb.timi15.mybookshelf.service.dto.dashboard.DashboardDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("v1/mybookshelf/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/{year}")
    public DashboardDto getDashboard(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer year
    ) {
        return dashboardService.getDashboard(year, token);
    }
}
