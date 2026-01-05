package hu.unideb.timi15.mybookshelf.service;

import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.service.dto.book.BookResDto;

import java.util.List;

public interface BookService {

    BookResDto addOrGetBook(BookEntity book, String userId);

    List<BookResDto> findAll(String token);

    BookResDto findByIsbn13(String isbn13);

}
