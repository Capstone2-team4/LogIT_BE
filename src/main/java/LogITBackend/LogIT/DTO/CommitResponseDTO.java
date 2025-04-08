package LogITBackend.LogIT.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitResponseDTO {
    private String id; //Commit id
    private Long user_id;
    private String message;
    private String stats;
    private LocalDateTime date;
}
