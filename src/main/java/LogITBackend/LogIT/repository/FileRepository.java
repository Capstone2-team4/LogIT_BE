package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    @Query("SELECT f FROM File f WHERE f.commit.id = :commitId")
    List<File> findAllByCommitId(@Param("commitId") String commitId);

}
