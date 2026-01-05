package hu.unideb.timi15.mybookshelf.service.dto.list;

import hu.unideb.timi15.mybookshelf.service.dto.book.BookResDto;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListItemResDto {

    private BookResDto book;

}
