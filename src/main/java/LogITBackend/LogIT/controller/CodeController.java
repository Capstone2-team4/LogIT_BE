package LogITBackend.LogIT.controller;

import LogITBackend.LogIT.DTO.CategoryResponseDTO;
import LogITBackend.LogIT.DTO.CodeRequestDTO;
import LogITBackend.LogIT.DTO.CodeResponseDTO;
import LogITBackend.LogIT.apiPayload.ApiResponse;
import LogITBackend.LogIT.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/codes")
@RequiredArgsConstructor
public class CodeController {

    private final CodeService codeService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<CodeResponseDTO>> addCode(@RequestBody CodeRequestDTO request) {
        CodeResponseDTO codeResponseDTO = codeService.addCode(request);
        return ResponseEntity.ok(ApiResponse.onSuccess(codeResponseDTO));
    }

}
