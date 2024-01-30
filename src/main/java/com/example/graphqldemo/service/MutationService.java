package com.example.graphqldemo.service;

import com.example.graphqldemo.model.Author;
import com.example.graphqldemo.model.Book;
import com.example.graphqldemo.repository.AuthorRepository;
import com.example.graphqldemo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MutationService.
 *
 * @author Rustam Bikiteev
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MutationService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public Author saveAuthor(String name, List<String> bookTitles) {
        log.debug("name: {}, bookIds: {}", name, bookTitles);
        Author author = new Author().setName(name);

        if (bookTitles != null && !bookTitles.isEmpty()){
            List<Book> books = bookTitles.stream()
                    .map(title -> bookRepository.findByTitle(title)
                            .orElse(bookRepository.save(new Book().setTitle(title))))
                    .collect(Collectors.toList());
            author.setBooks(books);
        }

        log.debug("author: {}", author);
        return authorRepository.save(author);
    }

    public Book saveBook(String title, List<String> authorNames) {
        log.debug("title: {}, authorIds: {}", title, authorNames);
        Book book = new Book().setTitle(title);

        if (authorNames != null && !authorNames.isEmpty()){
            List<Author> authors = authorNames.stream()
                    .map(name -> authorRepository.findByName(name)
                            .orElse(authorRepository.save(new Author().setName(name))))
                    .collect(Collectors.toList());
            book.setAuthors(authors);
        }
        log.debug("book: {}", book);
        checkBookDuplicate(book);
        return bookRepository.save(book);
    }

    private void checkBookDuplicate(Book book) {
        if (!bookRepository.findByTitle(book.getTitle()).isEmpty() && bookRepository.findByTitle(book.getTitle()).get().equals(book)){
        }
    }
}
