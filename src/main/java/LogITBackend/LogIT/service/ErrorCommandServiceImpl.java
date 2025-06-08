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

    public void saveErrorInfo(ErrorRequestDTO.ErrorListWrapperDTO request) {
        // errorList 내부의 여러 SaveErrorInfoRequestDTO들을 순회
        for (ErrorRequestDTO.SaveErrorInfoRequestDTO errorItem : request.getErrorList()) {

            // 1. ErrorInfo 저장
            ErrorInfo errorInfo = ErrorInfo.builder()
                    .commitId(errorItem.getCommitId())
                    .title(errorItem.getTitle())
                    .content(errorItem.getContent())
                    .build();
            ErrorInfo savedErrorInfo = errorInfoRepository.save(errorInfo);

            // 2. ErrorCode 리스트 저장
            List<ErrorCode> errorCodes = errorItem.getErrorCode().stream()
                    .map(dto -> ErrorCode.builder()
                            .errorInfo(savedErrorInfo)
                            .filePath(dto.getFilePath())
                            .errorLocation(dto.getErrorLocation())
                            .code(dto.getCode())
                            .build())
                    .toList();
            errorCodeRepository.saveAll(errorCodes);

            // 3. ErrorSolvedCode 리스트 저장
            List<ErrorSolvedCode> solvedCodes = errorItem.getErrorSolvedCode().stream()
                    .map(dto -> ErrorSolvedCode.builder()
                            .errorInfo(savedErrorInfo)
                            .filePath(dto.getFilePath())
                            .code(dto.getCode())
                            .build())
                    .toList();
            errorSolvedCodeRepository.saveAll(solvedCodes);
        }
    }
}
