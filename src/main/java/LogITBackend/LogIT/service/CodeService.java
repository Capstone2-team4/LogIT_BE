package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.*;
import LogITBackend.LogIT.domain.Codes;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CodeService {
    CodeResponseDTO addCodeBlock(CodeRequestDTO request);

    List<CodeResponseDTO> getCodeBlocks();

    SnippetUpdateResponse setCodeBlock(String snippetId, SnippetUpdateRequest request);

    CommitActionResponse commitCodeBlock(String commitId);
//    CodeResponseDTO addCode(@RequestBody CodeRequestDTO request);
}
