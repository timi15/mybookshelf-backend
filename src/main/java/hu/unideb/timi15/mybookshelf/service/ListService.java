package hu.unideb.timi15.mybookshelf.service;

import hu.unideb.timi15.mybookshelf.service.dto.list.ListItemResDto;
import hu.unideb.timi15.mybookshelf.service.dto.book.CreateBookReqDto;

import java.util.List;

public interface ListService {

    List<ListItemResDto> getList(String token);

    ListItemResDto addToList(String token, CreateBookReqDto dto);

    void removeFromList(String token, String isbn13);

}
