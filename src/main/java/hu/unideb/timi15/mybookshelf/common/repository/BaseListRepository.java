package hu.unideb.timi15.mybookshelf.common.repository;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import hu.unideb.timi15.mybookshelf.common.entity.BaseListEntity;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@NoRepositoryBean
public interface BaseListRepository<T extends BaseListEntity> extends FirestoreReactiveRepository<T> {

    Flux<T> findByUserId(String userId);

    Mono<T> findByUserIdAndIsbn13(String userId, String isbn13);

}
