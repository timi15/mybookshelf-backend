package hu.unideb.timi15.mybookshelf.repository;

import hu.unideb.timi15.mybookshelf.entity.BookReviewEntity;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends FirestoreReactiveRepository<BookReviewEntity> {
}
