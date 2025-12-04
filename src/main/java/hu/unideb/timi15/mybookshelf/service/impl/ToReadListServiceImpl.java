package hu.unideb.timi15.mybookshelf.service.impl;

import hu.unideb.timi15.mybookshelf.data.entity.BookEntity;
import hu.unideb.timi15.mybookshelf.data.entity.ToReadListEntity;
import hu.unideb.timi15.mybookshelf.exception.AlreadyInListException;
import hu.unideb.timi15.mybookshelf.exception.NotFoundException;
import hu.unideb.timi15.mybookshelf.mapper.ListMapper;
import hu.unideb.timi15.mybookshelf.data.repository.BookRepository;
import hu.unideb.timi15.mybookshelf.data.repository.ToReadListRepository;
import hu.unideb.timi15.mybookshelf.service.BookService;
import hu.unideb.timi15.mybookshelf.service.ListService;
import hu.unideb.timi15.mybookshelf.service.dto.book.request.CreateBookRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.list.ListItemResponseDTO;
import hu.unideb.timi15.mybookshelf.utils.FirebaseAuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToReadListServiceImpl implements ListService {

    private final ToReadListRepository toReadListRepository;
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

        if (null != toReadListRepository.findByUserIdAndIsbn13(userId, dto.getIsbn13()).block()) {
            throw new AlreadyInListException("Book is already in to read list");
        }

        ToReadListEntity entity = listMapper.toToReadListEntity(dto);
        entity.setUserId(userId);

        toReadListRepository.save(entity).block();

        return listMapper.toDto(entity, book);
    }

    @Override
    public List<ListItemResponseDTO> getList(String token) {
        String userId = FirebaseAuthUtil.getUserId(token);

        return toReadListRepository.findByUserId(userId)
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

        ToReadListEntity entity = toReadListRepository
                .findByUserIdAndIsbn13(userId, isbn13).block();

        if (null == entity) {
            throw new NotFoundException("Book not found in the list: " + isbn13);
        }

        toReadListRepository.delete(entity).block();
    }
}
