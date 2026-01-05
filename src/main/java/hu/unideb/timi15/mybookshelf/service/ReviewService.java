package hu.unideb.timi15.mybookshelf.service;

import hu.unideb.timi15.mybookshelf.service.dto.review.ReviewResDto;
import hu.unideb.timi15.mybookshelf.service.dto.review.CreateReviewReqDto;
import hu.unideb.timi15.mybookshelf.service.dto.review.UpdateReviewReqDto;

import java.util.List;

public interface ReviewService {

    List<ReviewResDto> findAll(String token);

    ReviewResDto findByIsbn(String token, String isbn13);

    ReviewResDto save(String token, CreateReviewReqDto dto);

    ReviewResDto update(String token, String isbn13, UpdateReviewReqDto dto);

    void deleteByIsbn(String token, String isbn13);
}
