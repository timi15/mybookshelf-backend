package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.data.repository.BookRepository;
import hu.unideb.timi15.mybookshelf.exception.NoSessionException;
import hu.unideb.timi15.mybookshelf.exception.NotFoundException;
import hu.unideb.timi15.mybookshelf.mapper.BookMapper;
import hu.unideb.timi15.mybookshelf.service.BookService;
import hu.unideb.timi15.mybookshelf.service.dto.book.BookResDto;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookResDto addOrGetBook(BookEntity book, String userId) {

        BookEntity existing = bookRepository
                .findByIsbn13(book.getIsbn13())
                .block();

        if (existing != null) {
            return bookMapper.toResponseDto(existing);
        }
        book.setUserId(userId);
        BookEntity saved = bookRepository.save(book).block();
        return bookMapper.toResponseDto(saved);
    }

    @Override
    public BookResDto findByIsbn13(String isbn13) {

        BookEntity bookEntity = bookRepository
                .findByIsbn13(isbn13)
                .block();

        if (bookEntity == null) {
            throw new NotFoundException("Book not found with ISBN: " + isbn13);
        }

        return bookMapper.toResponseDto(bookEntity);
    }

    @Override
    public List<BookResDto> findAll(String idToken) {
        if (null == idToken) {
            throw new NoSessionException();
        }
        List<BookEntity> bookEntities = bookRepository.findAll().collectList().block();
        return bookMapper.map(bookEntities);
    }
}
