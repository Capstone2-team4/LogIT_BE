package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("SELECT o FROM Owner o WHERE o.user.id = :userId AND o.ownerName = :ownerName")
    Optional<Owner>  findByUserIdAndOwnerName(@Param("userId") Long userId, @Param("ownerName") String ownerName);


    List<Owner> findAllByUserId(Long userId);
}
