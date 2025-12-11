package hu.unideb.timi15.mybookshelf.service;

import hu.unideb.timi15.mybookshelf.service.dto.book.CreateBookRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.list.ListItemResponseDTO;

import java.util.List;

public interface ListService {

    List<ListItemResponseDTO> getList(String token);

    ListItemResponseDTO addToList(String token, CreateBookRequestDTO dto);

    void removeFromList(String token, String isbn13);

}
