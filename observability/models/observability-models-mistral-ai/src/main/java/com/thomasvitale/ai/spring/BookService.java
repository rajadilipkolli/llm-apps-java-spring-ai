package com.thomasvitale.ai.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);
    private static final Map<Integer,Book> books = new ConcurrentHashMap<>();

    static {
        books.put(1, new Book("His Dark Materials", "Philip Pullman"));
        books.put(2, new Book("Narnia", "C.S. Lewis"));
        books.put(3, new Book("The Hobbit", "J.R.R. Tolkien"));
        books.put(4, new Book("The Lord of The Rings", "J.R.R. Tolkien"));
        books.put(5, new Book("The Silmarillion", "J.R.R. Tolkien"));
    }

    List<Book> getBooksByAuthor(Author author) {
        return books.values().stream()
                .filter(book -> author.name().equals(book.author()))
                .toList();
    }

    Book getBestsellerByAuthor(Author author) {
        logger.info("Getting bestseller for author {}", author.name());
        return switch (author.name()) {
            case "J.R.R. Tolkien" -> books.get(4);
            case "C.S. Lewis" -> books.get(2);
            case "Philip Pullman" -> books.get(1);
            default -> null;
        };
    }

    public record Book(String title, String author) {}
    public record Author(String name) {}

}
