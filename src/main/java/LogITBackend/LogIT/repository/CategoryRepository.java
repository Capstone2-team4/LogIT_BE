package LogITBackend.LogIT.repository;

import LogITBackend.LogIT.domain.CodeCategories;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CodeCategories, Integer> {

    List<CodeCategories> findAll();
    CodeCategories save(CodeCategories category);
}
