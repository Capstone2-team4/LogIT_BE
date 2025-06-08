package LogITBackend.LogIT.DTO;

import LogITBackend.LogIT.domain.File;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.web.servlet.tags.form.TextareaTag;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponseDTO {
    private Long id;

    private String filename;

    private Long additions;

    private Long deletions;

    private String patch;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static FileResponseDTO fromEntity(File file) {
        return new FileResponseDTO(
                file.getId(),
                file.getFilename(),
                file.getAdditions(),
                file.getDeletions(),
                file.getPatch(),
                file.getCreatedAt(),
                file.getUpdatedAt()
        );
    }

    @Getter
    @Setter
    @Builder
    public static class CommitFileResponseDTO {
        private String commitId;
        private String filePath;
        private String content;
    }
}
