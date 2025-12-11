package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.data.entity.ToReadListEntity;
import hu.unideb.timi15.mybookshelf.data.repository.BookRepository;
import hu.unideb.timi15.mybookshelf.data.repository.ToReadListRepository;
import hu.unideb.timi15.mybookshelf.common.repository.BaseListRepository;
import hu.unideb.timi15.mybookshelf.mapper.ListMapper;
import hu.unideb.timi15.mybookshelf.service.BookService;
import hu.unideb.timi15.mybookshelf.service.dto.book.CreateBookRequestDTO;
import hu.unideb.timi15.mybookshelf.common.service.BaseListService;
import org.springframework.stereotype.Service;

@Service
public class ToReadListServiceImpl extends BaseListService<ToReadListEntity> {

    private final ToReadListRepository toReadListRepository;

    public ToReadListServiceImpl(
            ToReadListRepository toReadListRepository,
            BookRepository bookRepository,
            BookService bookService,
            ListMapper listMapper
    ) {
        super(bookRepository, bookService, listMapper);
        this.toReadListRepository = toReadListRepository;
    }


    @Override
    protected BaseListRepository<ToReadListEntity> getRepository() {
        return toReadListRepository;
    }

    @Override
    protected ToReadListEntity mapToEntity(CreateBookRequestDTO dto) {
        return listMapper.toToReadListEntity(dto);
    }

    @Override
    protected String duplicateMessage() {
        return "Book is already in to-read list";
    }
}
