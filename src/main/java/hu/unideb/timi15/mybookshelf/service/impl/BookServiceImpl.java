package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.mapper.BookMapper;
import hu.unideb.timi15.mybookshelf.repository.BookRepository;
import hu.unideb.timi15.mybookshelf.service.BookService;
import hu.unideb.timi15.mybookshelf.service.dto.book.response.BookResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResponseDTO addOrGetBook(BookEntity book, String userId) {

        BookEntity existing = bookRepository
                .findByIsbn13(book.getIsbn13())
                .block();

        if (existing != null) {
            return bookMapper.toResponseDTO(existing);
        }
        book.setUserId(userId);
        BookEntity saved = bookRepository.save(book).block();
        return bookMapper.toResponseDTO(saved);
    }

    @Override
    public BookResponseDTO findByIsbn13(String isbn13) {

        BookEntity bookEntity = bookRepository
                .findByIsbn13(isbn13)
                .block();

        if (bookEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Book not found with ISBN: " + isbn13);
        }

        return bookMapper.toResponseDTO(bookEntity);
    }
}
