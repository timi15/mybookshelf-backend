package hu.unideb.timi15.mybookshelf.mapper;

import hu.unideb.timi15.mybookshelf.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.service.dto.book.request.CreateBookRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.book.response.BookResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {

    BookEntity toEntity(CreateBookRequestDTO dto);

    BookResponseDTO toResponseDTO(BookEntity entity);

}
