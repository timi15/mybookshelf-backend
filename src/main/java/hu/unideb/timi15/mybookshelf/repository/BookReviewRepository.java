package hu.unideb.timi15.mybookshelf.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import hu.unideb.timi15.mybookshelf.entity.BookReviewEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookReviewRepository extends FirestoreReactiveRepository<BookReviewEntity> {

    Mono<Boolean> existsByIsbn13(String isbn13);

    Mono<BookReviewEntity> findByIsbn13(String isbn13);

}
