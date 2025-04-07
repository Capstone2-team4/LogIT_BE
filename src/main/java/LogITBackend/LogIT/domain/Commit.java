package LogITBackend.LogIT.domain;

import LogITBackend.LogIT.domain.common.BaseEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Commits")
public class Commit extends BaseEntity {

    @Id
    @Column(length = 40)
    private String id; // commit SHA

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(length = 50)
    private String message;

    @Column(length = 100)
    private String stats;

    private LocalDateTime date;

    @OneToMany(mappedBy = "commit", cascade = CascadeType.ALL)
    private List<File> files = new ArrayList<>();

}
