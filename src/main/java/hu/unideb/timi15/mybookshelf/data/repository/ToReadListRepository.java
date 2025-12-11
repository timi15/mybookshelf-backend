package hu.unideb.timi15.mybookshelf.data.repository;

import hu.unideb.timi15.mybookshelf.data.entity.ToReadListEntity;
import hu.unideb.timi15.mybookshelf.common.repository.BaseListRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToReadListRepository extends BaseListRepository<ToReadListEntity> {

}
