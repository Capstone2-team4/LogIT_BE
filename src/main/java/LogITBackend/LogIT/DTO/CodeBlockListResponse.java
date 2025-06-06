package LogITBackend.LogIT.DTO;


import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class CodeBlockListResponse {
    private String commitId;
    private List<CodeResponseDTO> CodeBlocks;
}
