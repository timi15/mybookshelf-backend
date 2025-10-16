package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.exception.AlreadyExistsException;
import hu.unideb.timi15.mybookshelf.exception.NotFoundException;
import hu.unideb.timi15.mybookshelf.mapper.BookReviewMapper;
import hu.unideb.timi15.mybookshelf.repository.BookReviewRepository;
import hu.unideb.timi15.mybookshelf.service.BookReviewService;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.request.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.response.BookReviewResponseDTO;
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
    public BookReviewResponseDTO save(CreateBookReviewRequestDTO createBookReviewRequestDTO) {
        validateIsbnUnique(createBookReviewRequestDTO.getIsbn13());
        BookReviewEntity bookReviewEntity = bookReviewMapper.toEntity(createBookReviewRequestDTO);
        return bookReviewMapper.toResponseDTO(bookReviewRepository.save(bookReviewEntity).block());
    }

    @Override
    public List<BookReviewResponseDTO> findAll() {
        List<BookReviewEntity> bookReviewEntities = bookReviewRepository.findAll().collectList().block();
        return bookReviewMapper.map(bookReviewEntities);
    }

    @Override
    public BookReviewResponseDTO findByISBN(String isbn13) {
        BookReviewEntity bookReviewEntity = bookReviewRepository.findByIsbn13(isbn13)
                .switchIfEmpty(Mono.error(
                        new NotFoundException("Book review not found with ISBN: " + isbn13)))
                .block();
        return bookReviewMapper.toResponseDTO(bookReviewEntity);
    }

    public void validateIsbnUnique(String isbn13) {
        if (bookReviewRepository.existsByIsbn13(isbn13).hasElement().block()) {
            throw new AlreadyExistsException("Book review already exists with ISBN: " + isbn13);
        }
    }
}
