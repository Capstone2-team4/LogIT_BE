package LogITBackend.LogIT.domain;

import LogITBackend.LogIT.domain.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Commits")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commit extends BaseEntity {
    @Id
    @Column(length = 40)
    private String id; // commit SHA

    @Column(length = 255)
    private String message;

    @Column(length = 100)
    private String stats;

    private LocalDateTime date;

    @OneToMany(mappedBy = "commit", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<File> files = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;
}
