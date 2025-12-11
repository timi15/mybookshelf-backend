package hu.unideb.timi15.mybookshelf.service.dto.book;

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
    private String coverUrl;

    @NotBlank
    private String plot;
}
