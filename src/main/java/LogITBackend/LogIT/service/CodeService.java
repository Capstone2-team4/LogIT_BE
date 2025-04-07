package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.CodeRequestDTO;
import LogITBackend.LogIT.DTO.CodeResponseDTO;
import LogITBackend.LogIT.domain.Codes;
import org.springframework.web.bind.annotation.RequestBody;

public interface CodeService {
    CodeResponseDTO addCode(@RequestBody CodeRequestDTO request);
}
