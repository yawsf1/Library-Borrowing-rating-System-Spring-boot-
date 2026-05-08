package com.testing.bookspringapp.bookBorrowing;

import com.testing.bookspringapp.book.BookDTOMapper;
import com.testing.bookspringapp.bookRating.BookRatingDTOMapper;
import com.testing.bookspringapp.user.UserDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
@AllArgsConstructor
public class BookBorrowingDTOMapper implements Function<BookBorrowing, BookBorrowingResponse> {
    private final UserDTOMapper userDTOMapper;
    private final BookDTOMapper bookDTOMapper;

    @Override
    public BookBorrowingResponse apply(BookBorrowing bookBorrowing) {
        return new BookBorrowingResponse(
                bookBorrowing.getId(),
                bookBorrowing.getQuantite(),
                bookBorrowing.getTime(),
                bookBorrowing.isReturned(),
                userDTOMapper.apply(bookBorrowing.getUser()),
                bookDTOMapper.apply(bookBorrowing.getBook())
        );
    }

    public BookBorrowing toEntity(BookBorrowingRequest request) {
        BookBorrowing bookBorrowing = new BookBorrowing();
        bookBorrowing.setQuantite(request.quantite());
        bookBorrowing.setTime(request.time());
        bookBorrowing.setReturned(false);
        return bookBorrowing;
    }
}