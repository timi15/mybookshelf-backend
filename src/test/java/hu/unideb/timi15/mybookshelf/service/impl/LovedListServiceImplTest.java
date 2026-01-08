package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.data.entity.LovedListEntity;
import hu.unideb.timi15.mybookshelf.data.repository.BookRepository;
import hu.unideb.timi15.mybookshelf.data.repository.LovedListRepository;
import hu.unideb.timi15.mybookshelf.mapper.ListMapper;
import hu.unideb.timi15.mybookshelf.service.BookService;
import hu.unideb.timi15.mybookshelf.service.dto.book.CreateBookReqDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LovedListServiceImplTest {

    @Mock
    LovedListRepository lovedListRepository;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookService bookService;

    @Mock
    ListMapper listMapper;

    LovedListServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new LovedListServiceImpl(
                lovedListRepository,
                bookRepository,
                bookService,
                listMapper
        );
    }

    @Test
    void getRepository_returnsLovedListRepository() {
        assertSame(lovedListRepository, service.getRepository());
    }

    @Test
    void mapToEntity_delegatesToMapper() {
        CreateBookReqDto dto = new CreateBookReqDto();
        LovedListEntity entity = new LovedListEntity();

        when(listMapper.toLovedListEntity(dto))
                .thenReturn(entity);

        LovedListEntity result = service.mapToEntity(dto);

        assertNotNull(result);
        verify(listMapper).toLovedListEntity(dto);
    }
}
