package study.booklibrary.service;

import study.booklibrary.model.Book;
import study.booklibrary.model.BookFilter;

import java.util.List;

public interface BookService {

    Book findByNameOrAuthor(BookFilter filter);

    List<Book> findAllByCategory(String category);

    Book create(Book book);

    Book update(Book book);

    void delete(Book book);

    Book findById(Long id);

}
