package LogITBackend.LogIT.converter;

import LogITBackend.LogIT.DTO.Chatgpt.AiSummaryRequestDTO;
import LogITBackend.LogIT.DTO.Chatgpt.AiSummaryResponseDTO;
import LogITBackend.LogIT.domain.SummaryTemplate;

public class AiSummaryConverter {
    public static SummaryTemplate toSummaryTemplate(AiSummaryRequestDTO.CreateSummaryTemplateRequest request) {
        return SummaryTemplate.builder()
                .content(request.getTemplate())
                .build();
    }

    public static AiSummaryResponseDTO.getSummaryTemplateResultDTO toGetSummaryTemplateResultDTO(SummaryTemplate summaryTemplate) {
        return AiSummaryResponseDTO.getSummaryTemplateResultDTO.builder()
                .template(summaryTemplate.getContent())
                .build();
    }
}
