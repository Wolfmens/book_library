package study.booklibrary.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.booklibrary.mapper.BookMapper;
import study.booklibrary.model.BookFilter;
import study.booklibrary.model.BookResponse;
import study.booklibrary.model.BookResponseList;
import study.booklibrary.model.UpsertBookRequest;
import study.booklibrary.service.BookServiceImpl;


@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookServiceImpl bookService;

    private final BookMapper bookMapper;

    @GetMapping("/search")
    public ResponseEntity<BookResponse> findByNameOrAuthor(BookFilter filter) {
        return ResponseEntity.ok(bookMapper.bookToBookResponse(bookService.findByNameOrAuthor(filter)));
    }

    @GetMapping
    public ResponseEntity<BookResponseList> findAllByCategory(@RequestParam String category) {
        return ResponseEntity.ok(bookMapper.bookListToBookResponseList(bookService.findAllByCategory(category)));
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@RequestBody UpsertBookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                bookMapper.bookToBookResponse(bookService.create(bookMapper.requestToBook(request))));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> update(@PathVariable Long id,
                                       @RequestBody UpsertBookRequest request) {
        return ResponseEntity.ok(bookMapper.bookToBookResponse(bookService.update(bookMapper.requestToBook(id,request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.delete(bookService.findById(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
