package LogITBackend.LogIT.domain;

import LogITBackend.LogIT.domain.common.BaseEntity;
import LogITBackend.LogIT.domain.enums.Gender;
import LogITBackend.LogIT.domain.enums.LoginType;
import LogITBackend.LogIT.domain.enums.UserStatus;
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
public class Users extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(nullable = false, length = 8)
    private String nickname;

    @Column(length = 15)
    private String username;

    @Column(length = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private LoginType loginType;

    @Column(length = 50)
    private String email;

    @Column(columnDefinition = "TEXT")
    private String profileImage;

    @Column(length = 50)
    private String githubNickname;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column
    private UserStatus status;

    private LocalDateTime inactiveDate;

    private String providerId;

    private String githubAccesstoken;

    private String accesstoken;

    // 로그인 관련
//    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Codes> codesList = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<CodeCategories> codeCategoriesList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Owner> ownerList = new ArrayList<>();

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List<Records> recordsList = new ArrayList<>();

    public void encodePassword(String password) {
        this.password = password;
    }

    public void updateAccessToken(String accessToken) {
        this.accesstoken = accessToken;
    }

    public void updateGithubAccessToken(String githubAccessToken) {
        this.githubAccesstoken = githubAccessToken;
    }

    public void updateProviderId(String providerId) {
        this.providerId = providerId;
    }

    public void updateGithubNickname(String nickname) {
        this.githubNickname = nickname;
    }
}
