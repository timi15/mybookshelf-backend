package hu.unideb.timi15.mybookshelf.mapper;

import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.common.mapper.BaseMapper;
import hu.unideb.timi15.mybookshelf.service.dto.book.CreateBookRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.book.BookResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        config = BaseMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookMapper extends BaseMapper<CreateBookRequestDTO, Void, BookResponseDTO, BookEntity> {
}
