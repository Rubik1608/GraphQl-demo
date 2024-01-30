package com.example.graphqldemo.resolver;

import com.example.graphqldemo.model.Author;
import com.example.graphqldemo.model.Book;
import com.example.graphqldemo.service.QueryService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Query.
 *
 * @author Rustam Bikiteev
 */
@Component
@RequiredArgsConstructor
public class Query implements GraphQLQueryResolver {
    private final QueryService service;

    GraphQLScalarType longScalar = ExtendedScalars.newAliasedScalar("Long")
            .aliasedScalar(ExtendedScalars.GraphQLLong)
            .build();

    public Author getAuthor(String name){
        return service.getAuthor(name);
    }

    public List<Book> getBooksByAuthor(String authorName){
        return service.getBooksByAuthor(authorName);
    }

    public List<Book> getAllBooks(){
        return service.getAllBooks();
    }
}
