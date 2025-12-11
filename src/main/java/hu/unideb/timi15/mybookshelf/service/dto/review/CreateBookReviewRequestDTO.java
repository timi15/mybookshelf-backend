package hu.unideb.timi15.mybookshelf.service.dto.review;

import hu.unideb.timi15.mybookshelf.service.dto.book.CreateBookRequestDTO;
import jakarta.validation.constraints.*;
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
public class CreateBookReviewRequestDTO {

    @NotBlank
    private String isbn13;

    private CreateBookRequestDTO book;

    @PastOrPresent
    private LocalDate startDate;

    @PastOrPresent
    private LocalDate finishDate;

    @NotEmpty
    private List<String> genres;

    @Size(max = 500)
    private String reflection;

    @Min(1)
    @Max(5)
    private Integer rate;
}
