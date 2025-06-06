package LogITBackend.LogIT.DTO;

import LogITBackend.LogIT.domain.Codes;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
    private String status;
    private LocalDateTime date;


}
