package com.example.graphqldemo.resolver;

import com.example.graphqldemo.model.Author;
import com.example.graphqldemo.model.Book;
import com.example.graphqldemo.repository.AuthorRepository;
import com.example.graphqldemo.repository.BookRepository;
import com.example.graphqldemo.service.MutationService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mutation.
 *
 * @author Rustam Bikiteev
 */
@Component
@RequiredArgsConstructor
public class Mutation implements GraphQLMutationResolver {
    private final MutationService service;

    public Author saveAuthor(String name, List<String> bookTitles) {
        return service.saveAuthor(name, bookTitles);
    }

    public Book saveBook(String title, List<String> authorNames) {
        return service.saveBook(title, authorNames);
    }
}
