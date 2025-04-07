package LogITBackend.LogIT.DTO;

import LogITBackend.LogIT.domain.CodeCategories;
import LogITBackend.LogIT.domain.Codes;
import LogITBackend.LogIT.domain.Users;
import lombok.Getter;

@Getter
public class CodeRequestDTO {
    public String title;
    public String filePath;
    public int line;
    public String content;
    public String code;
    public String category;

    public Codes toEntity(Users user, CodeCategories category) {
        return Codes.builder()
                .users(user)
                .fileLocation(filePath)
                .title(title)
                .code(code)
                .content(content)
                .line(line)
                .codeCategories(category)
                .build();


    }
}
