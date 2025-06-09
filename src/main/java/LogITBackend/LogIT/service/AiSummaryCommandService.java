package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.Chatgpt.AiSummaryRequestDTO;
import LogITBackend.LogIT.DTO.Chatgpt.AiSummaryResponseDTO;

public interface AiSummaryCommandService {
    AiSummaryResponseDTO.CreateAiSummaryResultDTO createAiSummary(
            String owners,
            String repository,
            AiSummaryRequestDTO.CreateAiSummaryRequest request
    );
}
