package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.CodeCategories;
import LogITBackend.LogIT.domain.Codes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Codes, Long> {

}
