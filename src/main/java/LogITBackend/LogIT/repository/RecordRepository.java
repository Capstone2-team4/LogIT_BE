package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.Records;
import LogITBackend.LogIT.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Records, Long> {
    List<Records> findAllByUsersOrderByCreatedAtDesc(Users user);
}
