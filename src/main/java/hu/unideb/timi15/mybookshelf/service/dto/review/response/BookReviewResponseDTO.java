package hu.unideb.timi15.mybookshelf.service.dto.review.response;

import hu.unideb.timi15.mybookshelf.service.dto.book.response.BookResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookReviewResponseDTO {

    private String documentId;
    private String isbn13;
    private LocalDate startDate;
    private LocalDate finishDate;
    private String reflection;
    private Integer rate;
    private BookResponseDTO book;
}
