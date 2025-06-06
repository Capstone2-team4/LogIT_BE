package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.*;

import java.util.List;
import java.util.Map;

public interface CodeService {
    CodeResponseDTO addCodeBlock(CodeRequestDTO request);

    List<CodeResponseDTO> getCodeBlocks();

    SnippetUpdateResponse setCodeBlock(String snippetId, SnippetUpdateRequest request);

    CommitCodeBlocksResponse commitCodeBlock(String commitId, Map<String, CodeRequestDTO> bookmarksMap);

    SnippetUpdateResponse setCodeBlockStatus(String snippetId);

    CodeBlockListResponse getCodeBlockList(String commitId);
}
