package hu.unideb.timi15.mybookshelf.service;

import hu.unideb.timi15.mybookshelf.service.dto.book.request.CreateBookRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.list.response.ListItemResponseDTO;

import java.util.List;

public interface ListService {

    ListItemResponseDTO addToList(String token, CreateBookRequestDTO dto);

    List<ListItemResponseDTO> getList(String token);

    void removeFromList(String token, String isbn13);

}
