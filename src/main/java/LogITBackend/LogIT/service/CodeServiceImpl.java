package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.CategoryResponseDTO;
import LogITBackend.LogIT.DTO.CodeRequestDTO;
import LogITBackend.LogIT.DTO.CodeResponseDTO;
import LogITBackend.LogIT.apiPayload.code.status.ErrorStatus;
import LogITBackend.LogIT.apiPayload.exception.GeneralException;
import LogITBackend.LogIT.config.security.SecurityUtil;
import LogITBackend.LogIT.domain.CodeCategories;
import LogITBackend.LogIT.domain.Codes;
import LogITBackend.LogIT.domain.Users;
import LogITBackend.LogIT.repository.CategoryRepository;
import LogITBackend.LogIT.repository.CodeRepository;
import LogITBackend.LogIT.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements CodeService {

    private final CodeRepository codeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;


    @Override
    @Transactional
    public CodeResponseDTO addCode(CodeRequestDTO request) {
        Long userId = SecurityUtil.getCurrentUserId();
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        CodeCategories category = categoryRepository.findByUsersIdAndName(userId, request.getCategory())
                .orElseThrow(() -> new GeneralException(ErrorStatus.CATEGORY_NOT_FOUND));

        Codes code = request.toEntity(user, category);

        return CodeResponseDTO.toDTO(codeRepository.save(code));
    }
}
