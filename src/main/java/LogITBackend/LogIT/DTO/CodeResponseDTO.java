package LogITBackend.LogIT.DTO;

import LogITBackend.LogIT.domain.Codes;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CodeResponseDTO {
    private String id;
    private String title;
    private String filePath;
    private int startOffset; // 추가: 범위의 시작 오프셋
    private int endOffset;
    private String content;
    private String code;
    private String category;

//    public static CodeResponseDTO toDTO(Codes codes) {
//        return CodeResponseDTO.builder()
//                .title(codes.getTitle())
//                .content(codes.getContent())
//                .filePath(codes.getFileLocation())
//                .category(codes.getCodeCategories().getName())
//                .code(codes.getCode())
//                .build();
//    }
}
