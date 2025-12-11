package hu.unideb.timi15.mybookshelf.controller;

import hu.unideb.timi15.mybookshelf.service.ListService;
import hu.unideb.timi15.mybookshelf.service.dto.book.CreateBookRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.list.ListItemResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/mybookshelf/to-read")
public class ToReadListController {

    private final ListService listService;

    public ToReadListController(@Qualifier("toReadListServiceImpl") ListService listService) {
        this.listService = listService;
    }

    @PostMapping("/add")
    public ListItemResponseDTO addToToReadList(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateBookRequestDTO dto
    ) {
        return listService.addToList(token, dto);
    }

    @GetMapping
    public List<ListItemResponseDTO> getToReadList(
            @RequestHeader("Authorization") String token
    ) {
        return listService.getList(token);
    }

    @DeleteMapping("/{isbn13}")
    public void removeFromToReadList(
            @RequestHeader("Authorization") String token,
            @PathVariable String isbn13
    ) {
        listService.removeFromList(token, isbn13);
    }

}
