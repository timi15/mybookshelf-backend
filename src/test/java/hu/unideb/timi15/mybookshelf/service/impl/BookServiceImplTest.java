package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.data.repository.BookRepository;
import hu.unideb.timi15.mybookshelf.exception.NotFoundException;
import hu.unideb.timi15.mybookshelf.mapper.BookMapper;
import hu.unideb.timi15.mybookshelf.service.dto.book.BookResDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void getOrCreateBook_existingBook_returnsExisting() {
        BookEntity book = new BookEntity();
        book.setIsbn13("1111111111111");

        BookResDto dto = new BookResDto();

        when(bookRepository.findByIsbn13("1111111111111"))
                .thenReturn(Mono.just(book));
        when(bookMapper.toResponseDto(book))
                .thenReturn(dto);

        BookResDto result = bookService.getOrCreateBook(book, "user1");

        assertNotNull(result);
        verify(bookRepository, never()).save(any());
    }

    @Test
    void getOrCreateBook_newBook_savesAndReturns() {
        BookEntity book = new BookEntity();
        book.setIsbn13("1111111111111");

        BookEntity savedBook = new BookEntity();
        savedBook.setIsbn13("1111111111111");

        BookResDto dto = new BookResDto();

        when(bookRepository.findByIsbn13("1111111111111"))
                .thenReturn(Mono.empty());
        when(bookRepository.save(book))
                .thenReturn(Mono.just(savedBook));
        when(bookMapper.toResponseDto(savedBook))
                .thenReturn(dto);

        BookResDto result = bookService.getOrCreateBook(book, "user1");

        assertNotNull(result);
        verify(bookRepository).save(book);
    }

    @Test
    void findByIsbn13_existingBook_returnsBook() {
        BookEntity book = new BookEntity();
        book.setIsbn13("1111111111111");

        BookResDto dto = new BookResDto();

        when(bookRepository.findByIsbn13("1111111111111"))
                .thenReturn(Mono.just(book));
        when(bookMapper.toResponseDto(book))
                .thenReturn(dto);

        BookResDto result = bookService.findByIsbn13("1111111111111");

        assertNotNull(result);
    }

    @Test
    void findByIsbn13_notFound_throwsException() {
        when(bookRepository.findByIsbn13("1111111111111"))
                .thenReturn(Mono.empty());

        assertThrows(NotFoundException.class,
                () -> bookService.findByIsbn13("1111111111111"));
    }
}
