package hu.unideb.timi15.mybookshelf.data.entity;

import com.google.cloud.spring.data.firestore.Document;
import hu.unideb.timi15.mybookshelf.common.entity.BaseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collectionName = "books")
public class BookEntity extends BaseEntity {

    private String isbn13;
    private String userId;
    private String coverUrl;
    private String author;
    private String title;
    private String plot;

}
