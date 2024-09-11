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
import online.library.account.services.AdminService;
import online.library.account.utils.Strings;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository _userRepository;

    @Override
    public List<User> getAll() {
        try {
            return _userRepository.findByRole(UserRole.ADMIN);
        } catch (Exception e) {
            throw new RepositoryException(Strings.ADMIN.ERROR_FIND_ALL_LIST, e);
        }
    }

    @Override
    public Optional<User> getById(Long id) {
        try {
            Optional<User> admin = _userRepository.findById(id);

            if (!admin.isPresent() || admin.get().getRole() != UserRole.ADMIN) {
                throw new NotFoundException(Strings.ADMIN.NOT_FOUND);
            }

            return admin;
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.ADMIN.ERROR_FIND_BY_ID, e);
        }
    }

    @Override
    public User create(User admin) {
        try {
            Optional<User> verificarEmailUsuario = _userRepository.findByEmail(admin.getEmail());
            Optional<User> verificarCpfUsuario = _userRepository.findByCpf(admin.getCpf());
            UserDetails verificarLoginUsuario = _userRepository.findByLogin(admin.getLogin());

            // Check if there is already a user with the same email, CPF or login
            if (verificarEmailUsuario.isPresent() || verificarCpfUsuario.isPresent()
                    || verificarLoginUsuario != null)
                throw new ConflictException(Strings.ADMIN.CONFLICT);

            // Encrypt user password
            String encryptedPassword = new BCryptPasswordEncoder().encode(admin.getPassword());

            admin.setPassword(encryptedPassword);

            admin.setRole(UserRole.ADMIN);

            return _userRepository.save(admin);
        } catch (ConflictException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.ADMIN.ERROR_CREATE, e);
        }
    }

    @Override
    public User update(Long id, User admin) {
        try {
            Optional<User> adminModel = _userRepository.findById(id);

            // Check if there is an admin by id
            if (!adminModel.isPresent() || adminModel.get().getRole() != UserRole.ADMIN)
                throw new NotFoundException(Strings.ADMIN.NOT_FOUND);

            Optional<User> verificarEmailUsuario = _userRepository.findByEmail(admin.getEmail());
            Optional<User> verificarCpfUsuario = _userRepository.findByCpf(admin.getCpf());
            UserDetails verificarLoginUsuario = _userRepository.findByLogin(admin.getLogin());
            Optional<Long> idUsuario = _userRepository.findIdByLogin(admin.getLogin());

            // Check if there is already a user with the same email, CPF or login
            // And if it is the same admin who wants to change these fields
            if ((verificarEmailUsuario.isPresent()
                    && !verificarEmailUsuario.get().getId().equals(adminModel.get().getId())) ||
                    (verificarCpfUsuario.isPresent()
                            && !verificarCpfUsuario.get().getId().equals(adminModel.get().getId()))
                    ||
                    (verificarLoginUsuario != null && !idUsuario.get().equals(adminModel.get().getId())))
                throw new ConflictException(Strings.ADMIN.CONFLICT);

            // Updating admin fields
            User adminAtualizado = adminModel.get();
            adminAtualizado.setName(admin.getName());
            adminAtualizado.setCpf(admin.getCpf());
            adminAtualizado.setPhone(admin.getPhone());
            adminAtualizado.setBirthDate(admin.getBirthDate());
            adminAtualizado.setEmail(admin.getEmail());
            adminAtualizado.setLogin(admin.getLogin());

            return _userRepository.save(adminAtualizado);
        } catch (NotFoundException e) {
            throw e;
        } catch (ConflictException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.ADMIN.ERROR_UPDATE, e);
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Optional<User> admin = _userRepository.findById(id);
            // Check if there is a admin by id and if there is a role ADMIN
            if (admin.isPresent() && admin.get().getRole() == UserRole.ADMIN) {
                // Removing admin by your id
                _userRepository.deleteById(id);
            } else {
                throw new NotFoundException(Strings.ADMIN.NOT_FOUND);
            }
        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RepositoryException(Strings.ADMIN.ERROR_DELETE, e);
        }
    }
}
