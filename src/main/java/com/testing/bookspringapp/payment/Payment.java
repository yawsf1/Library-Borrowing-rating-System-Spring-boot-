package com.testing.bookspringapp.payment;

import com.testing.bookspringapp.BaseEntity;
import com.testing.bookspringapp.book.Book;
import com.testing.bookspringapp.bookBorrowing.BookBorrowing;
import com.testing.bookspringapp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Double amount;
}

