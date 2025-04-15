package LogITBackend.LogIT.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class RecordResponseDTO {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecordResultDTO {
        Long recordId;
        String title;
        String content;
        LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetRecordListResultDTO {
        List<GetRecordResultDTO> getRecordResultDTOList;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetRecordResultDTO {
        Long recordId;
        String title;
        String content;
        LocalDateTime createdAt;
    }
}
