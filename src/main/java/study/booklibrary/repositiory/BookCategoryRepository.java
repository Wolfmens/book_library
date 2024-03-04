package study.booklibrary.repositiory;

import org.springframework.data.jpa.repository.JpaRepository;
import study.booklibrary.model.CategoryBook;

import java.util.Optional;

public interface BookCategoryRepository extends JpaRepository<CategoryBook, Long> {

    Optional<CategoryBook> findByName(String name);

}
