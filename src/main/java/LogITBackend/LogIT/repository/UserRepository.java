package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    @Override
    Optional<Users> findById(Long id);
    Optional<Users> findByUsername(String username);
    Optional<Users> findByProviderId(String providerId);
}
