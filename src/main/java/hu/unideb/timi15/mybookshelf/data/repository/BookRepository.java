package hu.unideb.timi15.mybookshelf.data.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepository extends FirestoreReactiveRepository<BookEntity> {

    Mono<BookEntity> findByIsbn13(String isbn13);

}
