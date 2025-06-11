package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.ErrorResponseDTO;
import LogITBackend.LogIT.converter.ErrorConverter;
import LogITBackend.LogIT.domain.ErrorCode;
import LogITBackend.LogIT.domain.ErrorCodeBlock;
import LogITBackend.LogIT.domain.ErrorInfo;
import LogITBackend.LogIT.domain.ErrorSolvedCode;
import LogITBackend.LogIT.repository.ErrorCodeRepository;
import LogITBackend.LogIT.repository.ErrorInfoRepository;
import LogITBackend.LogIT.repository.ErrorSolvedCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ErrorQueryServiceImpl implements ErrorQueryService {

    private final ErrorInfoRepository errorInfoRepository;
    private final ErrorCodeRepository errorCodeRepository;
    private final ErrorSolvedCodeRepository errorSolvedCodeRepository;

    @Override
    public ErrorResponseDTO.GetErrorInfoListResultDTO getErrorInfoList(String commitId) {
        List<ErrorInfo> errorInfoList = errorInfoRepository.findAllByCommitId(commitId);
        if (errorInfoList.isEmpty()) {
            throw new RuntimeException("No error information found for commit ID: " + commitId);
        }
        return ErrorConverter.toGetErrorInfoListResultDTO(errorInfoList);
    }

    @Override
    public ErrorResponseDTO.GetErrorCodeListDTO getErrorCodeList(Long errorInfoId) {
        ErrorInfo getErrorInfo = errorInfoRepository.findById(errorInfoId)
                .orElseThrow(() -> new RuntimeException("Error information not found for ID: " + errorInfoId));
        List<ErrorCode> errorCodeList = errorCodeRepository.findAllByErrorInfo(getErrorInfo);
        return ErrorConverter.toGetErrorCodeListDTO(errorCodeList);
    }

    @Override
    public ErrorResponseDTO.GetErrorSolvedCodeListDTO getErrorSolvedCodeList(Long errorInfoId) {
        ErrorInfo getErrorInfo = errorInfoRepository.findById(errorInfoId)
                .orElseThrow(() -> new RuntimeException("Error information not found for ID: " + errorInfoId));
        List<ErrorSolvedCode> errorSolvedCodeList = errorSolvedCodeRepository.findAllByErrorInfo(getErrorInfo);
        return ErrorConverter.toGetErrorSolvedCodeListDTO(errorSolvedCodeList);
    }

}
