package hu.unideb.timi15.mybookshelf.service;

import hu.unideb.timi15.mybookshelf.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.service.dto.book.response.BookResponseDTO;

public interface BookService {

    BookResponseDTO addOrGetBook(BookEntity book, String userId);

    BookResponseDTO findByIsbn13(String isbn13);
}
