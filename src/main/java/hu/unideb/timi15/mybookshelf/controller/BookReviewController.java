package hu.unideb.timi15.mybookshelf.controller;

import hu.unideb.timi15.mybookshelf.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.service.BookReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/mybookshelf/book-review")
@RequiredArgsConstructor
public class BookReviewController {

    final BookReviewService bookReviewService;

    @PostMapping("save")
    public BookReviewEntity save(@RequestBody BookReviewEntity bookReviewEntity){
        return bookReviewService.save(bookReviewEntity);
    }
}
