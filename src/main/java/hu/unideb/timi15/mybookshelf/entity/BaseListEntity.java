package hu.unideb.timi15.mybookshelf.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public abstract class BaseListEntity {

    @DocumentId
    private String documentId;

    private String userId;
    private String isbn13;
}
