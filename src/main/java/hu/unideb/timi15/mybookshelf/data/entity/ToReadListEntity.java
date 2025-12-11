package hu.unideb.timi15.mybookshelf.data.entity;

import com.google.cloud.spring.data.firestore.Document;
import hu.unideb.timi15.mybookshelf.common.entity.BaseListEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collectionName = "to_read")
public class ToReadListEntity extends BaseListEntity {

}
