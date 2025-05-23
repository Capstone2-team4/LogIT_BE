package LogITBackend.LogIT.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryResponseDTO {
        private String repoName;
        LocalDateTime createdAt;
        LocalDateTime updatedAt;
}
