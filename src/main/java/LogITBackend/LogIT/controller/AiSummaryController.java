package LogITBackend.LogIT.controller;


import LogITBackend.LogIT.DTO.Chatgpt.AiSummaryRequestDTO;
import LogITBackend.LogIT.DTO.Chatgpt.AiSummaryResponseDTO;
import LogITBackend.LogIT.apiPayload.ApiResponse;
import LogITBackend.LogIT.service.AiSummaryCommandService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/summary")
public class AiSummaryController {

    private final AiSummaryCommandService aiSummaryCommandService;

    @Operation(summary = "ai로 선택한 커밋들의 내용(코드 + 코드블럭)을 글로 요약해주는 api", description =
            "# 입력한 commitId의 리스트에 해당하는 commit들의 내용(코드 + 코드블럭)을 요약해주는 api입니다."
    )
    @PostMapping()
    public ApiResponse<AiSummaryResponseDTO.CreateAiSummaryResultDTO> createAiSummaryRecord(
            @RequestParam String owners,
            @RequestParam String repos,
            @RequestBody AiSummaryRequestDTO.CreateAiSummaryRequest request
    ) {
        return ApiResponse.onSuccess(
                aiSummaryCommandService.createAiSummary(owners, repos, request)
        );
    }
}
