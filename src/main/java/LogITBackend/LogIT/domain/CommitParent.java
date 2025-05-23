package LogITBackend.LogIT.domain;

import LogITBackend.LogIT.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "commit_parents",
        uniqueConstraints = @UniqueConstraint(columnNames = {"commit_id", "parent_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommitParent extends BaseEntity {

    @Id // 단일 id 방식
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commit_id")
    private Commit commit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Commit parent;

}
