package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Long> {

    Optional<Branch> findByRepoIdAndName(Long RepoId, String branchName);
}
