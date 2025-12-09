package hu.unideb.timi15.mybookshelf.service.dto.dashboard;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardDTO {
    private long totalBooks;
    private long totalLovedBooks;
    private String lastReadBookImage;
    private List<String> top5BookImage;
    private List<String> top3Genres;
    private Map<String, Long> genreStat;
    private Map<String, Long> monthlyStat;
}
