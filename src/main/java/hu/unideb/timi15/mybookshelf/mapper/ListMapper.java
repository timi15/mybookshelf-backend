package hu.unideb.timi15.mybookshelf.mapper;

import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.data.entity.LovedListEntity;
import hu.unideb.timi15.mybookshelf.data.entity.ToReadListEntity;
import hu.unideb.timi15.mybookshelf.common.entity.BaseListEntity;
import hu.unideb.timi15.mybookshelf.service.dto.book.CreateBookReqDto;
import hu.unideb.timi15.mybookshelf.service.dto.list.ListItemResDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {BookMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ListMapper {

    LovedListEntity toLovedListEntity(CreateBookReqDto dto);

    ToReadListEntity toToReadListEntity(CreateBookReqDto dto);

    @Mapping(target = "book", source = "book")
    ListItemResDto toDto(BaseListEntity entity, BookEntity book);
}