package online.library.book.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.library.book.exceptions.ConflictException;
import online.library.book.exceptions.NotFoundException;
import online.library.book.exceptions.RepositoryException;
import online.library.book.models.Book;
import online.library.book.repositories.BookRepository;
import online.library.book.services.BookService;
import online.library.book.utils.Strings;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository _bookRepository;

    @Override
    public List<Book> getAll() {
        try {
            return _bookRepository.findAll();
        } catch (Exception e) {
            throw new RepositoryException(Strings.BOOK.ERROR_FIND_ALL_LIST, e);
        }
    }

    @Override
    public Optional<Book> getById(Long id) {
        try {
            Optional<Book> book = _bookRepository.findById(id);

            if (!book.isPresent()) {
                throw new NotFoundException(Strings.BOOK.NOT_FOUND);
            }

            return book;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.BOOK.ERROR_FIND_BY_ID, e);
        }
    }

    @Override
    public Book create(Book book) {
        try {
            Optional<Book> checkBookTitle = _bookRepository.findByTitle(book.getTitle());
            Optional<Book> checkBookIsbn = _bookRepository.findByIsbn(book.getIsbn());

            // Check if there is already a book with the same title or isbn
            if (checkBookTitle.isPresent() || checkBookIsbn.isPresent())
                throw new ConflictException(Strings.BOOK.CONFLICT);

            return _bookRepository.save(book);
        } catch (ConflictException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.BOOK.ERROR_CREATE, e);
        }
    }

    @Override
    public Book update(Long id, Book book) {
        try {
            Optional<Book> bookModel = _bookRepository.findById(id);

            // Check if a book exists by id
            if (!bookModel.isPresent())
                throw new NotFoundException(Strings.BOOK.NOT_FOUND);

            Optional<Book> checkBookTitle = _bookRepository.findByTitle(book.getTitle());
            Optional<Book> checkBookIsbn = _bookRepository.findByIsbn(book.getIsbn());

            // Check if there is already a book with the same title or isbn
            if ((checkBookTitle.isPresent() && !checkBookTitle.get().getId().equals(bookModel.get().getId())) ||
                    (checkBookIsbn.isPresent()&& !checkBookIsbn.get().getId().equals(bookModel.get().getId())))
                throw new ConflictException(Strings.BOOK.CONFLICT);

            // Updating book fields
            Book bookUpdated = bookModel.get();
            bookUpdated.setTitle(book.getTitle());
            bookUpdated.setAuthor(book.getAuthor());
            bookUpdated.setIsbn(book.getIsbn());
            bookUpdated.setCategory(book.getCategory());

            return _bookRepository.save(bookUpdated);
        } catch (NotFoundException e) {
            throw e;
        } catch (ConflictException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.BOOK.ERROR_UPDATE, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (_bookRepository.existsById(id)) {
                // Removing book by their id
                _bookRepository.deleteById(id);
            } else {
                throw new NotFoundException(Strings.BOOK.NOT_FOUND);
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.BOOK.ERROR_DELETE, e);
        }
    }
}
