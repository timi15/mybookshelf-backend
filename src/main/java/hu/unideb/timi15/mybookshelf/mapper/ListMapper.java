package hu.unideb.timi15.mybookshelf.mapper;

import hu.unideb.timi15.mybookshelf.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.entity.LovedListEntity;
import hu.unideb.timi15.mybookshelf.entity.ToReadListEntity;
import hu.unideb.timi15.mybookshelf.service.dto.book.request.CreateBookRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.list.response.ListItemResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {BookMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ListMapper {

    LovedListEntity toLovedListEntity(CreateBookRequestDTO dto);

    ToReadListEntity toToReadListEntity(CreateBookRequestDTO dto);

    @Mapping(target = "book", source = "book")
    ListItemResponseDTO toDto(LovedListEntity entity, BookEntity book);

    @Mapping(target = "book", source = "book")
    ListItemResponseDTO toDto(ToReadListEntity entity, BookEntity book);
}