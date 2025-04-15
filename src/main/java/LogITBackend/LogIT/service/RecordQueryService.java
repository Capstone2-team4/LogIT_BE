package LogITBackend.LogIT.service;

import LogITBackend.LogIT.domain.Records;

import java.util.List;

public interface RecordQueryService {
    List<Records> getRecordList();
    Records getRecordDetail(Long recordId);
}
