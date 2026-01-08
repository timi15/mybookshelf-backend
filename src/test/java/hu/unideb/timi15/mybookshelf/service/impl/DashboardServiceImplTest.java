package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.data.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.data.repository.BookRepository;
import hu.unideb.timi15.mybookshelf.data.repository.BookReviewRepository;
import hu.unideb.timi15.mybookshelf.data.repository.LovedListRepository;
import hu.unideb.timi15.mybookshelf.service.ReviewService;
import hu.unideb.timi15.mybookshelf.service.dto.dashboard.DashboardDto;
import hu.unideb.timi15.mybookshelf.service.dto.review.ReviewResDto;
import hu.unideb.timi15.mybookshelf.utils.FirebaseAuthUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Limit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.eq;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    LovedListRepository lovedListRepository;

    @Mock
    BookReviewRepository reviewRepository;

    @Mock
    ReviewService reviewService;

    @InjectMocks
    DashboardServiceImpl dashboardService;

    private MockedStatic<FirebaseAuthUtil> firebaseMock;

    @BeforeEach
    void setUp() {
        firebaseMock = mockStatic(FirebaseAuthUtil.class);
        firebaseMock.when(() -> FirebaseAuthUtil.getUserId(any()))
                .thenReturn("user1");
    }

    @AfterEach
    void tearDown() {
        firebaseMock.close();
    }

    @Test
    void getDashboard_returnsDashboardData() {

        // repository mock
        when(reviewRepository.countAllByUserId("user1"))
                .thenReturn(Mono.just(5L));

        when(lovedListRepository.countAllByUserId("user1"))
                .thenReturn(Mono.just(2L));

        BookReviewEntity lastReview = new BookReviewEntity();
        lastReview.setIsbn13("1111111111111");

        when(reviewRepository.findByUserIdOrderByFinishDateDesc(eq("user1"), any(Limit.class)))
                .thenReturn(Mono.just(lastReview));

        BookEntity bookEntity = new BookEntity();
        bookEntity.setCoverUrl("cover.jpg");

        when(bookRepository.findByIsbn13("1111111111111"))
                .thenReturn(Mono.just(bookEntity));

        when(reviewRepository.findByUserIdOrderByRateDesc(eq("user1"), any(Limit.class)))
                .thenReturn(Flux.just(lastReview));

        // reviewService mock
        ReviewResDto reviewDto = new ReviewResDto();
        reviewDto.setGenres(List.of("Fantasy", "Drama"));
        reviewDto.setFinishDate(LocalDate.of(2024, 3, 10));

        when(reviewService.findAll("token"))
                .thenReturn(List.of(reviewDto));

        // call
        DashboardDto result = dashboardService.getDashboard(2024, "token");

        // assert
        assertNotNull(result);
        assertEquals(5L, result.getNumberOfReadBooks());
        assertEquals(2L, result.getNumberOfFavouriteBooks());
        assertEquals("cover.jpg", result.getLastReadBookCoverUrl());
        assertFalse(result.getTop5BookCoverUrls().isEmpty());
        assertEquals(2, result.getGenreStats().size());
        assertTrue(result.getTop3Genres().contains("Fantasy"));
        assertEquals(1L, result.getMonthlyReadingStats().get("MAR"));
    }
}
