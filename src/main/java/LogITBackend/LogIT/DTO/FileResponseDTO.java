package LogITBackend.LogIT.DTO;

import LogITBackend.LogIT.domain.File;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

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
}
