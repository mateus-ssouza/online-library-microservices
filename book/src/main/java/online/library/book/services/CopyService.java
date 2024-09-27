package online.library.book.services;

import java.util.List;
import java.util.Optional;

import online.library.book.models.Copy;

public interface CopyService {
    public List<Copy> getAll();

    public List<Copy> getCopiesByBookId(Long bookId);

    public Optional<Copy> getById(Long id);

    public Copy create(String bookTitle);

    public void delete(Long id);
}
