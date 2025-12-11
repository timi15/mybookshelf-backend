package hu.unideb.timi15.mybookshelf.service.dto.list;

import hu.unideb.timi15.mybookshelf.service.dto.book.BookResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListItemResponseDTO {

    private BookResponseDTO book;

}
