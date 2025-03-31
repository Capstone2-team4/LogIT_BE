package LogITBackend.LogIT.DTO;

import LogITBackend.LogIT.domain.CodeCategories;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryResponseDTO {
    private String category;

    public static CategoryResponseDTO ToDTO(CodeCategories category) {
        return CategoryResponseDTO.builder()
                .category(category.getName())
                .build();
    }
}
