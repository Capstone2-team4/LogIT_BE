package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.Codes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CodesRepository extends JpaRepository<Codes, String> {

    List<Codes> getAllByCommitId(String commitId);
}
