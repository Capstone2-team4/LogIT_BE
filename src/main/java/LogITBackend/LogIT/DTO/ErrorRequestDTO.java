package LogITBackend.LogIT.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

public class ErrorRequestDTO {

    // 전체 요청을 감싸는 루트 DTO
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorListWrapperDTO {
        private List<SaveErrorInfoRequestDTO> errorList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaveErrorInfoRequestDTO {
        private String commitId;
        private String title;
        private String content;
        private List<ErrorCodeDTO> errorCode;
        private List<ErrorSolvedCodeDTO> errorSolvedCode;
    }

    @Getter
    @Setter
    public static class ErrorCodeDTO {
        private String filePath;
        private int errorLocation;
        private String code;
    }

    @Getter
    @Setter
    public static class ErrorSolvedCodeDTO {
        private String filePath;
        private String code;
        private List<ErrorCodeBlockDTO> errorCodeBlock;
    }

    @Getter
    @Setter
    public static class ErrorCodeBlockDTO {
        private String id;
        private String title;
        private String filePath;
        private int startOffset;
        private int endOffset;
        private String content;
        private String code;
        private String category;
        private String status;
    }
}
