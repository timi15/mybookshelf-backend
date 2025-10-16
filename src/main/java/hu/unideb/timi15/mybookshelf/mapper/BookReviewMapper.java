package hu.unideb.timi15.mybookshelf.mapper;

import hu.unideb.timi15.mybookshelf.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.request.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.bookreview.response.BookReviewResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DateConverter.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookReviewMapper {

    @Mapping(target = "startDate", source = "bookReviewEntity.startDate", qualifiedByName = "timestampToLocalDate")
    @Mapping(target = "finishDate", source = "bookReviewEntity.finishDate", qualifiedByName = "timestampToLocalDate")
    BookReviewResponseDTO toResponseDTO(BookReviewEntity bookReviewEntity);


    @Mapping(target = "documentId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "startDate", source = "createBookReviewRequestDTO.startDate", qualifiedByName = "localDateToTimestamp")
    @Mapping(target = "finishDate", source = "createBookReviewRequestDTO.finishDate", qualifiedByName = "localDateToTimestamp")
    BookReviewEntity toEntity(CreateBookReviewRequestDTO createBookReviewRequestDTO);


    List<BookReviewResponseDTO> map(List<BookReviewEntity> bookReviewEntities);

}
