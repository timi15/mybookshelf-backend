package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.exception.AlreadyExistsException;
import hu.unideb.timi15.mybookshelf.mapper.BookReviewMapper;
import hu.unideb.timi15.mybookshelf.repository.BookReviewRepository;
import hu.unideb.timi15.mybookshelf.service.BookReviewService;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.request.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.request.UpdateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.response.BookReviewResponseDTO;
import hu.unideb.timi15.mybookshelf.utils.FirebaseAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookReviewServiceImpl implements BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookReviewMapper bookReviewMapper;

    @Override
    public BookReviewResponseDTO save(String idToken, CreateBookReviewRequestDTO createBookReviewRequestDTO) {
        String userId = FirebaseAuthUtil.getUserId(idToken);

        validateIsbnUnique(userId, createBookReviewRequestDTO.getIsbn13());

        BookReviewEntity bookReviewEntity = bookReviewMapper.toEntity(createBookReviewRequestDTO);
        bookReviewEntity.setUserId(userId);

        return bookReviewMapper.toResponseDTO(bookReviewRepository.save(bookReviewEntity).block());
    }

    @Override
    public BookReviewResponseDTO save(String idToken, String isbn13, UpdateBookReviewRequestDTO updateBookReviewRequestDTO) {
        String userId = FirebaseAuthUtil.getUserId(idToken);

        BookReviewEntity existing = bookReviewRepository
                .findByUserIdAndIsbn13(userId, isbn13).block();

        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Book review not found with ISBN: " + isbn13);
        }

        bookReviewMapper.updateBookReviewFromDto(updateBookReviewRequestDTO, existing);

        BookReviewEntity updated = bookReviewRepository.save(existing).block();

        return bookReviewMapper.toResponseDTO(updated);
    }

    @Override
    public List<BookReviewResponseDTO> findAll(String idToken) {

        String userId = FirebaseAuthUtil.getUserId(idToken);

        List<BookReviewEntity> bookReviewEntities = bookReviewRepository.findAllByUserId(userId).collectList().block();
        return bookReviewMapper.map(bookReviewEntities);
    }

    @Override
    public BookReviewResponseDTO findByISBN(String idToken, String isbn13) {

        String userId = FirebaseAuthUtil.getUserId(idToken);

        BookReviewEntity bookReviewEntity = bookReviewRepository
                .findByUserIdAndIsbn13(userId, isbn13)
                .block();

        if (bookReviewEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Book review not found with ISBN: " + isbn13);
        }

        return bookReviewMapper.toResponseDTO(bookReviewEntity);
    }

    @Override
    public void deleteByISBN(String idToken, String isbn13) {

        String userId = FirebaseAuthUtil.getUserId(idToken);

        BookReviewEntity review = bookReviewRepository.findByUserIdAndIsbn13(userId, isbn13).block();

        if (review == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Book review not found with ISBN: " + isbn13);
        }

        bookReviewRepository.deleteByUserIdAndIsbn13(userId, isbn13).block();
    }

    public void validateIsbnUnique(String userId, String isbn13) {
        if (bookReviewRepository.existsByUserIdAndIsbn13(userId, isbn13).hasElement().block()) {
            throw new AlreadyExistsException("Book review already exists with ISBN: " + isbn13);
        }
    }
}
