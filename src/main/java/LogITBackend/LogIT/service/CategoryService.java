package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.CategoryRequestDTO;
import LogITBackend.LogIT.DTO.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDTO> getCategories();

    CategoryResponseDTO createCategory(CategoryRequestDTO request);
}
