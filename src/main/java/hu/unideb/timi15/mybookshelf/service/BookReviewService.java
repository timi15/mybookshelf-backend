package hu.unideb.timi15.mybookshelf.service;

import hu.unideb.timi15.mybookshelf.service.dto.bookreview.request.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.request.UpdateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.response.BookReviewResponseDTO;

import java.util.List;

public interface BookReviewService {

    BookReviewResponseDTO save(String idToken, CreateBookReviewRequestDTO createBookReviewRequestDTO);

    BookReviewResponseDTO save(String idToken, String isbn13, UpdateBookReviewRequestDTO updateBookReviewRequestDTO);

    List<BookReviewResponseDTO> findAll(String idToken);

    BookReviewResponseDTO findByISBN(String idToken, String isbn13);

    void deleteByISBN(String idToken, String isbn13);
}
