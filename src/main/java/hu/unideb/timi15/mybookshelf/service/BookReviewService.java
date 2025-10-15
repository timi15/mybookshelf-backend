package hu.unideb.timi15.mybookshelf.service;

import hu.unideb.timi15.mybookshelf.service.dto.bookreview.request.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.response.BookReviewResponseDTO;

public interface BookReviewService {

    BookReviewResponseDTO save(CreateBookReviewRequestDTO createBookReviewRequestDTO);

}
