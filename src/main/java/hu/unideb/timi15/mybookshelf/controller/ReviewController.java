package hu.unideb.timi15.mybookshelf.controller;

import hu.unideb.timi15.mybookshelf.service.ReviewService;
import hu.unideb.timi15.mybookshelf.service.dto.review.ReviewResDto;
import hu.unideb.timi15.mybookshelf.service.dto.review.CreateReviewReqDto;
import hu.unideb.timi15.mybookshelf.service.dto.review.UpdateReviewReqDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("v1/mybookshelf/book-reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewResDto>> findAll(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(reviewService.findAll(token));
    }

    @GetMapping("/{isbn13}")
    public ResponseEntity<ReviewResDto> findByIsbn(
            @RequestHeader("Authorization") String token,
            @PathVariable String isbn13
    ) {
        return ResponseEntity.ok(reviewService.findByIsbn(token, isbn13));
    }

    @PostMapping
    public ResponseEntity<ReviewResDto> save(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody CreateReviewReqDto dto
    ) {
        ReviewResDto resDto = reviewService.save(token, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @PutMapping("/{isbn13}")
    public ResponseEntity<ReviewResDto> updateBookReview(
            @RequestHeader("Authorization") String token,
            @PathVariable String isbn13,
            @Valid @RequestBody UpdateReviewReqDto dto) {

        ReviewResDto updatedReview = reviewService.update(token, isbn13, dto);

        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/{isbn13}")
    public ResponseEntity<?> deleteByIsbn(
            @RequestHeader("Authorization") String token,
            @PathVariable String isbn13
    ) {
        reviewService.deleteByIsbn(token, isbn13);
        return ResponseEntity.noContent().build();
    }

}
