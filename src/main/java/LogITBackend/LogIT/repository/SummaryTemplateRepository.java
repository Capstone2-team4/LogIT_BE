package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.SummaryTemplate;
import LogITBackend.LogIT.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SummaryTemplateRepository extends JpaRepository<SummaryTemplate, Long> {
    void deleteByUsers(Users users);
    SummaryTemplate findByUsers(Users getUser);
}
