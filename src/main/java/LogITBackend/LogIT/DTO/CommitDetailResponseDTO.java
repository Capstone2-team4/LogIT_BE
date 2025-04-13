package LogITBackend.LogIT.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitDetailResponseDTO {
    private CommitResponseDTO commitResponseDTO;
    private List<FileResponseDTO> files;
}
