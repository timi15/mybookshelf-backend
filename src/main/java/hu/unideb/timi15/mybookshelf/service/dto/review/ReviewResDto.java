package hu.unideb.timi15.mybookshelf.service.dto.review;

import hu.unideb.timi15.mybookshelf.service.dto.book.BookResDto;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResDto {

    private String documentId;
    private String isbn13;
    private LocalDate startDate;
    private LocalDate finishDate;
    private List<String> genres;
    private String reflection;
    private Integer rate;
    private BookResDto book;
}
