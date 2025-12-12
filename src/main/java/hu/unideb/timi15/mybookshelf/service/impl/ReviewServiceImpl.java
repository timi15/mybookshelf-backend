package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.data.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.exception.AlreadyExistException;
import hu.unideb.timi15.mybookshelf.exception.NotFoundException;
import hu.unideb.timi15.mybookshelf.mapper.BookMapper;
import hu.unideb.timi15.mybookshelf.mapper.BookReviewMapper;
import hu.unideb.timi15.mybookshelf.data.repository.BookReviewRepository;
import hu.unideb.timi15.mybookshelf.service.ReviewService;
import hu.unideb.timi15.mybookshelf.service.BookService;
import hu.unideb.timi15.mybookshelf.service.dto.book.BookResDto;
import hu.unideb.timi15.mybookshelf.service.dto.review.CreateReviewReqDto;
import hu.unideb.timi15.mybookshelf.service.dto.review.ReviewResDto;
import hu.unideb.timi15.mybookshelf.service.dto.review.UpdateReviewReqDto;
import hu.unideb.timi15.mybookshelf.utils.FirebaseAuthUtil;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookService bookService;
    private final BookReviewMapper bookReviewMapper;
    private final BookMapper bookMapper;

    @Override
    public ReviewResDto save(String token, CreateReviewReqDto dto) {
        String userId = FirebaseAuthUtil.getUserId(token);
        validateIsbnUnique(userId, dto.getIsbn13());

        BookResDto savedBook = bookService.addOrGetBook(
                bookMapper.toEntity(dto.getBook()),
                userId
        );

        BookReviewEntity reviewEntity = bookReviewMapper.toEntity(dto);
        reviewEntity.setUserId(userId);
        reviewEntity.setIsbn13(savedBook.getIsbn13());

        BookReviewEntity saved = bookReviewRepository.save(reviewEntity).block();

        ReviewResDto response = bookReviewMapper.toResponseDto(saved);
        response.setBook(savedBook);

        return response;
    }

    @Override
    public ReviewResDto update(String token, String isbn13, UpdateReviewReqDto dto) {
        String userId = FirebaseAuthUtil.getUserId(token);

        BookReviewEntity existing = bookReviewRepository
                .findByUserIdAndIsbn13(userId, isbn13)
                .block();

        if (existing == null) {
            throw new NotFoundException("Book review not found with ISBN: " + isbn13);
        }

        bookReviewMapper.updateEntityFromDto(dto, existing);

        BookReviewEntity updated = bookReviewRepository.save(existing).block();

        BookResDto book = bookService.findByIsbn13(isbn13);

        ReviewResDto response = bookReviewMapper.toResponseDto(updated);
        response.setBook(book);

        return response;
    }

    @Override
    public List<ReviewResDto> findAll(String token) {
        String userId = FirebaseAuthUtil.getUserId(token);

        List<BookReviewEntity> reviews = bookReviewRepository
                .findAllByUserId(userId)
                .collectList()
                .block();

        return reviews.stream().map(review -> {
            ReviewResDto dto = bookReviewMapper.toResponseDto(review);
            dto.setBook(bookService.findByIsbn13(review.getIsbn13()));
            return dto;
        }).toList();
    }

    @Override
    public ReviewResDto findByIsbn(String token, String isbn13) {
        String userId = FirebaseAuthUtil.getUserId(token);

        BookReviewEntity review = bookReviewRepository
                .findByUserIdAndIsbn13(userId, isbn13)
                .block();

        if (review == null) {
            throw new NotFoundException("Book review not found with ISBN: " + isbn13);
        }

        ReviewResDto dto = bookReviewMapper.toResponseDto(review);
        dto.setBook(bookService.findByIsbn13(isbn13));

        return dto;
    }

    @Override
    public void deleteByIsbn(String token, String isbn13) {
        String userId = FirebaseAuthUtil.getUserId(token);

        BookReviewEntity review = bookReviewRepository
                .findByUserIdAndIsbn13(userId, isbn13)
                .block();

        if (review == null) {
            throw new NotFoundException("Book review not found with ISBN: " + isbn13);
        }

        bookReviewRepository.delete(review).block();
    }

    private void validateIsbnUnique(String userId, String isbn13) {
        if (bookReviewRepository.existsByUserIdAndIsbn13(userId, isbn13)
                .hasElement().block()) {
            throw new AlreadyExistException("Book review already exists with ISBN: " + isbn13);
        }
    }
}