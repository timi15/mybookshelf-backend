package hu.unideb.timi15.mybookshelf.service.dto.book;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResDto {

    private String isbn13;
    private String userId;
    private String title;
    private String author;
    private String coverUrl;
    private String plot;
}
