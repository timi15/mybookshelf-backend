package hu.unideb.timi15.mybookshelf.service.dto.review.response;

import hu.unideb.timi15.mybookshelf.service.dto.book.response.BookResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookReviewResponseDTO {

    private String documentId;
    private String isbn13;
    private LocalDate startDate;
    private LocalDate finishDate;
    private List<String> genres;
    private String reflection;
    private Integer rate;
    private BookResponseDTO book;
}
