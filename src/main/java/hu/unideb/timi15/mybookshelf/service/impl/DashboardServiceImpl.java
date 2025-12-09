package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.data.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.data.repository.BookRepository;
import hu.unideb.timi15.mybookshelf.data.repository.BookReviewRepository;
import hu.unideb.timi15.mybookshelf.data.repository.LovedListRepository;
import hu.unideb.timi15.mybookshelf.service.BookReviewService;
import hu.unideb.timi15.mybookshelf.service.DashboardService;
import hu.unideb.timi15.mybookshelf.service.dto.dashboard.DashboardDTO;
import hu.unideb.timi15.mybookshelf.service.dto.review.response.BookReviewResponseDTO;
import hu.unideb.timi15.mybookshelf.utils.FirebaseAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final BookRepository bookRepository;
    private final LovedListRepository lovedListRepository;
    private final BookReviewRepository reviewRepository;
    private final BookReviewService bookReviewService;

    @Override
    public DashboardDTO getDashboard(Integer year, String token) {

        String userId = FirebaseAuthUtil.getUserId(token);

        Long totalBooksCount = getTotalBooksCount(userId);
        Long lovedBooksCount = getLovedBooksCount(userId);
        String lastReadBookImage = getLastReadBook(userId);
        List<String> top5BookImage = getTop5Book(userId);
        List<String> top3Genres = getTop3Genres(token);
        Map<String, Long> genreStat = getGenreStat(token);
        Map<String, Long> monthlyStats = getMonthlyStats(token, year);

        return DashboardDTO.builder()
                .totalBooks(totalBooksCount)
                .totalLovedBooks(lovedBooksCount)
                .lastReadBookImage(lastReadBookImage)
                .top5BookImage(top5BookImage)
                .top3Genres(top3Genres)
                .genreStat(genreStat)
                .monthlyStat(monthlyStats)
                .build();
    }


    private Long getTotalBooksCount(String userId) {
        return reviewRepository.countAllByUserId(userId).block();
    }

    private Long getLovedBooksCount(String userId) {
        return lovedListRepository.countAllByUserId(userId).block();
    }

    private String getLastReadBook(String userId) {

        BookReviewEntity lastReview = reviewRepository
                .findByUserIdOrderByFinishDateDesc(userId, Limit.of(1))
                .block();

        if (lastReview == null) return null;

        return bookRepository.findByIsbn13(lastReview.getIsbn13()).block().getImage();
    }

    private List<String> getTop5Book(String userId) {
        List<String> result = new ArrayList<>();

        List<BookReviewEntity> reviews = reviewRepository.findByUserIdOrderByRateDesc(userId, Limit.of(5)).collectList().block();
        for (BookReviewEntity review : reviews) {
            result.add(bookRepository.findByIsbn13(review.getIsbn13()).block().getImage());
        }

        return result;
    }

    private Map<String, Long> getGenreStat(String token) {
        List<BookReviewResponseDTO> reviews = bookReviewService.findAll(token);

        Map<String, Long> result = new HashMap<>();

        if (reviews != null) {

            for (BookReviewResponseDTO review : reviews) {
                for (String genre : review.getGenres()) {
                    result.put(genre, result.getOrDefault(genre, 0L) + 1);
                }
            }
        }
        return result;
    }

    private List<String> getTop3Genres(String token) {

        Map<String, Long> stat = getGenreStat(token);

        return stat.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
    }

    private Map<String, Long> getMonthlyStats(String token, Integer year) {

        List<BookReviewResponseDTO> reviews = bookReviewService.findAll(token);

        Map<String, Long> result = new HashMap<>();

        if (reviews == null) return result;

        for (BookReviewResponseDTO review : reviews) {
            String month = review.getFinishDate().getMonth().toString().substring(0,3);
            if (review.getFinishDate().getYear() == year) {
                result.put(month, result.getOrDefault(month, 0L) + 1);
            }
        }

        return result;
    }

}
