package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.CategoryRequestDTO;
import LogITBackend.LogIT.DTO.CategoryResponseDTO;
import LogITBackend.LogIT.apiPayload.code.status.ErrorStatus;
import LogITBackend.LogIT.apiPayload.exception.GeneralException;
import LogITBackend.LogIT.domain.CodeCategories;
import LogITBackend.LogIT.domain.Users;
import LogITBackend.LogIT.repository.CategoryRepository;

import LogITBackend.LogIT.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public List<CategoryResponseDTO> getCategories() {
        List<CodeCategories> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new GeneralException(ErrorStatus.CATEGORY_NOT_FOUND);
        }
        return categories.stream()
                .map(category -> CategoryResponseDTO.builder()
                        .category(category.getName())
                        .build())
                .toList();
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO request) {
        Users dummyUser = userRepository.findById(1L)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        System.out.println("dummyUser = " + dummyUser.getName());
        CodeCategories category = request.ToEntity(dummyUser);
        System.out.println("category.getName() = " + category.getName());
        return CategoryResponseDTO.ToDTO(categoryRepository.save(category));
    }
}
