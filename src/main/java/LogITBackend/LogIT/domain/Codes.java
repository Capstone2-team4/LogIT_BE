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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TEXT")
    private String code;

    private LocalDateTime date;

    @Column(nullable = false)
    private Integer line;

    @Column(nullable = false, length = 50)
    private String fileLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_category_id")
    private CodeCategories codeCategories;

    public void setUsers(Users users) {
        // 기존에 이미 등록되어 있던 관계를 제거
        if (this.users != null) {
            this.users.getCodesList().remove(this);
        }

        this.users = users;

        // 양방향 관계를 설정
        if (users != null) {
            users.getCodesList().add(this);
        }
    }

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
