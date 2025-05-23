package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.CodeCategories;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CodeCategories, Long> {
    List<CodeCategories> findAllByUsersId(@NotNull Long userId);
    CodeCategories save(CodeCategories category);
    Optional<CodeCategories> findByUsersIdAndName(Long userId, String name);
}
