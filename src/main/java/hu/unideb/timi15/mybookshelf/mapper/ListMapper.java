package hu.unideb.timi15.mybookshelf.mapper;

import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.data.entity.LovedListEntity;
import hu.unideb.timi15.mybookshelf.data.entity.ToReadListEntity;
import hu.unideb.timi15.mybookshelf.common.entity.BaseListEntity;
import hu.unideb.timi15.mybookshelf.service.dto.book.CreateBookRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.list.ListItemResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {BookMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ListMapper {

    LovedListEntity toLovedListEntity(CreateBookRequestDTO dto);

    ToReadListEntity toToReadListEntity(CreateBookRequestDTO dto);

    @Mapping(target = "book", source = "book")
    ListItemResponseDTO toDto(BaseListEntity entity, BookEntity book);
}