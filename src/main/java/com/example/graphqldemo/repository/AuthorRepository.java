package com.example.graphqldemo.repository;

import com.example.graphqldemo.model.Author;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * AuthorRepository.
 *
 * @author Rustam Bikiteev
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @EntityGraph(attributePaths = {"books"})
    Optional<Author> findByName(String name);

    @EntityGraph(attributePaths = {"books"})
    List<Author> findAll();
}
