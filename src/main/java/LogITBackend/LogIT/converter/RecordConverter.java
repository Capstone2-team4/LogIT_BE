package LogITBackend.LogIT.converter;

import LogITBackend.LogIT.DTO.RecordRequestDTO;
import LogITBackend.LogIT.DTO.RecordResponseDTO;
import LogITBackend.LogIT.domain.Records;

import java.util.List;
import java.util.stream.Collectors;

public class RecordConverter {
    // Convert RecordRequestDTO to Records entity
    public static Records toRecords(RecordRequestDTO.CreateRecordRequestDTO request) {
        return Records.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    // Convert Records entity to RecordResponseDTO
    public static RecordResponseDTO.RecordResultDTO toRecordResultDTO(Records record) {
        return RecordResponseDTO.RecordResultDTO.builder()
                .recordId(record.getId())
                .title(record.getTitle())
                .content(record.getContent())
                .createdAt(record.getCreatedAt())
                .build();
    }

    public static RecordResponseDTO.GetRecordListResultDTO toGetRecordListResultDTO(List<Records> recordList) {
        List<RecordResponseDTO.GetRecordResultDTO> getRecordResultDTOList = recordList.stream()
                .map(RecordConverter::toGetRecordResultDTO).collect(Collectors.toList());

        return RecordResponseDTO.GetRecordListResultDTO.builder()
                .getRecordResultDTOList(getRecordResultDTOList)
                .build();
    }

    public static RecordResponseDTO.GetRecordResultDTO toGetRecordResultDTO(Records record) {
        String contentPreview = record.getContent().length() > 20 ? record.getContent().substring(0, 20) + "..." : record.getContent();

        return RecordResponseDTO.GetRecordResultDTO.builder()
                .recordId(record.getId())
                .title(record.getTitle())
                .content(contentPreview)
                .createdAt(record.getCreatedAt())
                .build();
    }
}
