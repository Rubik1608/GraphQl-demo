package com.example.graphqldemo.repository;

import com.example.graphqldemo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * AuthorRepository.
 *
 * @author Rustam Bikiteev
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByName(String name);

}
