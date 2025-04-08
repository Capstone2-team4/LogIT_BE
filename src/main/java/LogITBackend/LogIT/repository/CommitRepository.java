package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.Commit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitRepository extends JpaRepository<Commit, String> {

}
