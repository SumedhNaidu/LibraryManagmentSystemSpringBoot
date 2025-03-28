package com.digitallibrary.repository;

import com.digitallibrary.model.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    // Existing methods
    Optional<Book> findByBookId(String bookId);

    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAvailable(boolean available);

    List<Book> findByGenre(String genre);

    // New methods to match your test cases
    boolean existsByBookId(String bookId);

//    boolean deleteByBookId(String bookId);
    @Modifying
    @Transactional
    @Query("DELETE FROM Book b WHERE b.bookId = :bookId")
    int deleteByBookId(@Param("bookId") String bookId);

}