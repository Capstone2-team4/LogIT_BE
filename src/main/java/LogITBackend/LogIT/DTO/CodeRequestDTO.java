package LogITBackend.LogIT.DTO;

import LogITBackend.LogIT.domain.CodeCategories;
import LogITBackend.LogIT.domain.Codes;
import LogITBackend.LogIT.domain.Users;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeRequestDTO {
    private String id;        // snippetId
    private String title;
    private String filePath;
    private int startOffset; // 추가: 범위의 시작 오프셋
    private int endOffset;   // 추가: 범위의 끝 오프셋
    private String content;
    private String code;
    private String category;

//    public Codes toEntity(Users user, CodeCategories category) {
//        return Codes.builder()
//                .file()
//                .fileLocation(filePath)
//                .title(title)
//                .code(code)
//                .content(content)
//                .line(line)
//                .codeCategories(category)
//                .build();
//
//
//    }
}
