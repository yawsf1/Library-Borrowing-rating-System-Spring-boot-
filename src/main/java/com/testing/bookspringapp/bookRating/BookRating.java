package com.testing.bookspringapp.bookRating;

import com.testing.bookspringapp.BaseEntity;
import com.testing.bookspringapp.book.Book;
import com.testing.bookspringapp.user.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "book_id"})
})
public class BookRating extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer rate;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

}
