package hu.unideb.timi15.mybookshelf.service.dto.bookreview.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateBookReviewRequestDTO {

    @NotNull
    private String isbn13;

    @PastOrPresent
    private LocalDate startDate;

    @PastOrPresent
    private LocalDate finishDate;

    @NotBlank
    @Size(max = 500)
    private String plot;

    @Size(max = 500)
    private String reflection;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    //TODO: user
}
