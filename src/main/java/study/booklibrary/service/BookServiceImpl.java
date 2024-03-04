package study.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.booklibrary.configuration.property.CacheNames;
import study.booklibrary.model.Book;
import study.booklibrary.model.BookFilter;
import study.booklibrary.model.CategoryBook;
import study.booklibrary.repositiory.BookCategoryRepository;
import study.booklibrary.repositiory.BookRepository;
import study.booklibrary.repositiory.SpecificationBook;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheManager = "redisCacheManger")
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final BookCategoryRepository bookCategoryRepository;

    @Override
    @Cacheable(cacheNames = CacheNames.BOOK_BY_NAME_OR_AUTHOR, key = "#filter.name + #filter.author")
    public Book findByNameOrAuthor(BookFilter filter) {
        return bookRepository.findOne(SpecificationBook.byFilter(filter))
                .orElseThrow();
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    @Cacheable(cacheNames = CacheNames.BOOKS_BY_CATEGORIES, key = "#categoryName")
    public List<Book> findAllByCategory(String categoryName) {
        CategoryBook categoryBook = bookCategoryRepository.findByName(categoryName).orElseThrow();
        return bookRepository.findAllByCategory(categoryBook);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheNames.BOOKS_BY_CATEGORIES, key = "#book.category.name", beforeInvocation = true),
            @CacheEvict(cacheNames = CacheNames.BOOK_BY_NAME_OR_AUTHOR, key = "#book.name + #book.author", beforeInvocation = true)
    })
    public Book create(Book book) {
        CategoryBook category = getActualCategory(book.getCategory());
        book.setCategory(category);

        return bookRepository.save(book);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheNames.BOOKS_BY_CATEGORIES, key = "#book.category.name", beforeInvocation = true),
            @CacheEvict(cacheNames = CacheNames.BOOK_BY_NAME_OR_AUTHOR, key = "#book.name + #book.author", beforeInvocation = true)
    })
    @Transactional
    public Book update(Book book) {
        Book bookFromDB = findById(book.getId());
        CategoryBook category = getActualCategory(book.getCategory());

        bookFromDB.setName(book.getName());
        bookFromDB.setAuthor(book.getAuthor());
        bookFromDB.setCategory(category);

        return bookRepository.save(bookFromDB);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheNames.BOOKS_BY_CATEGORIES, key = "#book.category.name", beforeInvocation = true),
            @CacheEvict(cacheNames = CacheNames.BOOK_BY_NAME_OR_AUTHOR, key = "#book.name + #book.author", beforeInvocation = true)
    })
    public void delete(Book book) {
        bookRepository.deleteById(book.getId());
    }

    private CategoryBook getActualCategory(CategoryBook category) {
        return bookCategoryRepository
                .findByName(category.getName()).orElseGet(() -> bookCategoryRepository.save(category));
    }
}
