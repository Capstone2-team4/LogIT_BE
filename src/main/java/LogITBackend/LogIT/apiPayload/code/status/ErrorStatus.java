package LogITBackend.LogIT.apiPayload.code.status;

import LogITBackend.LogIT.apiPayload.code.BaseErrorCode;
import LogITBackend.LogIT.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 회원 관련 응답 1000
    USER_ID_NULL(HttpStatus.BAD_REQUEST, "USER_1001", "사용자 아이디는 필수 입니다."),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_1002", "해당하는 사용자가 존재하지 않습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.BAD_REQUEST, "USER_1003", "닉네임은 필수 입니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER_1004", "이미 존재하는 이메일 입니다."),
    NAME_NOT_EQUAL(HttpStatus.BAD_REQUEST, "USER_1005", "이름이 일치하지 않습니다."),
    ID_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_1006", "해당하는 ID가 존재하지 않습니다."),
    ID_NOT_EQUAL(HttpStatus.BAD_REQUEST, "USER_1007", "ID가 일치하지 않습니다."),
    SAME_PASSWORD(HttpStatus.BAD_REQUEST, "USER_1008", "이전 비밀번호와 동일합니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
