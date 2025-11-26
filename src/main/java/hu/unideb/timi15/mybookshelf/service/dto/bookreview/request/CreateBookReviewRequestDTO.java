package hu.unideb.timi15.mybookshelf.service.dto.bookreview.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateBookReviewRequestDTO {

    @NotBlank
    private String isbn13;

    @NotBlank
    private String image;

    @NotBlank
    private String author;

    @NotBlank
    private String title;

    @PastOrPresent
    private LocalDate startDate;

    @PastOrPresent
    private LocalDate finishDate;

    @Size(max = 500)
    private String plot;

    @Size(max = 500)
    private String reflection;

    @Min(1)
    @Max(5)
    private Integer rate;
}
