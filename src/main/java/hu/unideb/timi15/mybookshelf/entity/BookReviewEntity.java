package hu.unideb.timi15.mybookshelf.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collectionName = "book_reviews")
public class BookReviewEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @DocumentId
    private String documentId;

    private String isbn13;
    private String userId;
    private Timestamp startDate;
    private Timestamp finishDate;
    private String reflection;
    private Integer rate;
}
