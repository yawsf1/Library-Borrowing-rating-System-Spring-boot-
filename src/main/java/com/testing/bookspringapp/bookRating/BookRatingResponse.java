package com.testing.bookspringapp.bookRating;

import com.testing.bookspringapp.book.Book;
import com.testing.bookspringapp.book.BookResponse;
import com.testing.bookspringapp.user.UserResponse;

public record BookRatingResponse(
    Integer rate,
    UserResponse user,
    BookResponse book
) {
}
