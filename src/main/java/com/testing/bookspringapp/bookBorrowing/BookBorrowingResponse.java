package com.testing.bookspringapp.bookBorrowing;

import com.testing.bookspringapp.book.BookResponse;
import com.testing.bookspringapp.user.UserResponse;


public record BookBorrowingResponse (
    Integer id,
    Integer quantite,
    Integer date,
    Boolean returned,
    UserResponse user,
    BookResponse book
){
}
