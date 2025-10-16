package hu.unideb.timi15.mybookshelf.service;

import hu.unideb.timi15.mybookshelf.service.dto.bookreview.request.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.response.BookReviewResponseDTO;

import java.util.List;

public interface BookReviewService {

    BookReviewResponseDTO save(CreateBookReviewRequestDTO createBookReviewRequestDTO);

    List<BookReviewResponseDTO> findAll();

    BookReviewResponseDTO findByISBN(String isbn13);
}
