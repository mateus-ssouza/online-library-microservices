package online.library.account.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import online.library.account.enums.UserRole;
import online.library.account.exceptions.*;
import online.library.account.models.User;
import online.library.account.repositories.UserRepository;
import online.library.account.services.UserService;
import online.library.account.utils.Strings;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository _userRepository;

    @Override
    public List<User> getAll() {
        try {
        return _userRepository.findByRole(UserRole.USER);
    } catch (Exception e) {
        throw new RepositoryException(Strings.USER.ERROR_FIND_ALL_LIST, e);
    }
    }

    @Override
    public Optional<User> getById(Long id) {
        try {
            Optional<User> user = _userRepository.findById(id);

            if (!user.isPresent() || user.get().getRole() != UserRole.USER) {
                throw new NotFoundException(Strings.USER.NOT_FOUND);
            }

            return user;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.USER.ERROR_FIND_BY_ID, e);
        }
    }

    @Override
    public User create(User user) {
        try {
            Optional<User> verificarEmailUsuario = _userRepository.findByEmail(user.getEmail());
            Optional<User> verificarCpfUsuario = _userRepository.findByCpf(user.getCpf());
            UserDetails verificarLoginUsuario = _userRepository.findByLogin(user.getLogin());

            // Check if there is already a user with the same email, CPF or login
            if (verificarEmailUsuario.isPresent() || verificarCpfUsuario.isPresent()
                    || verificarLoginUsuario != null)
                throw new ConflictException(Strings.USER.CONFLICT);

            // Encrypt user password
            String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());

            user.setPassword(encryptedPassword);

            user.setRole(UserRole.USER);

            return _userRepository.save(user);
        } catch (ConflictException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.USER.ERROR_CREATE, e);
        }
    }

    @Override
    public User update(Long id, User user) {
        try {
            Optional<User> userModel = _userRepository.findById(id);

            // Check if a user exists by id
            if (!userModel.isPresent() || userModel.get().getRole() != UserRole.USER)
                throw new NotFoundException(Strings.USER.NOT_FOUND);

            Optional<User> verificarEmailUsuario = _userRepository.findByEmail(user.getEmail());
            Optional<User> verificarCpfUsuario = _userRepository.findByCpf(user.getCpf());
            UserDetails verificarLoginUsuario = _userRepository.findByLogin(user.getLogin());
            Optional<Long> idUsuario = _userRepository.findIdByLogin(user.getLogin());

            // Check if there is already a user with the same email, CPF or login
            // And if it is the same user who wants to change these fields
            if ((verificarEmailUsuario.isPresent()
                    && !verificarEmailUsuario.get().getId().equals(userModel.get().getId())) ||
                    (verificarCpfUsuario.isPresent()
                            && !verificarCpfUsuario.get().getId().equals(userModel.get().getId()))
                    ||
                    (verificarLoginUsuario != null && !idUsuario.get().equals(userModel.get().getId())))
                throw new ConflictException(Strings.USER.CONFLICT);

            // Updating user fields
            User userAtualizado = userModel.get();
            userAtualizado.setName(user.getName());
            userAtualizado.setCpf(user.getCpf());
            userAtualizado.setPhone(user.getPhone());
            userAtualizado.setBirthDate(user.getBirthDate());
            userAtualizado.setEmail(user.getEmail());
            userAtualizado.setLogin(user.getLogin());

            return _userRepository.save(userAtualizado);
        } catch (NotFoundException e) {
            throw e;
        } catch (ConflictException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.USER.ERROR_UPDATE, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Optional<User> user = _userRepository.findById(id);
            // Check if there is a user by id and if there is a role USER
            if (user.isPresent() && user.get().getRole() == UserRole.USER) {
                // Removing user by their id
                _userRepository.deleteById(id);
            } else {
                throw new NotFoundException(Strings.USER.NOT_FOUND);
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.USER.ERROR_DELETE, e);
        }
    }
}
