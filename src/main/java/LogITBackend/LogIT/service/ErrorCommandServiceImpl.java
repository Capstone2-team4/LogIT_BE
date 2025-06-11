package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.ErrorRequestDTO;
import LogITBackend.LogIT.domain.ErrorCode;
import LogITBackend.LogIT.domain.ErrorCodeBlock;
import LogITBackend.LogIT.domain.ErrorInfo;
import LogITBackend.LogIT.domain.ErrorSolvedCode;
import LogITBackend.LogIT.repository.ErrorCodeBlockRepository;
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
    private final ErrorCodeBlockRepository errorCodeBlockRepository;

    public void saveErrorInfo(ErrorRequestDTO.ErrorListWrapperDTO request) {
//        // errorList 내부의 여러 SaveErrorInfoRequestDTO들을 순회
//        for (ErrorRequestDTO.SaveErrorInfoRequestDTO errorItem : request.getErrorList()) {
//
//            // 1. ErrorInfo 저장
//            ErrorInfo errorInfo = ErrorInfo.builder()
//                    .commitId(errorItem.getCommitId())
//                    .title(errorItem.getTitle())
//                    .content(errorItem.getContent())
//                    .build();
//            ErrorInfo savedErrorInfo = errorInfoRepository.save(errorInfo);
//
//            // 2. ErrorCode 리스트 저장
//            List<ErrorCode> errorCodes = errorItem.getErrorCode().stream()
//                    .map(dto -> ErrorCode.builder()
//                            .errorInfo(savedErrorInfo)
//                            .filePath(dto.getFilePath())
//                            .errorLocation(dto.getErrorLocation())
//                            .code(dto.getCode())
//                            .build())
//                    .toList();
//            errorCodeRepository.saveAll(errorCodes);
//
//            // 3. ErrorSolvedCode + ErrorCodeBlock 저장
//            for (ErrorRequestDTO.ErrorSolvedCodeDTO solvedDTO : errorItem.getErrorSolvedCode()) {
//                ErrorSolvedCode solvedCode = ErrorSolvedCode.builder()
//                        .errorInfo(savedErrorInfo)
//                        .filePath(solvedDTO.getFilePath())
//                        .code(solvedDTO.getCode())
//                        .build();
//                ErrorSolvedCode savedSolvedCode = errorSolvedCodeRepository.save(solvedCode);
//
//                List<ErrorCodeBlock> blocks = solvedDTO.getErrorCodeBlock().stream()
//                        .map(blockDTO -> ErrorCodeBlock.builder()
////                                .id(blockDTO.getId())
//                                .title(blockDTO.getTitle())
//                                .fileName(blockDTO.getFilePath())
//                                .startOffset(blockDTO.getStartOffset())
//                                .endOffset(blockDTO.getEndOffset())
//                                .content(blockDTO.getContent())
//                                .code(blockDTO.getCode())
//                                .category(blockDTO.getCategory())
//                                .status(blockDTO.getStatus())
////                                .errorSolvedCode(savedSolvedCode) // 연결
//                                .build())
//                        .peek(block -> block.setErrorSolvedCode(savedSolvedCode)) // 🔥 여기서 setter 호출
//                        .toList();
//                // ErrorCodeBlock 저장
//                errorCodeBlockRepository.saveAll(blocks); // 실제 구현체에서 이 메서드 제공해야 함
//            }
//        }

        // errorList 내부의 여러 SaveErrorInfoRequestDTO들을 순회
        for (ErrorRequestDTO.SaveErrorInfoRequestDTO errorItem : request.getErrorList()) {

            // 1. ErrorInfo 저장
            ErrorInfo errorInfo = ErrorInfo.builder()
                    .commitId(errorItem.getCommitId())
                    .title(errorItem.getTitle())
                    .content(errorItem.getContent())
                    .build();
            ErrorInfo savedErrorInfo = errorInfoRepository.save(errorInfo);

            // 2. ErrorCode 리스트 저장 - 각각 개별적으로 저장
            if (errorItem.getErrorCode() != null) {
                for (ErrorRequestDTO.ErrorCodeDTO dto : errorItem.getErrorCode()) {
                    ErrorCode errorCode = ErrorCode.builder()
                            .errorInfo(savedErrorInfo)
                            .filePath(dto.getFilePath())
                            .errorLocation(dto.getErrorLocation())
                            .code(dto.getCode())
                            .build();
                    errorCodeRepository.save(errorCode); // saveAll 대신 개별 저장
                }
            }

            // 3. ErrorSolvedCode + ErrorCodeBlock 저장
            if (errorItem.getErrorSolvedCode() != null) {
                for (ErrorRequestDTO.ErrorSolvedCodeDTO solvedDTO : errorItem.getErrorSolvedCode()) {
                    ErrorSolvedCode solvedCode = ErrorSolvedCode.builder()
                            .errorInfo(savedErrorInfo)
                            .filePath(solvedDTO.getFilePath())
                            .code(solvedDTO.getCode())
                            .build();
                    ErrorSolvedCode savedSolvedCode = errorSolvedCodeRepository.save(solvedCode);

                    // ErrorCodeBlock 개별 저장
                    if (solvedDTO.getErrorCodeBlock() != null) {
                        for (ErrorRequestDTO.ErrorCodeBlockDTO blockDTO : solvedDTO.getErrorCodeBlock()) {
                            ErrorCodeBlock block = ErrorCodeBlock.builder()
                                    .title(blockDTO.getTitle())
                                    .fileName(blockDTO.getFilePath())
                                    .startOffset(blockDTO.getStartOffset())
                                    .endOffset(blockDTO.getEndOffset())
                                    .content(blockDTO.getContent())
                                    .code(blockDTO.getCode())
                                    .category(blockDTO.getCategory())
                                    .status(blockDTO.getStatus())
                                    .build();
                            block.setErrorSolvedCode(savedSolvedCode);
                            errorCodeBlockRepository.save(block); // saveAll 대신 개별 저장
                        }
                    }
                }
            }
        }
    }
}
