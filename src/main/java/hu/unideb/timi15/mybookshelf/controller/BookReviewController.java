package hu.unideb.timi15.mybookshelf.controller;

import hu.unideb.timi15.mybookshelf.service.BookReviewService;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.request.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.response.BookReviewResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/mybookshelf/book-review")
@RequiredArgsConstructor
public class BookReviewController {

    final BookReviewService bookReviewService;

    @PostMapping("/save")
    public BookReviewResponseDTO save(@Valid @RequestBody CreateBookReviewRequestDTO createBookReviewRequestDTO) {
        return bookReviewService.save(createBookReviewRequestDTO);
    }
}
