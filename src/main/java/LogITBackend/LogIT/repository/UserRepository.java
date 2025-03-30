package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    @Override
    Optional<Users> findById(Long id);
}
