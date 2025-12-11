package hu.unideb.timi15.mybookshelf.service;

import hu.unideb.timi15.mybookshelf.service.dto.review.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.review.UpdateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.review.BookReviewResponseDTO;

import java.util.List;

public interface BookReviewService {

    List<BookReviewResponseDTO> findAll(String token);

    BookReviewResponseDTO findByISBN(String token, String isbn13);

    BookReviewResponseDTO save(String token, CreateBookReviewRequestDTO createBookReviewRequestDTO);

    BookReviewResponseDTO update(String token, String isbn13, UpdateBookReviewRequestDTO updateBookReviewRequestDTO);

    void deleteByISBN(String token, String isbn13);
}
