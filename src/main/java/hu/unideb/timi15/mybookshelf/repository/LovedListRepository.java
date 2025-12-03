package hu.unideb.timi15.mybookshelf.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import hu.unideb.timi15.mybookshelf.entity.LovedListEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface LovedListRepository extends FirestoreReactiveRepository<LovedListEntity> {


    Flux<LovedListEntity> findByUserId(String userId);

    Mono<LovedListEntity> findByUserIdAndIsbn13(String userId, String isbn13);

}
