package hu.unideb.timi15.mybookshelf.data.entity;

import com.google.cloud.spring.data.firestore.Document;
import hu.unideb.timi15.mybookshelf.data.entity.common.BaseListEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collectionName = "loved")
public class LovedListEntity extends BaseListEntity {

}
