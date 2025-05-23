package LogITBackend.LogIT.apiPayload.exception.handler;


import LogITBackend.LogIT.apiPayload.code.BaseErrorCode;
import LogITBackend.LogIT.apiPayload.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
