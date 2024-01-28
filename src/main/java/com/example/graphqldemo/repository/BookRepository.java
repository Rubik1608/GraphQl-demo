package com.example.graphqldemo.repository;

import com.example.graphqldemo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * BookRepository.
 *
 * @author Rustam Bikiteev
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId")
    List<Book> getBooksByAuthorId(@Param("authorId") Long authorId);

    Optional<Book> findByTitle(String title);

}
