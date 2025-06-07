package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.ErrorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorInfoRepository extends JpaRepository<ErrorInfo, Long> {

}
