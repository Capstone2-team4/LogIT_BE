package LogITBackend.LogIT.converter;

import LogITBackend.LogIT.DTO.ErrorResponseDTO;
import LogITBackend.LogIT.domain.ErrorCode;
import LogITBackend.LogIT.domain.ErrorInfo;
import LogITBackend.LogIT.domain.ErrorSolvedCode;

import java.util.List;
import java.util.stream.Collectors;

public class ErrorConverter {
    public static ErrorResponseDTO.GetErrorInfoResultDTO toGetErrorInfoResultDTO(ErrorInfo errorInfo) {
        return ErrorResponseDTO.GetErrorInfoResultDTO.builder()
                .title(errorInfo.getTitle())
                .content(errorInfo.getContent())
                .build();
    }

    public static ErrorResponseDTO.GetErrorInfoListResultDTO toGetErrorInfoListResultDTO(List<ErrorInfo> errorInfoList) {
        List<ErrorResponseDTO.GetErrorInfoResultDTO> getErrorInfoResultDTOList = errorInfoList.stream()
                .map(ErrorConverter::toGetErrorInfoResultDTO)
                .collect(Collectors.toList());

        return ErrorResponseDTO.GetErrorInfoListResultDTO.builder()
                .getErrorInfoResultDTOList(getErrorInfoResultDTOList)
                .build();
    }

    public static ErrorResponseDTO.GetErrorCodeDTO toGetErrorCodeDTO(ErrorCode errorCode) {
        return ErrorResponseDTO.GetErrorCodeDTO.builder()
                .filePath(errorCode.getFilePath())
                .errorLocation(errorCode.getErrorLocation())
                .code(errorCode.getCode())
                .build();
    }

    public static ErrorResponseDTO.GetErrorCodeListDTO toGetErrorCodeListDTO(List<ErrorCode> errorCodeList) {
        List<ErrorResponseDTO.GetErrorCodeDTO> getErrorCodeDTOList = errorCodeList.stream()
                .map(ErrorConverter::toGetErrorCodeDTO)
                .collect(Collectors.toList());

        return ErrorResponseDTO.GetErrorCodeListDTO.builder()
                .errorCodeList(getErrorCodeDTOList)
                .build();
    }

    public static ErrorResponseDTO.GetErrorSolvedCodeDTO toGetErrorSolvedCodeDTO(ErrorSolvedCode errorSolvedCode) {
        return ErrorResponseDTO.GetErrorSolvedCodeDTO.builder()
                .filePath(errorSolvedCode.getFilePath())
                .code(errorSolvedCode.getCode())
                .build();
    }

    public static ErrorResponseDTO.GetErrorSolvedCodeListDTO toGetErrorSolvedCodeListDTO(List<ErrorSolvedCode> errorSolvedCodeList) {
        List<ErrorResponseDTO.GetErrorSolvedCodeDTO> getErrorSolvedCodeDTOList = errorSolvedCodeList.stream()
                .map(ErrorConverter::toGetErrorSolvedCodeDTO)
                .collect(Collectors.toList());

        return ErrorResponseDTO.GetErrorSolvedCodeListDTO.builder()
                .errorSolvedCodeList(getErrorSolvedCodeDTOList)
                .build();
    }
}
