package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.data.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.data.repository.BookRepository;
import hu.unideb.timi15.mybookshelf.data.repository.BookReviewRepository;
import hu.unideb.timi15.mybookshelf.data.repository.LovedListRepository;
import hu.unideb.timi15.mybookshelf.service.ReviewService;
import hu.unideb.timi15.mybookshelf.service.DashboardService;
import hu.unideb.timi15.mybookshelf.service.dto.dashboard.DashboardDto;
import hu.unideb.timi15.mybookshelf.service.dto.review.ReviewResDto;
import hu.unideb.timi15.mybookshelf.utils.FirebaseAuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final BookRepository bookRepository;
    private final LovedListRepository lovedListRepository;
    private final BookReviewRepository reviewRepository;
    private final ReviewService reviewService;

    @Override
    public DashboardDto getDashboard(Integer year, String token) {

        log.info("getDashboard called for year={}", year);

        String userId = FirebaseAuthUtil.getUserId(token);

        Long numberOfReadBooks = getNumberOfReadBooks(userId);
        Long numberOfFavouriteBooks = getNumberOfFavouriteBooks(userId);
        String lastReadBookCoverUrl = getLastReadBookCoverUrl(userId);
        List<String> top5BookCoverUrls = getTop5BookCoverUrls(userId);
        List<String> top3Genres = getTop3Genres(token);
        Map<String, Long> genreStats = getGenreStats(token);
        Map<String, Long> monthlyReadingStats = getMonthlyReadingStats(token, year);

        log.info("Dashboard data generated for userId={}", userId);

        return DashboardDto.builder()
                .numberOfReadBooks(numberOfReadBooks)
                .numberOfFavouriteBooks(numberOfFavouriteBooks)
                .lastReadBookCoverUrl(lastReadBookCoverUrl)
                .top5BookCoverUrls(top5BookCoverUrls)
                .top3Genres(top3Genres)
                .genreStats(genreStats)
                .monthlyReadingStats(monthlyReadingStats)
                .build();
    }


    private Long getNumberOfReadBooks(String userId) {
        return reviewRepository.countAllByUserId(userId).block();
    }

    private Long getNumberOfFavouriteBooks(String userId) {
        return lovedListRepository.countAllByUserId(userId).block();
    }

    private String getLastReadBookCoverUrl(String userId) {

        BookReviewEntity lastReview = reviewRepository
                .findByUserIdOrderByFinishDateDesc(userId, Limit.of(1))
                .block();

        if (lastReview == null) {
            return null;
        }

        return Optional.ofNullable(bookRepository.findByIsbn13(lastReview.getIsbn13()).block())
                .map(BookEntity::getCoverUrl)
                .orElse(null);
    }

    private List<String> getTop5BookCoverUrls(String userId) {
        List<String> result = new ArrayList<>();

        List<BookReviewEntity> reviews = reviewRepository
                .findByUserIdOrderByRateDesc(userId, Limit.of(5))
                .collectList()
                .block();

        if (reviews == null) {
            return result;
        }

        for (BookReviewEntity review : reviews) {
            Optional.ofNullable(bookRepository.findByIsbn13(review.getIsbn13()).block())
                    .map(BookEntity::getCoverUrl)
                    .ifPresent(result::add);
        }

        return result;
    }

    private Map<String, Long> getGenreStats(String token) {

        List<ReviewResDto> reviews = Optional.ofNullable(reviewService.findAll(token))
                .orElse(Collections.emptyList());

        Map<String, Long> result = new HashMap<>();

        for (ReviewResDto review : reviews) {
            for (String genre : review.getGenres()) {
                result.put(genre, result.getOrDefault(genre, 0L) + 1);
            }
        }

        return result;
    }

    private List<String> getTop3Genres(String token) {

        Map<String, Long> stat = getGenreStats(token);

        return stat.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
    }

    private Map<String, Long> getMonthlyReadingStats(String token, Integer year) {

        List<ReviewResDto> reviews = Optional.ofNullable(reviewService.findAll(token))
                .orElse(Collections.emptyList());

        Map<String, Long> result = new HashMap<>();

        for (ReviewResDto review : reviews) {
            String month = review.getFinishDate().getMonth().toString().substring(0, 3);
            if (review.getFinishDate().getYear() == year) {
                result.put(month, result.getOrDefault(month, 0L) + 1);
            }
        }

        return result;
    }

}
