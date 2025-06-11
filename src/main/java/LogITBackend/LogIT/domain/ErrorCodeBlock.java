package LogITBackend.LogIT.domain;

import LogITBackend.LogIT.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@DynamicInsert
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ErrorCodeBlock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String code;

    @Column(nullable = false)
    private Integer startOffset;

    @Column(nullable = false)
    private Integer endOffset;

    @Column(nullable = false, length = 255)
    private String fileName;

    @Column(nullable = false, length = 20)
    private String status;

    @Column(nullable = false, length = 40)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "error_solved_code_id")
    private ErrorSolvedCode errorSolvedCode;

    public void setErrorSolvedCode(ErrorSolvedCode errorSolvedCode) {
//        // 기존에 이미 등록되어 있던 관계를 제거
//        if (this.errorSolvedCode != null) {
//            this.errorSolvedCode.getErrorCodeBlockList().remove(this);
//        }
        this.errorSolvedCode = errorSolvedCode;

//        // 양방향 관계를 설정
//        if (errorSolvedCode != null) {
//            errorSolvedCode.getErrorCodeBlockList().add(this);
//        }
    }
}
