package com.testing.bookspringapp.bookBorrowing;

import com.testing.bookspringapp.BaseEntity;
import com.testing.bookspringapp.book.Book;
import com.testing.bookspringapp.payment.Payment;
import com.testing.bookspringapp.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class BookBorrowing extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer quantite;

    private Integer time;

    private boolean returned;
    @ManyToOne
    private Book book;

    @ManyToOne
    private User user;

    @OneToOne
    @JoinColumn(nullable = true)
    private Payment payment;

}
