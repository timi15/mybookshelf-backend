package hu.unideb.timi15.mybookshelf.controller;

import hu.unideb.timi15.mybookshelf.service.BookReviewService;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.request.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.request.UpdateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.response.BookReviewResponseDTO;
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
    public ResponseEntity<List<BookReviewResponseDTO>> findAll(@RequestHeader("Authorization") String idToken) {
        return ResponseEntity.ok(bookReviewService.findAll(idToken));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookReviewResponseDTO> findByISBN(@RequestHeader("Authorization") String idToken, @PathVariable String isbn) {
        return ResponseEntity.ok(bookReviewService.findByISBN(idToken, isbn));
    }

    @PostMapping("/save")
    public ResponseEntity<BookReviewResponseDTO> save(@RequestHeader("Authorization") String idToken, @Valid @RequestBody CreateBookReviewRequestDTO createBookReviewRequestDTO) {
        BookReviewResponseDTO responseDTO = bookReviewService.save(idToken, createBookReviewRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<BookReviewResponseDTO> updateBookReview(
            @RequestHeader("Authorization") String idToken,
            @PathVariable String isbn,
            @Valid @RequestBody UpdateBookReviewRequestDTO updateBookReviewRequestDTO) {

        BookReviewResponseDTO updatedReview = bookReviewService.save(idToken, isbn, updateBookReviewRequestDTO);

        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<?> deleteByISBN(@RequestHeader("Authorization") String idToken, @PathVariable String isbn) {
        bookReviewService.deleteByISBN(idToken, isbn);
        return ResponseEntity.noContent().build();
    }

}
