package hu.unideb.timi15.mybookshelf.data.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.spring.data.firestore.Document;
import hu.unideb.timi15.mybookshelf.data.entity.common.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collectionName = "book_reviews")
public class BookReviewEntity extends BaseEntity {

    private String isbn13;
    private String userId;
    private Timestamp startDate;
    private Timestamp finishDate;
    private List<String> genres;
    private String reflection;
    private Integer rate;

}
