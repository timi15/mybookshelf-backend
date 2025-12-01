package hu.unideb.timi15.mybookshelf.mapper;

import hu.unideb.timi15.mybookshelf.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.service.dto.review.request.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.review.request.UpdateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.review.response.BookReviewResponseDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DateConverter.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookReviewMapper {

    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "timestampToLocalDate")
    @Mapping(target = "finishDate", source = "finishDate", qualifiedByName = "timestampToLocalDate")
    BookReviewResponseDTO toResponseDTO(BookReviewEntity bookReviewEntity);

    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "localDateToTimestamp")
    @Mapping(target = "finishDate", source = "finishDate", qualifiedByName = "localDateToTimestamp")
    BookReviewEntity toEntity(CreateBookReviewRequestDTO createBookReviewRequestDTO);

    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "localDateToTimestamp")
    @Mapping(target = "finishDate", source = "finishDate", qualifiedByName = "localDateToTimestamp")
    void updateBookReviewFromDto(UpdateBookReviewRequestDTO dto, @MappingTarget BookReviewEntity entity);

    List<BookReviewResponseDTO> map(List<BookReviewEntity> bookReviewEntities);

}
