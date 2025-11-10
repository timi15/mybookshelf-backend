package hu.unideb.timi15.mybookshelf.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import hu.unideb.timi15.mybookshelf.entity.BookReviewEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookReviewRepository extends FirestoreReactiveRepository<BookReviewEntity> {

    Mono<Boolean> existsByUserIdAndIsbn13(String userId, String isbn13);

    Mono<BookReviewEntity> findByUserIdAndIsbn13(String userId, String isbn13);

    Flux<BookReviewEntity> findAllByUserId(String userId);

    Mono<Void> deleteByUserIdAndIsbn13(String userId, String isbn13);
}
