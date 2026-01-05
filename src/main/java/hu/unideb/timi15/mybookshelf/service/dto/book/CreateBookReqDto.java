package hu.unideb.timi15.mybookshelf.service.dto.book;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookReqDto {

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
