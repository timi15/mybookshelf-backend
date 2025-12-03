package hu.unideb.timi15.mybookshelf.mapper;

import hu.unideb.timi15.mybookshelf.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.service.dto.book.request.CreateBookRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.book.response.BookResponseDTO;
import hu.unideb.timi15.mybookshelf.service.dto.list.request.CreateListItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    BookEntity toEntity(CreateBookRequestDTO dto);

    BookResponseDTO toResponseDTO(BookEntity bookEntity);

    List<BookResponseDTO> map(List<BookEntity> bookEntities);

}
