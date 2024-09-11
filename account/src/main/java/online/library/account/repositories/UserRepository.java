package online.library.account.repositories;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import online.library.account.enums.UserRole;
import online.library.account.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);

    Optional<User> findByEmail(String email);

    Optional<User> findByCpf(String cpf);

    @Query("SELECT u.id FROM User u WHERE u.login = :login")
    Optional<Long> findIdByLogin(@Param("login") String login);

    List<User> findByRole(UserRole role);
}
