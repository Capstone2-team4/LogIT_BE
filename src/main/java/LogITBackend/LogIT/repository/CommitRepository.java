package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.Commit;
import LogITBackend.LogIT.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommitRepository extends JpaRepository<Commit, String> {
    @Query("SELECT MAX(c.date) FROM Commit c WHERE c.repo.id = :repoId")
    Optional<LocalDateTime> findLatestCommitDateByUserId(Long repoId);

    @Query("SELECT c FROM Commit c WHERE c.repo.id = :repoId")
    List<Commit> findAllByRepoId(Long repoId);
}
