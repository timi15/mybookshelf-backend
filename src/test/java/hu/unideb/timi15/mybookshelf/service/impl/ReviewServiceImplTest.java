package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.data.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.data.repository.BookReviewRepository;
import hu.unideb.timi15.mybookshelf.exception.AlreadyExistException;
import hu.unideb.timi15.mybookshelf.exception.NotFoundException;
import hu.unideb.timi15.mybookshelf.mapper.BookMapper;
import hu.unideb.timi15.mybookshelf.mapper.BookReviewMapper;
import hu.unideb.timi15.mybookshelf.service.BookService;
import hu.unideb.timi15.mybookshelf.service.dto.book.BookResDto;
import hu.unideb.timi15.mybookshelf.service.dto.review.CreateReviewReqDto;
import hu.unideb.timi15.mybookshelf.service.dto.review.ReviewResDto;
import hu.unideb.timi15.mybookshelf.service.dto.review.UpdateReviewReqDto;
import hu.unideb.timi15.mybookshelf.utils.FirebaseAuthUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    BookReviewRepository bookReviewRepository;

    @Mock
    BookService bookService;

    @Mock
    BookReviewMapper bookReviewMapper;

    @Mock
    BookMapper bookMapper;

    @InjectMocks
    ReviewServiceImpl reviewService;

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
    void save_newReview_savesSuccessfully() {
        CreateReviewReqDto dto = new CreateReviewReqDto();
        dto.setIsbn13("1111111111111");

        BookReviewEntity entity = new BookReviewEntity();
        entity.setIsbn13("1111111111111");

        BookResDto bookDto = new BookResDto();
        bookDto.setIsbn13("1111111111111");

        when(bookReviewRepository.existsByUserIdAndIsbn13("user1", "1111111111111"))
                .thenReturn(Mono.empty());

        when(bookMapper.toEntity(any()))
                .thenReturn(new BookEntity());

        when(bookService.getOrCreateBook(any(), eq("user1")))
                .thenReturn(bookDto);

        when(bookReviewMapper.toEntity(dto))
                .thenReturn(entity);

        when(bookReviewRepository.save(any()))
                .thenReturn(Mono.just(entity));

        when(bookReviewMapper.toResponseDto(entity))
                .thenReturn(new ReviewResDto());

        ReviewResDto result = reviewService.save("token", dto);

        assertNotNull(result);
        verify(bookReviewRepository).save(any());
    }

    @Test
    void save_duplicateReview_throwsException() {
        CreateReviewReqDto dto = new CreateReviewReqDto();
        dto.setIsbn13("1111111111111");

        when(bookReviewRepository.existsByUserIdAndIsbn13("user1", "1111111111111"))
                .thenReturn(Mono.just(true));

        assertThrows(AlreadyExistException.class,
                () -> reviewService.save("token", dto));
    }

    @Test
    void findByIsbn_notFound_throwsException() {
        when(bookReviewRepository.findByUserIdAndIsbn13("user1", "1111111111111"))
                .thenReturn(Mono.empty());

        assertThrows(NotFoundException.class,
                () -> reviewService.findByIsbn("token", "1111111111111"));
    }

    @Test
    void update_existingReview_updatesSuccessfully() {
        String isbn = "1111111111111";

        UpdateReviewReqDto dto = new UpdateReviewReqDto();

        BookReviewEntity existing = new BookReviewEntity();
        existing.setIsbn13(isbn);
        existing.setDocumentId("doc1");

        BookResDto bookDto = new BookResDto();
        bookDto.setIsbn13(isbn);

        when(bookReviewRepository.findByUserIdAndIsbn13("user1", isbn))
                .thenReturn(Mono.just(existing));

        when(bookReviewRepository.save(existing))
                .thenReturn(Mono.just(existing));

        when(bookService.findByIsbn13(isbn))
                .thenReturn(bookDto);

        when(bookReviewMapper.toResponseDto(existing))
                .thenReturn(new ReviewResDto());

        ReviewResDto result = reviewService.update("token", isbn, dto);

        assertNotNull(result);
        verify(bookReviewMapper).updateEntityFromDto(dto, existing);
        verify(bookReviewRepository).save(existing);
    }

    @Test
    void update_notExistingReview_throwsException() {
        when(bookReviewRepository.findByUserIdAndIsbn13("user1", "1111111111111"))
                .thenReturn(Mono.empty());

        assertThrows(NotFoundException.class,
                () -> reviewService.update("token", "1111111111111", new UpdateReviewReqDto()));
    }

    @Test
    void delete_existingReview_deletesSuccessfully() {
        String isbn = "1111111111111";

        BookReviewEntity entity = new BookReviewEntity();
        entity.setIsbn13(isbn);
        entity.setDocumentId("doc1");

        when(bookReviewRepository.findByUserIdAndIsbn13("user1", isbn))
                .thenReturn(Mono.just(entity));

        when(bookReviewRepository.delete(entity))
                .thenReturn(Mono.empty());

        reviewService.deleteByIsbn("token", isbn);

        verify(bookReviewRepository).delete(entity);
    }

    @Test
    void delete_notExistingReview_throwsException() {
        when(bookReviewRepository.findByUserIdAndIsbn13("user1", "1111111111111"))
                .thenReturn(Mono.empty());

        assertThrows(NotFoundException.class,
                () -> reviewService.deleteByIsbn("token", "1111111111111"));
    }

}
