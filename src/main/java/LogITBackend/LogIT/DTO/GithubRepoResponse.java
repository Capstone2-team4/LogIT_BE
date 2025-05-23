package LogITBackend.LogIT.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GithubRepoResponse {
    private String ownerName;
    private List<RepositoryResponseDTO> repoList;
}
