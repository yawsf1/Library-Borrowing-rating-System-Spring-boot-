package com.testing.bookspringapp.bookRating;

import com.testing.bookspringapp.book.BookDTOMapper;
import com.testing.bookspringapp.book.BookRequest;
import com.testing.bookspringapp.user.UserDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.function.Function;


@Component
@AllArgsConstructor
public class BookRatingDTOMapper implements Function<BookRating, BookRatingResponse> {
    private final UserDTOMapper userDTOMapper;
    private final BookDTOMapper bookDTOMapper;
    @Override
    public BookRatingResponse apply(BookRating bookRating) {
        return new BookRatingResponse(
                bookRating.getRate(),
                userDTOMapper.apply(bookRating.getUser()),
                bookDTOMapper.apply(bookRating.getBook())
        );
    }
    public BookRating toEntity(BookRatingRequest bookRatingRequest){
        BookRating bookRating = new BookRating();
        bookRating.setRate(bookRatingRequest.rate());
        bookRating.setBook(bookRatingRequest.book());
        return bookRating;
    }
}
