package hu.unideb.timi15.mybookshelf.service;

import hu.unideb.timi15.mybookshelf.service.dto.review.request.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.review.request.UpdateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.review.response.BookReviewResponseDTO;

import java.util.List;

public interface BookReviewService {

    BookReviewResponseDTO save(String token, CreateBookReviewRequestDTO createBookReviewRequestDTO);

    BookReviewResponseDTO update(String token, String isbn13, UpdateBookReviewRequestDTO updateBookReviewRequestDTO);

    List<BookReviewResponseDTO> findAll(String token);

    BookReviewResponseDTO findByISBN(String token, String isbn13);

    void deleteByISBN(String token, String isbn13);
}
