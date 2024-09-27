package online.library.book.services;

import java.util.List;
import java.util.Optional;

import online.library.book.models.Book;

public interface BookService {
    public List<Book> getAll();

    public Optional<Book> getById(Long id);

    public Book create(Book book);

    public Book update(Long id, Book book);

    public void delete(Long id);
}
