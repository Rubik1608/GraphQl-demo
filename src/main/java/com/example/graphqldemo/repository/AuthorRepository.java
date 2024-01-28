package com.example.graphqldemo.repository;

import com.example.graphqldemo.model.Author;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * AuthorRepository.
 *
 * @author Rustam Bikiteev
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a from Author a JOIN fetch a.books")
    Optional<Author> getAuthor(Long authorId);

    Optional<Author> findByName(String name);

}
