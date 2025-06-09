package LogITBackend.LogIT.DTO.Chatgpt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AiSummaryResponseDTO {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateAiSummaryResultDTO {
        private String AiSummaryRecord;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getSummaryTemplateResultDTO {
        private String template;
    }
}
