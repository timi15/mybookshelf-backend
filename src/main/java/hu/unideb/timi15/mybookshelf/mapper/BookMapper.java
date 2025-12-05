package hu.unideb.timi15.mybookshelf.mapper;

import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.mapper.common.BaseMapper;
import hu.unideb.timi15.mybookshelf.service.dto.book.request.CreateBookRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.book.response.BookResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        config = BaseMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookMapper extends BaseMapper<CreateBookRequestDTO, Void, BookResponseDTO, BookEntity> {
}
