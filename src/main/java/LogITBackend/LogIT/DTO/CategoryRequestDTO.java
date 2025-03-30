package LogITBackend.LogIT.DTO;

import LogITBackend.LogIT.domain.CodeCategories;
import LogITBackend.LogIT.domain.Users;
import lombok.Getter;

@Getter
public class CategoryRequestDTO {
    private String category;

    public CodeCategories ToEntity(Users user) {
        return CodeCategories.builder()
                .name(category)
                .users(user)
                .build();

    }
}
