package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.data.entity.LovedListEntity;
import hu.unideb.timi15.mybookshelf.common.repository.BaseListRepository;
import hu.unideb.timi15.mybookshelf.mapper.ListMapper;
import hu.unideb.timi15.mybookshelf.data.repository.BookRepository;
import hu.unideb.timi15.mybookshelf.data.repository.LovedListRepository;
import hu.unideb.timi15.mybookshelf.service.BookService;
import hu.unideb.timi15.mybookshelf.service.dto.book.CreateBookReqDto;
import hu.unideb.timi15.mybookshelf.common.service.BaseListService;
import org.springframework.stereotype.Service;

@Service
public class LovedListServiceImpl extends BaseListService<LovedListEntity> {

    private final LovedListRepository lovedListRepository;

    public LovedListServiceImpl(
            LovedListRepository lovedListRepository,
            BookRepository bookRepository,
            BookService bookService,
            ListMapper listMapper
    ) {
        super(bookRepository, bookService, listMapper);
        this.lovedListRepository = lovedListRepository;
    }


    @Override
    protected BaseListRepository<LovedListEntity> getRepository() {
        return lovedListRepository;
    }

    @Override
    protected LovedListEntity mapToEntity(CreateBookReqDto dto) {
        return listMapper.toLovedListEntity(dto);
    }

    @Override
    protected String duplicateMessage() {
        return "Book is already in loved list";
    }
}

