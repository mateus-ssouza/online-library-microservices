package online.library.book.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import online.library.book.models.Copy;

@Repository
public interface CopyRepository extends JpaRepository<Copy, Long> {

}
