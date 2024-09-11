package online.library.account.services;

import java.util.List;
import java.util.Optional;

import online.library.account.models.User;

public interface AdminService {
    public List<User> getAll();

    public Optional<User> getById(Long id);

    public User create(User admin);

    public User update(Long id, User admin);

    public void delete(Long id);
}
