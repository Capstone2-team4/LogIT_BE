package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.*;
import LogITBackend.LogIT.apiPayload.code.status.ErrorStatus;
import LogITBackend.LogIT.apiPayload.exception.GeneralException;
import LogITBackend.LogIT.config.security.SecurityUtil;
import LogITBackend.LogIT.domain.CodeCategories;
import LogITBackend.LogIT.domain.Codes;
import LogITBackend.LogIT.domain.Users;
import LogITBackend.LogIT.domain.common.RedisCommon;
import LogITBackend.LogIT.repository.CategoryRepository;
import LogITBackend.LogIT.repository.CodeRepository;
import LogITBackend.LogIT.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RedisCommon redisCommon;


    @Override
    public CodeResponseDTO addCodeBlock(CodeRequestDTO request) {
        Long userId = SecurityUtil.getCurrentUserId();
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        redisCommon.saveSnippet(String.valueOf(userId), request);

        return redisCommon.getSnippet(String.valueOf(userId), request.getId());

    }

    @Override
    public List<CodeResponseDTO> getCodeBlocks() {
        Long userId = SecurityUtil.getCurrentUserId();

        List<CodeResponseDTO> result = redisCommon.getAllSnippets(String.valueOf(userId));

        return result;
    }

    @Override
    public SnippetUpdateResponse setCodeBlock(String snippetId, SnippetUpdateRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();

        boolean success = redisCommon.updateSnippet(
                String.valueOf(userId),
                snippetId,
                request.getStartOffset(),
                request.getEndOffset(),
                request.getCode()
        );

        if (!success) {
            throw new GeneralException(ErrorStatus.SNIPPET_NOT_FOUND);
        }

        return SnippetUpdateResponse.builder()
                .snippetId(snippetId)
                .message("스니펫이 성공적으로 수정되었습니다.")
                .build();
    }

    @Override
    public CommitActionResponse commitCodeBlock(String commitId) {
        Long userId = SecurityUtil.getCurrentUserId();

        List<CodeResponseDTO> result = redisCommon.getAllSnippets(String.valueOf(userId));

        return CommitActionResponse.builder()
                .message("성공")
                .build();

    }


    //    @Override
//    @Transactional
//    public CodeResponseDTO addCode(CodeRequestDTO request) {
//        Long userId = SecurityUtil.getCurrentUserId();
//        Users user = userRepository.findById(userId)
//                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
//
//        CodeCategories category = categoryRepository.findByUsersIdAndName(userId, request.getCategory())
//                .orElseThrow(() -> new GeneralException(ErrorStatus.CATEGORY_NOT_FOUND));
//
//        Codes code = request.toEntity(user, category);
//
//        return CodeResponseDTO.toDTO(codeRepository.save(code));
//    }
}
