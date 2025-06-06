package LogITBackend.LogIT.controller;

import LogITBackend.LogIT.DTO.CategoryRequestDTO;
import LogITBackend.LogIT.DTO.CategoryResponseDTO;
import LogITBackend.LogIT.apiPayload.ApiResponse;
import LogITBackend.LogIT.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @Operation(summary = "플러그인 - 유저의 카테고리 조회", description =
            "# 플러그인 관련 API입니다."
    )
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<String>>> getCategories() {
        List<String> categories = categoryService.getCategories();
        return ResponseEntity.ok(ApiResponse.onSuccess(categories));
    }

    @Operation(summary = "플러그인 - 유저의 카테고리 생성", description =
            "# 플러그인 관련 API입니다."
    )
    @PostMapping("")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> createCategory(@RequestBody CategoryRequestDTO request) {
        CategoryResponseDTO category = categoryService.createCategory(request);
        return ResponseEntity.ok(ApiResponse.onSuccess(category));
    }
}
