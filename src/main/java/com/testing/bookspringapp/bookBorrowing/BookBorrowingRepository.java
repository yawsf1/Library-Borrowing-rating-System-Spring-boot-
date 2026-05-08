package com.testing.bookspringapp.bookBorrowing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookBorrowingRepository extends JpaRepository<BookBorrowing, Integer> {
    @EntityGraph(attributePaths = {"user", "book"})
    Page<BookBorrowing> findByUserId(Integer id, Pageable pageable);
}
