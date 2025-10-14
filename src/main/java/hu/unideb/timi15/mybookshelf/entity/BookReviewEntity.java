package hu.unideb.timi15.mybookshelf.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookReviewEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @DocumentId
    private String documentId;

    private String isbn13;

    private String userId;

    private Instant startDate;

    private Instant finishDate;

    private String plot;

    private String reflection;

    private Integer rating;
}
