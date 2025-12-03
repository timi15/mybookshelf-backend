package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.entity.LovedListEntity;
import hu.unideb.timi15.mybookshelf.exception.AlreadyInListException;
import hu.unideb.timi15.mybookshelf.exception.NotFoundException;
import hu.unideb.timi15.mybookshelf.mapper.ListMapper;
import hu.unideb.timi15.mybookshelf.repository.BookRepository;
import hu.unideb.timi15.mybookshelf.repository.LovedListRepository;
import hu.unideb.timi15.mybookshelf.service.BookService;
import hu.unideb.timi15.mybookshelf.service.ListService;
import hu.unideb.timi15.mybookshelf.service.dto.book.request.CreateBookRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.list.response.ListItemResponseDTO;
import hu.unideb.timi15.mybookshelf.utils.FirebaseAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LovedListServiceImpl implements ListService {

    private final LovedListRepository lovedListRepository;
    private final BookRepository bookRepository;
    private final BookService bookService;
    private final ListMapper listMapper;

    @Override
    public ListItemResponseDTO addToList(String token, CreateBookRequestDTO dto) {
        String userId = FirebaseAuthUtil.getUserId(token);

        BookEntity book = bookRepository.findByIsbn13(dto.getIsbn13()).block();
        if (book == null) {
            book = BookEntity.builder()
                    .isbn13(dto.getIsbn13())
                    .title(dto.getTitle())
                    .author(dto.getAuthor())
                    .image(dto.getImage())
                    .plot(dto.getPlot())
                    .build();
        }
        bookService.addOrGetBook(book, userId);

        if(null != lovedListRepository.findByUserIdAndIsbn13(userId, dto.getIsbn13()).block()){
            throw new AlreadyInListException("Book is already in loved list");
        }

        LovedListEntity entity = listMapper.toLovedListEntity(dto);
        entity.setUserId(userId);

        lovedListRepository.save(entity).block();

        return listMapper.toDto(entity, book);
    }

    @Override
    public List<ListItemResponseDTO> getList(String token) {
        String userId = FirebaseAuthUtil.getUserId(token);

        return lovedListRepository.findByUserId(userId)
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

        LovedListEntity entity = lovedListRepository
                .findByUserIdAndIsbn13(userId, isbn13).block();

        if (null == entity) {
            throw new NotFoundException("Book not found in the list: " + isbn13);
        }

        lovedListRepository.delete(entity).block();
    }
}

