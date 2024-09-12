package online.library.book.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import online.library.book.models.Copy;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {
 Optional<Copy> findByCopyCode(String copyCode);
}
