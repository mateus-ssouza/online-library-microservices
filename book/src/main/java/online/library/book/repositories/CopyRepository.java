package online.library.book.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import online.library.book.models.Copy;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {
    Optional<Copy> findByCopyCode(String copyCode);
    
    @Query("SELECT c FROM Copy c WHERE c.book.id = :bookId")
    List<Copy> findByBookId(@Param("bookId") Long bookId);
}
