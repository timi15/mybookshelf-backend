package hu.unideb.timi15.mybookshelf.common.service;

import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.data.repository.BookRepository;
import hu.unideb.timi15.mybookshelf.common.entity.BaseListEntity;
import hu.unideb.timi15.mybookshelf.common.repository.BaseListRepository;
import hu.unideb.timi15.mybookshelf.exception.AlreadyInListException;
import hu.unideb.timi15.mybookshelf.exception.NotFoundException;
import hu.unideb.timi15.mybookshelf.mapper.ListMapper;
import hu.unideb.timi15.mybookshelf.service.BookService;
import hu.unideb.timi15.mybookshelf.service.ListService;
import hu.unideb.timi15.mybookshelf.service.dto.list.ListItemResDto;
import hu.unideb.timi15.mybookshelf.service.dto.book.CreateBookReqDto;
import hu.unideb.timi15.mybookshelf.utils.FirebaseAuthUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class BaseListService<T extends BaseListEntity> implements ListService {

    protected final BookRepository bookRepository;
    protected final BookService bookService;
    protected final ListMapper listMapper;

    protected BaseListService(BookRepository bookRepository,
                              BookService bookService,
                              ListMapper listMapper) {
        this.bookRepository = bookRepository;
        this.bookService = bookService;
        this.listMapper = listMapper;
    }

    protected abstract BaseListRepository<T> getRepository();

    protected abstract T mapToEntity(CreateBookReqDto dto);

    protected abstract String duplicateMessage();

    @Override
    public ListItemResDto addToList(String token, CreateBookReqDto dto) {
        String userId = FirebaseAuthUtil.getUserId(token);

        BookEntity book = bookRepository.findByIsbn13(dto.getIsbn13()).block();
        if (book == null) {
            book = BookEntity.builder()
                    .isbn13(dto.getIsbn13())
                    .title(dto.getTitle())
                    .author(dto.getAuthor())
                    .coverUrl(dto.getCoverUrl())
                    .plot(dto.getPlot())
                    .build();
        }
        bookService.addOrGetBook(book, userId);

        if (getRepository().findByUserIdAndIsbn13(userId, dto.getIsbn13()).block() != null) {
            throw new AlreadyInListException(duplicateMessage());
        }

        T entity = mapToEntity(dto);
        entity.setUserId(userId);

        getRepository().save(entity).block();

        return listMapper.toDto(entity, book);
    }

    @Override
    public List<ListItemResDto> getList(String token) {
        String userId = FirebaseAuthUtil.getUserId(token);

        return getRepository().findByUserId(userId)
                .toStream()
                .map(entity -> {
                    BookEntity book = bookRepository.findByIsbn13(entity.getIsbn13()).block();
                    return listMapper.toDto(entity, book);
                })
                .toList();
    }

    @Override
    public void removeFromList(String token, String isbn13) {
        String userId = FirebaseAuthUtil.getUserId(token);

        T entity = getRepository().findByUserIdAndIsbn13(userId, isbn13).block();

        if (entity == null) {
            throw new NotFoundException("Book not found in the list: " + isbn13);
        }

        getRepository().delete(entity).block();
    }
}

