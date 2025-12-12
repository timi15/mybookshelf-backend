package hu.unideb.timi15.mybookshelf.mapper;

import hu.unideb.timi15.mybookshelf.data.entity.BookReviewEntity;
import hu.unideb.timi15.mybookshelf.common.mapper.BaseMapper;
import hu.unideb.timi15.mybookshelf.service.dto.review.CreateReviewReqDto;
import hu.unideb.timi15.mybookshelf.service.dto.review.ReviewResDto;
import hu.unideb.timi15.mybookshelf.service.dto.review.UpdateReviewReqDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        uses = {DateConverter.class, BookMapper.class},
        config = BaseMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookReviewMapper extends BaseMapper<
        CreateReviewReqDto,
        UpdateReviewReqDto,
        ReviewResDto,
        BookReviewEntity> {

    @Override
    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "timestampToLocalDate")
    @Mapping(target = "finishDate", source = "finishDate", qualifiedByName = "timestampToLocalDate")
    ReviewResDto toResponseDto(BookReviewEntity entity);

    @Override
    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "localDateToTimestamp")
    @Mapping(target = "finishDate", source = "finishDate", qualifiedByName = "localDateToTimestamp")
    BookReviewEntity toEntity(CreateReviewReqDto dto);

    @Mapping(target = "startDate", source = "startDate", qualifiedByName = "localDateToTimestamp")
    @Mapping(target = "finishDate", source = "finishDate", qualifiedByName = "localDateToTimestamp")
    void updateEntityFromDto(UpdateReviewReqDto dto, @MappingTarget BookReviewEntity entity);

}
