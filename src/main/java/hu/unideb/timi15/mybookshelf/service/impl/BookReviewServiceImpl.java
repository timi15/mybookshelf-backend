package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.mapper.BookReviewMapper;
import hu.unideb.timi15.mybookshelf.repository.BookReviewRepository;
import hu.unideb.timi15.mybookshelf.service.BookReviewService;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.request.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.response.BookReviewResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookReviewServiceImpl implements BookReviewService {

    final BookReviewRepository bookReviewRepository;
    final BookReviewMapper bookReviewMapper;

    @Override
    public BookReviewResponseDTO save(CreateBookReviewRequestDTO createBookReviewRequestDTO) {
        BookReviewEntity bookReviewEntity = bookReviewMapper.toEntity(createBookReviewRequestDTO);
        return bookReviewMapper.toResponseDTO(bookReviewRepository.save(bookReviewEntity).block());
    }
}
