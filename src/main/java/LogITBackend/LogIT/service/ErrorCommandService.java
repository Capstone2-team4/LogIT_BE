package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.ErrorRequestDTO;

public interface ErrorCommandService {
    void saveErrorInfo(ErrorRequestDTO.ErrorListWrapperDTO request);
}
