package hu.unideb.timi15.mybookshelf.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import hu.unideb.timi15.mybookshelf.entity.LovedListEntity;
import hu.unideb.timi15.mybookshelf.entity.ToReadListEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ToReadListRepository extends FirestoreReactiveRepository<ToReadListEntity> {

    Flux<ToReadListEntity> findByUserId(String userId);

    Mono<ToReadListEntity> findByUserIdAndIsbn13(String userId, String isbn13);

}
