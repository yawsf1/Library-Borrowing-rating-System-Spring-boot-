package com.testing.bookspringapp.book;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.testing.bookspringapp.BaseEntity;
import com.testing.bookspringapp.bookBorrowing.BookBorrowing;
import com.testing.bookspringapp.bookRating.BookRating;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Book extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer year;
    private String genre;
    private Integer stock;
    private Double price;
    private String author;
    private Double averageRate = 0.0;
    private Integer ratingCount = 0;


    @OneToMany(mappedBy = "book")
    private List<BookRating> userBooks = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<BookBorrowing> bookBorrowings = new ArrayList<>();

}
