package hu.unideb.timi15.mybookshelf.data.repository;

import hu.unideb.timi15.mybookshelf.data.entity.LovedListEntity;
import hu.unideb.timi15.mybookshelf.data.repository.common.BaseListRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LovedListRepository extends BaseListRepository<LovedListEntity> {

}
