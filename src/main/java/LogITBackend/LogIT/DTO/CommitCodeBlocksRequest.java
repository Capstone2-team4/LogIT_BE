package LogITBackend.LogIT.DTO;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommitCodeBlocksRequest {
    private String commitId;
    /**
     * key:   snippetId(UUID)
     * value: CodeBlockDto (id, filePath, startOffset, ... 등)
     */
    private Map<String, CodeRequestDTO> bookmarksMap;
}
