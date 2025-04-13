package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.Owner;
import LogITBackend.LogIT.domain.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RepoRepository extends JpaRepository<Repo, Long> {
    Optional<Repo> findByOwnerIdAndRepoName(Long ownerId, String repoName);

    List<Repo> findAllByOwnerId(Long ownerId);

    @Query("SELECT MAX(r.createdAt) FROM Repo r WHERE r.owner.id = :ownerId")
    Optional<LocalDateTime> findLatestRepoCreatedAtByOwnerId(@Param("ownerId") Long ownerId);
}
