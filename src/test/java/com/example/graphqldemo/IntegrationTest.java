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
import org.springframework.test.context.TestPropertySource;
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
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql://localhost:5432/book_author"
})
public class IntegrationTest {

    private static final String TITLE = "test title";
    private static final String NAME = "test name";
    private static final List<String> NAMES = Arrays.asList("test name1", "test name2");
    private static final List<String> TITLES = Arrays.asList("test title1", "test title2");

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
            .withDatabaseName("book_author")
            .withUsername("postgres")
            .withPassword("admin");

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
        Author author = query.getAuthor("King");
        assertEquals("King", author.getName());
        assertEquals(2, author.getBooks().size());
        assertEquals("It", author.getBooks().get(0).getTitle());
        assertEquals("Blind Zone", author.getBooks().get(1).getTitle());
    }

    @Test
    @Transactional
    public void getBooksByAuthor() {
        createBooksWithAuthors();
        Long id = authorRepository.findByName("King").get().getId();
        List<Book> books = query.getBooksByAuthor(id);
        assertEquals(2, books.size());
        assertEquals("It", books.get(0).getTitle());
        assertEquals("Blind Zone", books.get(1).getTitle());

    }

    @Test
    @Transactional
    public void saveBooks() {
        Book book = mutation.saveBook(TITLE, NAMES);
        assertEquals(1, bookRepository.findAll().size());
        assertEquals(TITLE, bookRepository.findAll().get(0).getTitle());
        assertEquals(NAMES.get(0), bookRepository.findAll().get(0).getAuthors().get(0).getName());
        assertEquals(NAMES.get(1), bookRepository.findAll().get(0).getAuthors().get(1).getName());
    }

    @Test
    @Transactional
    public void saveAuthor() {
        Author author = mutation.saveAuthor(NAME, TITLES);
        assertEquals(1, authorRepository.findAll().size());
        assertEquals(NAME, authorRepository.findAll().get(0).getName());
        assertEquals(TITLES.get(0), authorRepository.findAll().get(0).getBooks().get(0).getTitle());
        assertEquals(TITLES.get(1), authorRepository.findAll().get(0).getBooks().get(1).getTitle());
    }


    private void createBooksWithAuthors() {
        Book book1 = new Book().setTitle("It");
        Book book2 = new Book().setTitle("Amber");
        Book book3 = new Book().setTitle("Golden Compass");
        Book book4 = new Book().setTitle("Blind Zone");
        Author author1 = new Author().setName("King");
        Author author2 = new Author().setName("Beket");
        Author author3 = new Author().setName("Pullman");
        mutation.saveAuthor(author1.getName(), Arrays.asList(book1.getTitle(), book4.getTitle()));
        mutation.saveAuthor(author2.getName(), Arrays.asList(book2.getTitle()));
        mutation.saveAuthor(author3.getName(), Arrays.asList(book3.getTitle()));
    }


}
