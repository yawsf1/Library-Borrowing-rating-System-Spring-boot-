package com.testing.bookspringapp.bookRating;

import com.testing.bookspringapp.book.Book;
import com.testing.bookspringapp.user.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BookRatingRequest(
        @NotNull(message = "Book required")
        Book book,

        @Min(value = 0, message = "Rate can't be under 0 stars")
        @Max(value = 5, message = "Rate can't be more than 5 starts")
        Integer rate
) {
}
