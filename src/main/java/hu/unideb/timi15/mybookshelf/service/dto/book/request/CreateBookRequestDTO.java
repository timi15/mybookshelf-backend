package hu.unideb.timi15.mybookshelf.service.dto.book.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateBookRequestDTO {

    @NotBlank
    private String isbn13;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String image;

    @NotBlank
    private String plot;
}
