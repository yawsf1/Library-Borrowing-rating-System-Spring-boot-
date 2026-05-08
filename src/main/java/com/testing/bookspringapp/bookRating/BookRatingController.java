package com.testing.bookspringapp.bookRating;


import com.testing.bookspringapp.PageResponse;
import com.testing.bookspringapp.book.BookRequest;
import com.testing.bookspringapp.user.User;
import com.testing.bookspringapp.user.UserResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rates")
@RequiredArgsConstructor
public class BookRatingController {
    private final BookRatingService service;

    // 1. Create: User adds their own rate
    @PostMapping
    public void addRate(@Valid @RequestBody BookRatingRequest request, Authentication auth) {
        service.addRate(request, auth);
    }

    // 2. Read (Self): User views their own history
    @GetMapping("/me")
    public PageResponse<BookRatingResponse> getMyRates(
            Authentication auth,
            Pageable pageable) {
        return service.getRateByUser(auth, pageable);
    }

    // 3. Read (Public): Everyone sees ratings for a book
    @GetMapping("/book/{bookId}")
    public PageResponse<BookRatingResponse> getRatesByBook(
            @PathVariable Integer bookId,
            Pageable pageable) {
        return service.getRateByBook(bookId, pageable);
    }

    // 4. Update: User modifies their own rate (Service checks ownership)
    @PutMapping("/{id}")
    public void modifyRate(@PathVariable Integer id, @Valid @RequestBody BookRatingRequest request, Authentication auth) {
        service.modifyRate(id, request, auth);
    }

    // 5. Delete: User deletes their own rate (Service checks ownership)
    @DeleteMapping("/{id}")
    public void deleteRate(@PathVariable Integer id, Authentication auth) {
        service.deleteRate(id, auth);
    }
}