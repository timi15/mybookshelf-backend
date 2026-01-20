package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.data.repository.BookRepository;
import hu.unideb.timi15.mybookshelf.exception.UnauthorizedException;
import hu.unideb.timi15.mybookshelf.exception.NotFoundException;
import hu.unideb.timi15.mybookshelf.mapper.BookMapper;
import hu.unideb.timi15.mybookshelf.service.BookService;
import hu.unideb.timi15.mybookshelf.service.dto.book.BookResDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResDto getOrCreateBook(BookEntity book, String userId) {

        log.info("addOrGetBook called with isbn13={}", book.getIsbn13());

        BookEntity existing = bookRepository
                .findByIsbn13(book.getIsbn13())
                .block();

        if (existing != null) {
            log.info("Book already exists, returning existing record.");
            return bookMapper.toResponseDto(existing);
        }
        book.setUserId(userId);
        BookEntity saved = bookRepository.save(book).block();

        log.info("Book successfully saved. isbn13={}", saved.getIsbn13());

        return bookMapper.toResponseDto(saved);
    }

    @Override
    public BookResDto findByIsbn13(String isbn13) {

        log.info("findByIsbn13 called with isbn13={}", isbn13);

        BookEntity bookEntity = bookRepository
                .findByIsbn13(isbn13)
                .block();

        if (bookEntity == null) {
            log.warn("Book not found. isbn13={}", isbn13);
            throw new NotFoundException("Book not found with ISBN: " + isbn13);
        }

        return bookMapper.toResponseDto(bookEntity);
    }

    @Override
    public List<BookResDto> findAll(String idToken) {
        if (null == idToken) {
            log.error("findAll called without session.");
            throw new UnauthorizedException("Authentication token is missing");
        }
        List<BookEntity> bookEntities = bookRepository.findAll().collectList().block();
        log.info("Fetching all books.");
        return bookMapper.map(bookEntities);
    }
}
