package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.ErrorRequestDTO;
import LogITBackend.LogIT.domain.ErrorCode;
import LogITBackend.LogIT.domain.ErrorInfo;
import LogITBackend.LogIT.domain.ErrorSolvedCode;
import LogITBackend.LogIT.repository.ErrorCodeRepository;
import LogITBackend.LogIT.repository.ErrorInfoRepository;
import LogITBackend.LogIT.repository.ErrorSolvedCodeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ErrorCommandServiceImpl implements ErrorCommandService {

    private final ErrorInfoRepository errorInfoRepository;
    private final ErrorCodeRepository errorCodeRepository;
    private final ErrorSolvedCodeRepository errorSolvedCodeRepository;

    public void saveErrorInfo(ErrorRequestDTO.SaveErrorInfoRequestDTO request) {
        // 1. ErrorInfos 저장
        ErrorInfo errorInfo = ErrorInfo.builder()
                .commitId(request.getCommitId())
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        ErrorInfo getErrorInfo = errorInfoRepository.save(errorInfo);

        // 2. ErrorCodes 저장
        List<ErrorCode> errorCodes = request.getErrorCode().stream()
                .map(dto -> ErrorCode.builder()
                        .errorInfo(getErrorInfo)
                        .filePath(dto.getFilePath())
                        .errorLocation(dto.getErrorLocation())
                        .code(dto.getCode())
                        .build())
                .toList();
        errorCodeRepository.saveAll(errorCodes);

        // 3. ErrorSolvedCodes 저장
        List<ErrorSolvedCode> solvedCodes = request.getErrorSolvedCode().stream()
                .map(dto -> ErrorSolvedCode.builder()
                        .errorInfo(getErrorInfo)
                        .filePath(dto.getFilePath())
                        .code(dto.getCode())
                        .build())
                .toList();
        errorSolvedCodeRepository.saveAll(solvedCodes);
    }
}
