package com.testing.bookspringapp.book;

import jakarta.validation.constraints.*;

public record BookRequest(
        @NotBlank(message = "Name of the book is required")
        @Size(min = 3, max = 500, message = "Name must be between 3 and 500 characters")
        String name,

        @Min(value = 1000, message = "Enter a valid year")
        @Max(value = 2025, message = "Enter a valid year")
        Integer year,

        @NotBlank(message = "Genre is required")
        @Size(min = 3, max = 500, message = "Genre must be between 3 and 500 characters")
        String genre,

        @Size(min = 3, max = 500, message = "Author name must be between 3 and 500 characters")
        String author,

        @Min(value = 0, message = "Stock must be 0 or greater")
        Integer stock,

        @DecimalMin(value = "0.00", message = "Price must be 0.00 or greater")
        Double price
) {

}