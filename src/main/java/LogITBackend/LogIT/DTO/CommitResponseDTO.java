package LogITBackend.LogIT.DTO;


import LogITBackend.LogIT.domain.Commit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitResponseDTO {
    private String id; //Commit id
    private Long repo_id;
    private String message;
    private String stats;
    private LocalDateTime date;

    public static CommitResponseDTO fromEntity(Commit commit) {
        return new CommitResponseDTO(
                commit.getId(),
                commit.getRepo().getId(),
                commit.getMessage(),
                commit.getStats(),
                commit.getDate()
        );
    }
}
