package LogITBackend.LogIT.domain.common;

import LogITBackend.LogIT.DTO.CodeRequestDTO;
import LogITBackend.LogIT.DTO.CodeResponseDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisCommon {
    private final RedisTemplate<String, String> redisTemplate;
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (com.google.gson.JsonDeserializer<LocalDateTime>)
                    (json, type, context) -> LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            .registerTypeAdapter(LocalDateTime.class, (com.google.gson.JsonSerializer<LocalDateTime>)
                    (src, type, context) -> new com.google.gson.JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
            .create();

    /**
     * MyBookMark 객체를 Redis Hash에 JSON으로 저장
     *   - redis Hash key: "user:{userId}:snippets"
     *   - field: snippetId
     *   - value: JSON 문자열 (CodeRequestDTO 직렬화 결과)
     */
    public void saveSnippet(String userId, CodeRequestDTO snippet) {
        String redisKey = "user:" + userId + ":snippets";
        String field    = snippet.getId();
        String value    = gson.toJson(snippet);

        // HashOperations<K, HK, HV>: K=String, HK=String, HV=String
        redisTemplate.opsForHash().put(redisKey, field, value);
    }

    /**
     * 저장된 스니펫을 하나 가져오기
     */
    public CodeResponseDTO getSnippet(String userId, String snippetId) {
        String redisKey = "user:" + userId + ":snippets";
        Object json     = redisTemplate.opsForHash().get(redisKey, snippetId);
        if (json == null) return null;
        return gson.fromJson((String) json, CodeResponseDTO.class);
    }

    /**
     * 해당 스니펫 삭제
     */
    public CodeResponseDTO.CodeDeleteResponseDTO deleteSnippet(String userId, String snippetId) {
        String redisKey = "user:" + userId + ":snippets";
        redisTemplate.opsForHash().delete(redisKey, snippetId);

        return CodeResponseDTO.CodeDeleteResponseDTO.builder()
                .id(snippetId)
                .message("snippet deleted from redis")
                .build();


    }

    /**
     * 유저가 저장한 모든 스니펫 목록 조회
     */
    public List<CodeResponseDTO> getAllSnippets(String userId) {
        String redisKey = "user:" + userId + ":snippets";
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(redisKey);

        List<CodeResponseDTO> result = new ArrayList<>();
        for (Object jsonVal : entries.values()) {
            result.add(gson.fromJson((String) jsonVal, CodeResponseDTO.class));
        }
        return result;
    }

    public boolean updateSnippet(String userId, String snippetId, int startOffset, int endOffset, String code) {
        String redisKey = "user:" + userId + ":snippets";
        Object json = redisTemplate.opsForHash().get(redisKey, snippetId);
        if (json == null) return false;

        CodeRequestDTO existing = gson.fromJson((String) json, CodeRequestDTO.class);

        // 필드 업데이트
        existing.setStartOffset(startOffset);
        existing.setEndOffset(endOffset);
        existing.setCode(code);

        // 다시 저장
        String updatedJson = gson.toJson(existing);
        redisTemplate.opsForHash().put(redisKey, snippetId, updatedJson);
        return true;
    }

    public boolean updateSnippetStatus(String userId, String snippetId) {
        String redisKey = "user:" + userId + ":snippets";
        Object json = redisTemplate.opsForHash().get(redisKey, snippetId);
        if (json == null) return false;

        CodeRequestDTO existing = gson.fromJson((String) json, CodeRequestDTO.class);

        // 필드 업데이트
        existing.setStatus("deleted");

        // 다시 저장
        String updatedJson = gson.toJson(existing);
        redisTemplate.opsForHash().put(redisKey, snippetId, updatedJson);
        return true;
    }


}
