package hu.unideb.timi15.mybookshelf.service;

import hu.unideb.timi15.mybookshelf.service.dto.dashboard.DashboardDTO;

public interface DashboardService {

    DashboardDTO getDashboard(Integer year, String token);

}
