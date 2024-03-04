package study.booklibrary.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.booklibrary.model.*;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper {

    public Book requestToBook(UpsertBookRequest request) {
        Book book = new Book();
        CategoryBook categoryBook = new CategoryBook();
        categoryBook.setName(request.getCategory());
        book.setAuthor(request.getAuthor());
        book.setName(request.getName());
        book.setCategory(categoryBook);
        return book;
    }

    public Book requestToBook(Long id, UpsertBookRequest request) {
        Book book = requestToBook(request);
        book.setId(id);

        return book;
    }

    public BookResponse bookToBookResponse(Book book) {
        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(book.getId());
        bookResponse.setAuthor(book.getAuthor());
        bookResponse.setName(book.getName());
        bookResponse.setCategory(book.getCategory());

        return bookResponse;
    }


    public BookResponseList bookListToBookResponseList(List<Book> allByCategory) {
        BookResponseList bookResponseList = new BookResponseList();
        bookResponseList
                .setBookResponseList(allByCategory.stream()
                        .map(this::bookToBookResponse)
                        .collect(Collectors.toList()));


        return bookResponseList;
    }
}
