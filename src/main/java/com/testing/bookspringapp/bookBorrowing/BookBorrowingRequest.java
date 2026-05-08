package com.testing.bookspringapp.bookBorrowing;

import com.testing.bookspringapp.book.Book;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record BookBorrowingRequest (
    @Min(value = 1, message = "Quantite can't not be less than 1")
    Integer quantite,

    @Min(value = 1, message = "You must be borrowing a book for at least one day")
    @Max(value = 60, message = "You can't borrow a book for more than 2 months (you can borrow it again after returning it)")
    Integer time,

    boolean returned,
    @NotNull(message = "Book required")
    Book book
){
}
