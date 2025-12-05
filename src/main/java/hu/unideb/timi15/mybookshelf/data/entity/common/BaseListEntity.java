package hu.unideb.timi15.mybookshelf.data.entity.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class BaseListEntity extends BaseEntity {

    private String userId;
    private String isbn13;

}
