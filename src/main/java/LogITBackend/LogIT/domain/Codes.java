package LogITBackend.LogIT.domain;

import LogITBackend.LogIT.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Codes extends BaseEntity {

    // Id : 플러그인에서 만든 uuid 사용, 자동 생성x
    @Id
    private String id;

    @Column(nullable = false, length = 40)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String code;

    @Column(nullable = false, length = 40)
    private String commitId;

    @Column(nullable = false)
    private Integer startOffset;

    @Column(nullable = false)
    private Integer endOffset;

    @Column(nullable = false, length = 255)
    private String fileName;

    @Column(nullable = false, length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_category_id")
    private CodeCategories codeCategories;




    public void setDiaryCategories(CodeCategories codeCategories) {
        // 기존에 이미 등록되어 있던 관계를 제거
        if (this.codeCategories != null) {
            this.codeCategories.getCodesList().remove(this);
        }

        this.codeCategories = codeCategories;

        // 양방향 관계를 설정
        if (codeCategories != null) {
            codeCategories.getCodesList().add(this);
        }
    }

}
