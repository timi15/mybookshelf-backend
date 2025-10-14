package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.repository.BookReviewRepository;
import hu.unideb.timi15.mybookshelf.service.BookReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookReviewServiceImpl implements BookReviewService {

    final BookReviewRepository bookReviewRepository;

    @Override
    public BookReviewEntity save(BookReviewEntity bookReviewEntity) {
        return bookReviewRepository.save(bookReviewEntity).block();
    }
}
