package LogITBackend.LogIT.DTO.Chatgpt;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class AiSummaryRequestDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateAiSummaryRequest {
        private List<String> commitIdList;
        private String template;
    }
}
