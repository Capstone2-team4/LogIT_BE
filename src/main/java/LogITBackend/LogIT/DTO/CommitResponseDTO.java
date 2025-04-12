package LogITBackend.LogIT.DTO;


import LogITBackend.LogIT.domain.Commit;
import LogITBackend.LogIT.domain.File;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitResponseDTO {
    private String id; //Commit id
    private Long user_id;
    private String message;
    private String stats;
    private LocalDateTime date;

    public static CommitResponseDTO fromEntity(Commit commit) {
        return new CommitResponseDTO(
                commit.getId(),
                commit.getUser().getId(),
                commit.getMessage(),
                commit.getStats(),
                commit.getDate()
        );
    }
}
