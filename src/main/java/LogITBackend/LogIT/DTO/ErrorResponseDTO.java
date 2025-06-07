package LogITBackend.LogIT.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponseDTO {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetErrorInfoListResultDTO {
        List<ErrorResponseDTO.GetErrorInfoResultDTO> getErrorInfoResultDTOList;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetErrorInfoResultDTO {
        String title;
        String content;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetErrorCodeListDTO {
        private List<GetErrorCodeDTO> errorCodeList;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetErrorCodeDTO {
        private String filePath;
        private int errorLocation;
        private String code;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetErrorSolvedCodeListDTO {
        private List<GetErrorSolvedCodeDTO> errorSolvedCodeList;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetErrorSolvedCodeDTO {
        private String filePath;
        private String code;
    }
}
