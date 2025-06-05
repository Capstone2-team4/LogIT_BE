package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.Codes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodesRepository extends JpaRepository<Codes, Long> {

}
