package hu.unideb.timi15.mybookshelf.controller;

import hu.unideb.timi15.mybookshelf.service.BookReviewService;
import hu.unideb.timi15.mybookshelf.service.dto.review.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.review.UpdateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.review.BookReviewResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/mybookshelf/book-review")
@RequiredArgsConstructor
public class BookReviewController {

    private final BookReviewService bookReviewService;

    @GetMapping("/all")
    public ResponseEntity<List<BookReviewResponseDTO>> findAll(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(bookReviewService.findAll(token));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookReviewResponseDTO> findByISBN(@RequestHeader("Authorization") String token, @PathVariable String isbn) {
        return ResponseEntity.ok(bookReviewService.findByISBN(token, isbn));
    }

    @PostMapping("/save")
    public ResponseEntity<BookReviewResponseDTO> save(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateBookReviewRequestDTO createBookReviewRequestDTO) {
        BookReviewResponseDTO responseDTO = bookReviewService.save(token, createBookReviewRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookReviewResponseDTO> updateBookReview(
            @RequestHeader("Authorization") String token,
            @PathVariable String isbn,
            @Valid @RequestBody UpdateBookReviewRequestDTO updateBookReviewRequestDTO) {

        BookReviewResponseDTO updatedReview = bookReviewService.update(token, isbn, updateBookReviewRequestDTO);

        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteByISBN(@RequestHeader("Authorization") String token, @PathVariable String isbn) {
        bookReviewService.deleteByISBN(token, isbn);
        return ResponseEntity.noContent().build();
    }

}
