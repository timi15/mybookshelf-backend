package hu.unideb.timi15.mybookshelf.service.dto.bookreview.response;

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

    private String userId;

    private String image;

    private String author;

    private String title;

    private LocalDate startDate;

    private LocalDate finishDate;

    private String plot;

    private String reflection;

    private Integer rate;
}
