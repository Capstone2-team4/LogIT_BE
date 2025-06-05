package LogITBackend.LogIT.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommitCodeBlocksResponse {
    private String message;
}
