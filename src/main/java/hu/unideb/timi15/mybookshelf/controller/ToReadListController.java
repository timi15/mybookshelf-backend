package hu.unideb.timi15.mybookshelf.controller;

import hu.unideb.timi15.mybookshelf.service.ListService;
import hu.unideb.timi15.mybookshelf.service.dto.list.ListItemResDto;
import hu.unideb.timi15.mybookshelf.service.dto.book.CreateBookReqDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("v1/mybookshelf/to-read")
public class ToReadListController {

    private final ListService listService;

    public ToReadListController(@Qualifier("toReadListServiceImpl") ListService listService) {
        this.listService = listService;
    }

    @PostMapping
    public ListItemResDto addToToReadList(
            @RequestHeader("Authorization") String token,
            @RequestBody CreateBookReqDto dto
    ) {
        return listService.addToList(token, dto);
    }

    @GetMapping
    public List<ListItemResDto> getToReadList(@RequestHeader("Authorization") String token) {
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
