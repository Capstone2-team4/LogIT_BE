package LogITBackend.LogIT.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommitActionResponse {
    private String message;
}
