package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.ErrorCodeBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErrorCodeBlockRepository extends JpaRepository<ErrorCodeBlock, Long> {

}
