package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.exception.AlreadyExistException;
import hu.unideb.timi15.mybookshelf.exception.NotFoundException;
import hu.unideb.timi15.mybookshelf.mapper.BookMapper;
import hu.unideb.timi15.mybookshelf.mapper.BookReviewMapper;
import hu.unideb.timi15.mybookshelf.repository.BookReviewRepository;
import hu.unideb.timi15.mybookshelf.service.BookReviewService;
import hu.unideb.timi15.mybookshelf.service.BookService;
import hu.unideb.timi15.mybookshelf.service.dto.book.response.BookResponseDTO;
import hu.unideb.timi15.mybookshelf.service.dto.review.request.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.review.request.UpdateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.review.response.BookReviewResponseDTO;
import hu.unideb.timi15.mybookshelf.utils.FirebaseAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookReviewServiceImpl implements BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookService bookService;
    private final BookReviewMapper bookReviewMapper;
    private final BookMapper bookMapper;

    @Override
    public BookReviewResponseDTO save(String idToken, CreateBookReviewRequestDTO dto) {
        String userId = FirebaseAuthUtil.getUserId(idToken);
        validateIsbnUnique(userId, dto.getIsbn13());

        BookResponseDTO savedBook = bookService.addOrGetBook(
                bookMapper.toEntity(dto.getBook()),
                userId
        );

        BookReviewEntity reviewEntity = bookReviewMapper.toEntity(dto);
        reviewEntity.setUserId(userId);
        reviewEntity.setIsbn13(savedBook.getIsbn13());

        BookReviewEntity saved = bookReviewRepository.save(reviewEntity).block();

        BookReviewResponseDTO response = bookReviewMapper.toResponseDTO(saved);
        response.setBook(savedBook);

        return response;
    }

    @Override
    public BookReviewResponseDTO update(String idToken, String isbn13, UpdateBookReviewRequestDTO dto) {
        String userId = FirebaseAuthUtil.getUserId(idToken);

        BookReviewEntity existing = bookReviewRepository
                .findByUserIdAndIsbn13(userId, isbn13)
                .block();

        if (existing == null) {
            throw new NotFoundException("Book review not found with ISBN: " + isbn13);
        }

        bookReviewMapper.updateBookReviewFromDto(dto, existing);

        BookReviewEntity updated = bookReviewRepository.save(existing).block();

        BookResponseDTO book = bookService.findByIsbn13(isbn13);

        BookReviewResponseDTO response = bookReviewMapper.toResponseDTO(updated);
        response.setBook(book);

        return response;
    }

    @Override
    public List<BookReviewResponseDTO> findAll(String idToken) {
        String userId = FirebaseAuthUtil.getUserId(idToken);

        List<BookReviewEntity> reviews = bookReviewRepository
                .findAllByUserId(userId)
                .collectList()
                .block();

        return reviews.stream().map(review -> {
            BookReviewResponseDTO dto = bookReviewMapper.toResponseDTO(review);
            dto.setBook(bookService.findByIsbn13(review.getIsbn13()));
            return dto;
        }).toList();
    }

    @Override
    public BookReviewResponseDTO findByISBN(String idToken, String isbn13) {
        String userId = FirebaseAuthUtil.getUserId(idToken);

        BookReviewEntity review = bookReviewRepository
                .findByUserIdAndIsbn13(userId, isbn13)
                .block();

        if (review == null) {
            throw new NotFoundException("Book review not found with ISBN: " + isbn13);
        }

        BookReviewResponseDTO dto = bookReviewMapper.toResponseDTO(review);
        dto.setBook(bookService.findByIsbn13(isbn13));

        return dto;
    }

    @Override
    public void deleteByISBN(String idToken, String isbn13) {
        String userId = FirebaseAuthUtil.getUserId(idToken);

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