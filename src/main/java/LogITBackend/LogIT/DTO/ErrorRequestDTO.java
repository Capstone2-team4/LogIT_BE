package LogITBackend.LogIT.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

public class ErrorRequestDTO {

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
    }
}
