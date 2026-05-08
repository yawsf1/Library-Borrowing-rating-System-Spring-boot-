package com.testing.bookspringapp.bookBorrowing;


import com.testing.bookspringapp.PageResponse;
import com.testing.bookspringapp.bookRating.BookRatingResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/borrows")
public class BookBorrowingController {
    public final BookBorrowingService bookBorrowingService;

    @PostMapping
    public void borrowBook(
            @Valid @RequestBody BookBorrowingRequest bookBorrowingRequest,
            Authentication authentication
    ){
        bookBorrowingService.borrowingBook(bookBorrowingRequest, authentication);
    }

    @DeleteMapping(path = "{id}")
    public void cancelBorrowing(@PathVariable Integer id, Authentication authentication){
        bookBorrowingService.cancelBorrowing(id, authentication);
    }

    @GetMapping("/me")
    public PageResponse<BookBorrowingResponse> getMyBorrowings(Authentication authentication, Pageable pageable){
        return bookBorrowingService.getMyBorrowings(authentication, pageable);
    }

}
