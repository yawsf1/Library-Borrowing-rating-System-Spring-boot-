package com.testing.bookspringapp.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Integer> {
    @Query("Select AVG(b.rate) from BookRating b where b.book.id = :id")
    Double getAverageRatingByBookId(Integer id);
}
