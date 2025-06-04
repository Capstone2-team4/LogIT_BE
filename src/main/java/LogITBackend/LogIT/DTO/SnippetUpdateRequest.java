package LogITBackend.LogIT.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SnippetUpdateRequest {
    private int startOffset;
    private int endOffset;
    private String code;
}
