package online.library.book.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.library.book.exceptions.NotFoundException;
import online.library.book.exceptions.RepositoryException;
import online.library.book.models.Book;
import online.library.book.models.Copy;
import online.library.book.repositories.BookRepository;
import online.library.book.repositories.CopyRepository;
import online.library.book.services.CopyService;
import online.library.book.utils.CopyCodeGenerator;
import online.library.book.utils.Strings;

@Service
public class CopyServiceImpl implements CopyService {

    @Autowired
    private CopyRepository _copyRepository;

    @Autowired
    private BookRepository _bookRepository;

    @Override
    public List<Copy> getAll() {
        try {
            return _copyRepository.findAll();
        } catch (Exception e) {
            throw new RepositoryException(Strings.COPY.ERROR_FIND_ALL_LIST, e);
        }
    }

    @Override
    public Optional<Copy> getById(Long id) {
        try {
            Optional<Copy> copy = _copyRepository.findById(id);

            if (!copy.isPresent()) {
                throw new NotFoundException(Strings.COPY.NOT_FOUND);
            }

            return copy;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.COPY.ERROR_FIND_BY_ID, e);
        }
    }

    @Override
    public Copy create(String bookTitle) {
        try {
            Optional<Book> checkBook = _bookRepository.findByTitle(bookTitle);

            // Check if there is already a book with the same title
            if (!checkBook.isPresent())
                throw new NotFoundException(Strings.BOOK.NOT_FOUND);

            var copy = new Copy();
            copy.setBook(checkBook.get());
            
            Optional<Copy> checkCopy;

            do {
                var copyCode = CopyCodeGenerator.generateCopyCode();
                copy.setCopyCode(copyCode);
                checkCopy = _copyRepository.findByCopyCode(copy.getCopyCode());
            } while (checkCopy.isPresent());

            return _copyRepository.save(copy);
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.COPY.ERROR_CREATE, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            if (_copyRepository.existsById(id)) {
                // Removing copy by their id
                _copyRepository.deleteById(id);
            } else {
                throw new NotFoundException(Strings.COPY.NOT_FOUND);
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.COPY.ERROR_DELETE, e);
        }
    }

    @Override
    public List<Copy> getCopiesByBookId(Long bookId) {
        try {
            Optional<Book> checkBook = _bookRepository.findById(bookId);

            // Check if there is already a book with the same id
            if (!checkBook.isPresent())
                throw new NotFoundException(Strings.BOOK.NOT_FOUND);

            return _copyRepository.findByBookId(bookId);
        } catch (Exception e) {
            throw new RepositoryException(Strings.COPY.ERROR_FIND_ALL_LIST, e);
        }
    }
}
