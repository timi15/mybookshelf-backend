package hu.unideb.timi15.mybookshelf.service.dto.dashboard;

import lombok.Data;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardDto {
    private long numberOfReadBooks;
    private long numberOfFavouriteBooks;
    private String lastReadBookCoverUrl;
    private List<String> top5BookCoverUrls;
    private List<String> top3Genres;
    private Map<String, Long> genreStats;
    private Map<String, Long> monthlyReadingStats;
}
