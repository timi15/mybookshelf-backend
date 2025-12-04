package hu.unideb.timi15.mybookshelf.mapper.common;

import hu.unideb.timi15.mybookshelf.data.entity.common.BaseEntity;
import org.mapstruct.MapperConfig;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@MapperConfig(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BaseMapper<CreateReqT, UpdateReqT, ResT, E extends BaseEntity> {

    ResT toResponseDTO(E entity);

    E toEntity(CreateReqT dto);

    List<ResT> map(List<E> entities);

    void updateEntityFromDto(UpdateReqT dto, @MappingTarget E entity);
}