package LogITBackend.LogIT.controller;

import LogITBackend.LogIT.DTO.CategoryRequestDTO;
import LogITBackend.LogIT.DTO.CategoryResponseDTO;
import LogITBackend.LogIT.apiPayload.ApiResponse;
import LogITBackend.LogIT.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("")
    public ResponseEntity<ApiResponse<List<String>>> getCategories() {
        List<String> categories = categoryService.getCategories();
        return ResponseEntity.ok(ApiResponse.onSuccess(categories));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> createCategory(@RequestBody CategoryRequestDTO request) {
        CategoryResponseDTO category = categoryService.createCategory(request);
        return ResponseEntity.ok(ApiResponse.onSuccess(category));
    }
}
