package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.data.entity.ToReadListEntity;
import hu.unideb.timi15.mybookshelf.data.repository.BookRepository;
import hu.unideb.timi15.mybookshelf.data.repository.ToReadListRepository;
import hu.unideb.timi15.mybookshelf.mapper.ListMapper;
import hu.unideb.timi15.mybookshelf.service.BookService;
import hu.unideb.timi15.mybookshelf.service.dto.book.CreateBookReqDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ToReadListServiceImplTest {

    @Mock
    ToReadListRepository toReadListRepository;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookService bookService;

    @Mock
    ListMapper listMapper;

    ToReadListServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new ToReadListServiceImpl(
                toReadListRepository,
                bookRepository,
                bookService,
                listMapper
        );
    }

    @Test
    void getRepository_returnsToReadListRepository() {
        assertSame(toReadListRepository, service.getRepository());
    }


    @Test
    void mapToEntity_delegatesToMapper() {
        CreateBookReqDto dto = new CreateBookReqDto();
        ToReadListEntity entity = new ToReadListEntity();

        when(listMapper.toToReadListEntity(dto))
                .thenReturn(entity);

        ToReadListEntity result = service.mapToEntity(dto);

        assertNotNull(result);
        verify(listMapper).toToReadListEntity(dto);
    }
}
