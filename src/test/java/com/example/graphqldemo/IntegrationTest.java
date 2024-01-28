package com.example.graphqldemo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.graphqldemo.model.Author;
import com.example.graphqldemo.model.Book;
import com.example.graphqldemo.repository.AuthorRepository;
import com.example.graphqldemo.repository.BookRepository;
import com.example.graphqldemo.resolver.Mutation;
import com.example.graphqldemo.resolver.Query;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.List;

/**
 * IntegrationTest.
 *
 * @author Rustam Bikiteev
 */
@SpringBootTest
@Testcontainers
@ActiveProfiles(value = "test")
public class IntegrationTest {

    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "admin";
    private static final String DB_NAME = "book_author";
    private static final String TITLE = "test title";
    private static final String NAME = "test name";
    private static final List<String> NAMES = Arrays.asList("test name1", "test name2");
    private static final List<String> TITLES = Arrays.asList("test title1", "test title2");

    private static final String AUTHOR_KING = "King";
    private static final String AUTHOR_BEKET = "Beket";
    private static final String AUTHOR_PULLMAN = "Pullman";
    private static final String BOOK_IT = "It";
    private static final String BOOK_BLIND_ZONE = "Blind Zone";
    private static final String BOOK_AMBER = "Amber";
    private static final String BOOK_GOLDEN_COMPASS = "Golden Compass";

    @Autowired
    private Query query;
    @Autowired
    private Mutation mutation;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName(DB_NAME)
            .withUsername(DB_USERNAME)
            .withPassword(DB_PASSWORD);

    @Test
    @Transactional
    public void getAllBooks() {
        createBooksWithAuthors();
        List<Book> books = query.getAllBooks();
        assertEquals(4, books.size());
    }

    @Test
    @Transactional
    public void getAuthor() {
        createBooksWithAuthors();
        Author author = query.getAuthor(AUTHOR_KING);
        assertEquals(AUTHOR_KING, author.getName());
        assertEquals(2, author.getBooks().size());
        assertEquals(BOOK_IT, author.getBooks().get(0).getTitle());
        assertEquals(BOOK_BLIND_ZONE, author.getBooks().get(1).getTitle());
    }

    @Test
    @Transactional
    public void getBooksByAuthor() {
        createBooksWithAuthors();
        List<Book> books = query.getBooksByAuthor(AUTHOR_KING);
        assertEquals(2, books.size());
        assertEquals(BOOK_IT, books.get(0).getTitle());
        assertEquals(BOOK_BLIND_ZONE, books.get(1).getTitle());

    }

    @Test
    @Transactional
    public void saveBooks() {
        mutation.saveBook(TITLE, NAMES);
        assertEquals(1, bookRepository.findAll().size());
        assertEquals(TITLE, bookRepository.findAll().get(0).getTitle());
        assertEquals(NAMES.get(0), bookRepository.findAll().get(0).getAuthors().get(0).getName());
        assertEquals(NAMES.get(1), bookRepository.findAll().get(0).getAuthors().get(1).getName());
    }

    @Test
    @Transactional
    public void saveAuthor() {
        mutation.saveAuthor(NAME, TITLES);
        assertEquals(1, authorRepository.findAll().size());
        assertEquals(NAME, authorRepository.findAll().get(0).getName());
        assertEquals(TITLES.get(0), authorRepository.findAll().get(0).getBooks().get(0).getTitle());
        assertEquals(TITLES.get(1), authorRepository.findAll().get(0).getBooks().get(1).getTitle());
    }


    private void createBooksWithAuthors() {
        Book book1 = new Book().setTitle(BOOK_IT);
        Book book2 = new Book().setTitle(BOOK_AMBER);
        Book book3 = new Book().setTitle(BOOK_GOLDEN_COMPASS);
        Book book4 = new Book().setTitle(BOOK_BLIND_ZONE);
        Author author1 = new Author().setName(AUTHOR_KING);
        Author author2 = new Author().setName(AUTHOR_BEKET);
        Author author3 = new Author().setName(AUTHOR_PULLMAN);
        mutation.saveAuthor(author1.getName(), Arrays.asList(book1.getTitle(), book4.getTitle()));
        mutation.saveAuthor(author2.getName(), Arrays.asList(book2.getTitle()));
        mutation.saveAuthor(author3.getName(), Arrays.asList(book3.getTitle()));
    }


}
