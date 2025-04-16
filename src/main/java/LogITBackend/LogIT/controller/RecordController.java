package LogITBackend.LogIT.controller;

import LogITBackend.LogIT.DTO.RecordRequestDTO;
import LogITBackend.LogIT.DTO.RecordResponseDTO;
import LogITBackend.LogIT.apiPayload.ApiResponse;
import LogITBackend.LogIT.converter.RecordConverter;
import LogITBackend.LogIT.domain.Records;
import LogITBackend.LogIT.service.RecordCommandService;
import LogITBackend.LogIT.service.RecordQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/records")
@RequiredArgsConstructor
public class RecordController {
    private final RecordCommandService recordCommandService;
    private final RecordQueryService recordQueryService;

    @Operation(summary = "글 작성",
            description = """
                    글 제목과 글 내용을 작성해주세요.
                    저장된 글의 ID, 글 제목, 글 내용 그리고 생성 날짜가 반환됩니다.
                    """
    )

    @PostMapping(value = "/")
    public ApiResponse<RecordResponseDTO.CreateRecordResultDTO> createRecord(
            @RequestBody RecordRequestDTO.CreateRecordRequestDTO request
    ){
        Records records = recordCommandService.createRecord(request);

        return ApiResponse.onSuccess(
                RecordConverter.toCreateRecordResultDTO(records)
        );
    }

    @Operation(summary = "글 삭제",
            description = """
                    삭제할 글 id를 작성해주세요.
                    해당 글이 삭제됩니다.
                    """
    )
    @DeleteMapping("/delete/{recordId}")
    public ApiResponse<Object> deleteRecord(
            @PathVariable Long recordId
    ) {
        recordCommandService.deleteRecord(recordId);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "글 수정",
            description = """
                    글 id -> Path Variable \n
                    수정할 글의 제목, 내용 -> body 를 작성해주세요.\n
                    ⭐️ 수정 하는 항목만 보내주세요. ⭐️\n
                    """
    )
    @PatchMapping(value = "/edit/{recordId}")
    public ApiResponse<RecordResponseDTO.RecordResultDTO> editRecord(
            @PathVariable Long recordId,
            @RequestBody RecordRequestDTO.EditRecordRequestDTO request
    ) {
        Records records = recordCommandService.editRecord(recordId, request);
        return ApiResponse.onSuccess(
                RecordConverter.toRecordResultDTO(records)
        );
    }

    @Operation(summary = "글 전체 조회",
            description = """
                    글 전체 조회 api입니다.\n
                    """
    )
    @GetMapping("/list")
    public ApiResponse<RecordResponseDTO.GetRecordListResultDTO> getRecordList() {
        List<Records> recordsList = recordQueryService.getRecordList();

        return ApiResponse.onSuccess(
                RecordConverter.toGetRecordListResultDTO(recordsList)
        );
    }

    @Operation(summary = "특정 글 조회",
            description = """
                    특정 글 조회 api입니다.\n
                    """
    )
    @GetMapping("/{recordId}")
    public ApiResponse<RecordResponseDTO.GetRecordResultDTO> getRecordDetail(
            @PathVariable Long recordId
    ) {
        Records recordDetail = recordQueryService.getRecordDetail(recordId);

        return ApiResponse.onSuccess(
                RecordConverter.toGetRecordDetailResultDTO(recordDetail)
        );
    }
}
