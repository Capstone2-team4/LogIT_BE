package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.ErrorResponseDTO;

public interface ErrorQueryService {
    ErrorResponseDTO.GetErrorInfoListResultDTO getErrorInfoList(String commitId);
    ErrorResponseDTO.GetErrorCodeListDTO getErrorCodeList(Long errorInfoId);
    ErrorResponseDTO.GetErrorSolvedCodeListDTO getErrorSolvedCodeList(Long errorInfoId);
}
