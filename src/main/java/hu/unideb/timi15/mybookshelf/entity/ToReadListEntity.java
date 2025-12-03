package hu.unideb.timi15.mybookshelf.entity;

import com.google.cloud.spring.data.firestore.Document;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collectionName = "to_read")
public class ToReadListEntity extends BaseListEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

}
