package com.example.graphqldemo.service;

import com.example.graphqldemo.model.Author;
import com.example.graphqldemo.model.Book;
import com.example.graphqldemo.repository.AuthorRepository;
import com.example.graphqldemo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * QueryService.
 *
 * @author Rustam Bikiteev
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class QueryService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public Author getAuthor(String name){
        Author author = authorRepository.findByName(name).orElseThrow();
        log.debug("author: {}", author);
        return author;
    }

    public List<Book> getBooksByAuthor(Long authorId){
       List<Book> books = bookRepository.getBooksByAuthorId(authorId);
       log.debug("books: {}", books);
       return books;
    }

    public List<Book> getAllBooks(){
        List<Book> books = bookRepository.findAll();
        log.debug("books: {}", books);
        return books;
    }
}
