package com.testing.bookspringapp.book;

import java.time.Year;

public record BookResponse(
        Integer id,
        String name,
        Year year,
        String genre,
        Integer stock,
        Double price,
        Double rate,
        Integer ratingCount
){
}
