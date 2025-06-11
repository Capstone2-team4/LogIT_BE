package LogITBackend.LogIT.DTO;

import lombok.*;

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
        Long errorInfoId;
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
        private List<GetCodeBlockDTO> codeBlockList;
    }

    @Getter
    @Setter
    @Builder
    public static class GetCodeBlockDTO {
//        private String id;
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
