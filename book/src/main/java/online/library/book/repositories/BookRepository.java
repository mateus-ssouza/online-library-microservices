package online.library.book.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import online.library.book.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
}
