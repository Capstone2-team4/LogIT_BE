package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.ErrorInfo;
import LogITBackend.LogIT.domain.ErrorSolvedCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorSolvedCodeRepository extends JpaRepository<ErrorSolvedCode, Long> {
    List<ErrorSolvedCode> findAllByErrorInfo(ErrorInfo errorInfo);
}
