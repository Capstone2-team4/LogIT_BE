package LogITBackend.LogIT.apiPayload.exception.handler;


import LogITBackend.LogIT.apiPayload.code.BaseErrorCode;
import LogITBackend.LogIT.apiPayload.exception.GeneralException;

public class ExceptionHandler extends GeneralException {
    public ExceptionHandler(BaseErrorCode code) {
        super(code);
    }
}
