package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.RecordRequestDTO;
import LogITBackend.LogIT.domain.Records;

public interface RecordCommandService {
    Records createRecord(RecordRequestDTO.CreateRecordRequestDTO request);
    Records editRecord(Long recordId, RecordRequestDTO.EditRecordRequestDTO request);
    void deleteRecord(Long recordId);
}
