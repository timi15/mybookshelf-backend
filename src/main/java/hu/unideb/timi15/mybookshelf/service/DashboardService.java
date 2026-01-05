package hu.unideb.timi15.mybookshelf.service;

import hu.unideb.timi15.mybookshelf.service.dto.dashboard.DashboardDto;

public interface DashboardService {

    DashboardDto getDashboard(Integer year, String token);

}
