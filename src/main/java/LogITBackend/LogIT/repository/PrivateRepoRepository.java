package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.PrivateRepo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivateRepoRepository extends JpaRepository<PrivateRepo, Long> {

    List<PrivateRepo> findAllByUserId(Long userId);
}
