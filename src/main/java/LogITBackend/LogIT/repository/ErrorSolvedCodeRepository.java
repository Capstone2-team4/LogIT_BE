package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.ErrorSolvedCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorSolvedCodeRepository extends JpaRepository<ErrorSolvedCode, Long> {
}
