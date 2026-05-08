package com.testing.bookspringapp.bookRating;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRatingRepository extends JpaRepository<BookRating, Integer> {

    @EntityGraph(attributePaths = {"user", "book"})
    Page<BookRating> findByUserId(Integer userId, Pageable pageable);

    // for normal List<BookRating> findByBookId(Integer id);
    // for pagination:
    @EntityGraph(attributePaths = {"user", "book"})
    Page<BookRating> findByBookId(Integer bookId, Pageable pageable);
    boolean existsByUserIdAndBookId(Integer userId, Integer bookId);

    Optional<BookRating> findByUserIdAndBookId(Integer userId, Integer bookId);

    void deleteByUserId(Integer id);
    void deleteByBookId(Integer id);


    @Query("SELECT COALESCE(AVG(r.rate), 0.0) FROM BookRating r WHERE r.book.id = :bookId")
    Double getAverageRatingByBookId(Integer bookId);

    @Query("SELECT COUNT(r) FROM BookRating r WHERE r.book.id = :bookId")
    Integer getCountByBookId(Integer bookId);


}
