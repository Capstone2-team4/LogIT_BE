package LogITBackend.LogIT.DTO;

import LogITBackend.LogIT.domain.Codes;
import lombok.Builder;

@Builder
public class CodeResponseDTO {
    public String title;
    public String filePath;
    public int line;
    public String content;
    public String code;
    public String category;

    public static CodeResponseDTO toDTO(Codes codes) {
        return CodeResponseDTO.builder()
                .title(codes.getTitle())
                .content(codes.getContent())
                .line(codes.getLine())
                .filePath(codes.getFileLocation())
                .category(codes.getCodeCategories().getName())
                .code(codes.getCode())
                .build();
    }
}
