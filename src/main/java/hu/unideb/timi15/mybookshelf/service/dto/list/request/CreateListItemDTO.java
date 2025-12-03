package hu.unideb.timi15.mybookshelf.service.dto.list.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateListItemDTO {

    private String isbn13;

}
