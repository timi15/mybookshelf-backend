package hu.unideb.timi15.mybookshelf.controller;

import hu.unideb.timi15.mybookshelf.service.ListService;
import hu.unideb.timi15.mybookshelf.service.dto.book.request.CreateBookRequestDTO;
import hu.unideb.timi15.mybookshelf.service.dto.list.ListItemResponseDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/mybookshelf/loved")
public class LovedListController {

    private final ListService listService;

    public LovedListController(@Qualifier("lovedListServiceImpl") ListService listService) {
        this.listService = listService;
    }

    @PostMapping("/add")
    public ListItemResponseDTO addToLoved(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateBookRequestDTO dto
    ) {
        return listService.addToList(token, dto);
    }

    @GetMapping
    public List<ListItemResponseDTO> getLovedList(
            @RequestHeader("Authorization") String token
    ) {
        return listService.getList(token);
    }

    @DeleteMapping("/{isbn13}")
    public void removeFromLovedList(
            @RequestHeader("Authorization") String token,
            @PathVariable String isbn13
    ) {
        listService.removeFromList(token, isbn13);
    }

}
