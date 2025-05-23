package LogITBackend.LogIT.service;

import LogITBackend.LogIT.apiPayload.code.status.ErrorStatus;
import LogITBackend.LogIT.apiPayload.exception.handler.ExceptionHandler;
import LogITBackend.LogIT.config.security.SecurityUtil;
import LogITBackend.LogIT.domain.Records;
import LogITBackend.LogIT.domain.Users;
import LogITBackend.LogIT.repository.RecordRepository;
import LogITBackend.LogIT.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordQueryServiceImpl implements RecordQueryService{

    private final UserRepository userRepository;
    private final RecordRepository recordRepository;

    @Override
    public List<Records> getRecordList() {
        Long userId = SecurityUtil.getCurrentUserId();
        Users getUser = userRepository.findById(userId).orElseThrow(() -> new ExceptionHandler(ErrorStatus.USER_NOT_FOUND));
        List<Records> recordsList = recordRepository.findAllByUsersOrderByCreatedAtDesc(getUser);
        return recordsList;
    }

    @Override
    public Records getRecordDetail(Long recordId) {
        // 해당유저의 기록이 맞는지 확인하기 <- 추후에
        Records getRecord = recordRepository.findById(recordId).orElseThrow(() -> new ExceptionHandler(ErrorStatus.RECORD_NOT_FOUND));
        return getRecord;
    }
}
