package online.library.book.services;

import java.util.List;
import java.util.Optional;

import online.library.book.models.Copy;

public interface CopyService {
    public List<Copy> getAll();

    public Optional<Copy> getById(Long id);

    public Copy create(Copy copy);

    public Copy update(Long id, Copy copy);

    public void delete(Long id);
}
