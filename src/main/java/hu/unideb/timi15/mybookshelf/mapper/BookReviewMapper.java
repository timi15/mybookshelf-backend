package hu.unideb.timi15.mybookshelf.mapper;

import hu.unideb.timi15.mybookshelf.data.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.mapper.common.BaseMapper;
import hu.unideb.timi15.mybookshelf.service.dto.review.request.CreateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.review.request.UpdateBookReviewRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.review.response.BookReviewResponseDTO;
import org.mapstruct.*;


@Mapper(
        componentModel = "spring",
        uses = {DateConverter.class, BookMapper.class},
        config = BaseMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookReviewMapper extends BaseMapper<CreateBookReviewRequestDTO, UpdateBookReviewRequestDTO ,BookReviewResponseDTO,BookReviewEntity> {

    @Override
    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "timestampToLocalDate")
    @Mapping(target = "finishDate", source = "finishDate", qualifiedByName = "timestampToLocalDate")
    BookReviewResponseDTO toResponseDTO(BookReviewEntity entity);

    @Override
    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "localDateToTimestamp")
    @Mapping(target = "finishDate", source = "finishDate", qualifiedByName = "localDateToTimestamp")
    BookReviewEntity toEntity(CreateBookReviewRequestDTO dto);

    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "localDateToTimestamp")
    @Mapping(target = "finishDate", source = "finishDate", qualifiedByName = "localDateToTimestamp")
    void updateEntityFromDto(UpdateBookReviewRequestDTO dto, @MappingTarget BookReviewEntity entity);

}
