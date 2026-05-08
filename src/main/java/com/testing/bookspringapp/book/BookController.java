package com.testing.bookspringapp.book;

import com.testing.bookspringapp.PageResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    public final BookService bookService;
    @GetMapping
    public PageResponse<BookResponse> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy

    ){
        return bookService.getAllBooks(page, size, sortBy);
    }


    @GetMapping(path = "{id}")
    public BookResponse getBook(@PathVariable Integer id){
        return bookService.getBook(id);
    }

    @PostMapping
    public void addBook(@Valid @RequestBody BookRequest bookRequest){
        bookService.addUser(bookRequest);
    }

    @PutMapping(path = "{id}")
    public void updateBook(
            @PathVariable Integer id,
            @Valid @RequestBody BookRequest bookRequest
    ){
        bookService.updateBook(id, bookRequest);
    }

    @DeleteMapping(path = "{id}")
    public void deleteBook(@PathVariable Integer id){
        bookService.deleteBook(id);
    }
}
