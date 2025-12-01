package hu.unideb.timi15.mybookshelf.service.dto.book.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookResponseDTO {

    private String isbn13;
    private String userId;
    private String title;
    private String author;
    private String image;
    private String plot;
}
