package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.ErrorCode;
import LogITBackend.LogIT.domain.ErrorInfo;
import LogITBackend.LogIT.domain.ErrorSolvedCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorCodeRepository extends JpaRepository<ErrorCode, Long> {
    List<ErrorCode> findAllByErrorInfo(ErrorInfo errorInfo);

    List<ErrorCode> findAllByErrorInfoId(Long errorInfoId);
}
