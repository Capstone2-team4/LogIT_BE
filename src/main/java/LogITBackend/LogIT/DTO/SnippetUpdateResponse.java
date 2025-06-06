package LogITBackend.LogIT.DTO;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SnippetUpdateResponse {
    private String snippetId;
    private String message;
//    private int startOffset;
//    private int endOffset;
//    private String code;
}
