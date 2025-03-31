package LogITBackend.LogIT.controller;

import LogITBackend.LogIT.DTO.CategoryRequestDTO;
import LogITBackend.LogIT.DTO.CategoryResponseDTO;
import LogITBackend.LogIT.apiPayload.ApiResponse;
import LogITBackend.LogIT.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("")
    public ApiResponse<List<CategoryResponseDTO>> getCategories() {
        List<CategoryResponseDTO> categories = categoryService.getCategories();
        return ApiResponse.onSuccess(categories);
    }

    @PostMapping("")
    public ApiResponse<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO request) {
        CategoryResponseDTO category = categoryService.createCategory(request);
        return ApiResponse.onSuccess(category);
    }
}
