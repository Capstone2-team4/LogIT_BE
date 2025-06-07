package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorCodeRepository extends JpaRepository<ErrorCode, Long> {
}
