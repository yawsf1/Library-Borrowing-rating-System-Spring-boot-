package com.testing.bookspringapp.book;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.function.Function;

@Component
public class BookDTOMapper implements Function<Book, BookResponse> {

    @Override
    public BookResponse apply(Book book) {
        return new BookResponse(
                book.getId(),
                book.getName(),
                Year.of(book.getYear()),
                book.getGenre(),
                book.getStock(),
                book.getPrice(),
                book.getAverageRate(),
                book.getRatingCount()
        );
    }
    public Book toEntity(BookRequest bookRequest){
        Book book = new Book();
        book.setName(bookRequest.name());
        book.setYear(bookRequest.year());
        book.setGenre(bookRequest.genre());
        return book;
    }
}
