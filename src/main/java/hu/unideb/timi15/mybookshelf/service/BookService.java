package hu.unideb.timi15.mybookshelf.service;

import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.service.dto.book.response.BookResponseDTO;

import java.util.List;

public interface BookService {

    BookResponseDTO addOrGetBook(BookEntity book, String userId);

    BookResponseDTO findByIsbn13(String isbn13);

    List<BookResponseDTO> findAll(String token);
}
