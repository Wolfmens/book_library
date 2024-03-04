package study.booklibrary.repositiory;

import org.springframework.data.jpa.domain.Specification;
import study.booklibrary.model.Book;
import study.booklibrary.model.BookFilter;

public interface SpecificationBook {

    static Specification<Book> byFilter(BookFilter filter) {
        return Specification
                .where(findingByAuthor(filter))
                .and(findingByName(filter));
    }

    static Specification<Book> findingByName(BookFilter filter) {
        return ((root, query, criteriaBuilder) -> {
            if (filter.getName() == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("name"), filter.getName());
        });

    }

    static Specification<Book> findingByAuthor(BookFilter filter) {
        return ((root, query, criteriaBuilder) -> {
            if (filter.getAuthor() == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("author"), filter.getAuthor());
        });
    }
}
