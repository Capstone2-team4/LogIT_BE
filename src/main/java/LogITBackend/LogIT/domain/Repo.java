package LogITBackend.LogIT.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @Column(length = 100, nullable = false)
    private String repoName;

    @OneToMany(mappedBy = "repo", cascade = CascadeType.ALL)
    private List<Branch> branches;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
