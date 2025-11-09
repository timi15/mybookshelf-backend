package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.exception.AlreadyExistsException;
import hu.unideb.timi15.mybookshelf.exception.NotFoundException;
import hu.unideb.timi15.mybookshelf.mapper.BookReviewMapper;
import hu.unideb.timi15.mybookshelf.repository.BookReviewRepository;
import hu.unideb.timi15.mybookshelf.service.BookReviewService;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.request.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.response.BookReviewResponseDTO;
import hu.unideb.timi15.mybookshelf.utils.FirebaseAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

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
    public List<BookReviewResponseDTO> findAll(String idToken) {

        String userId = FirebaseAuthUtil.getUserId(idToken);

        List<BookReviewEntity> bookReviewEntities = bookReviewRepository.findAllByUserId(userId).collectList().block();
        return bookReviewMapper.map(bookReviewEntities);
    }

    @Override
    public BookReviewResponseDTO findByISBN(String idToken, String isbn13) {

        String userId = FirebaseAuthUtil.getUserId(idToken);

        BookReviewEntity bookReviewEntity = bookReviewRepository.findByUserIdAndIsbn13(userId, isbn13)
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Book review not found with ISBN: " + isbn13)))
                .block();
        return bookReviewMapper.toResponseDTO(bookReviewEntity);
    }

    public void validateIsbnUnique(String userId, String isbn13) {
        if (bookReviewRepository.existsByUserIdAndIsbn13(userId, isbn13).hasElement().block()) {
            throw new AlreadyExistsException("Book review already exists with ISBN: " + isbn13);
        }
    }
}
