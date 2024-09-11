package online.library.account.services;

import java.util.List;
import java.util.Optional;

import online.library.account.models.User;

public interface UserService {
    public List<User> getAll();

    public Optional<User> getById(Long id);

    public User create(User user);

    public User update(Long id, User user);

    public void delete(Long id);
}
