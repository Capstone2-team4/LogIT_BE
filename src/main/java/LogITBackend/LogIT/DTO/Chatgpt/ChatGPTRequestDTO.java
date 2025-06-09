package LogITBackend.LogIT.DTO.Chatgpt;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChatGPTRequestDTO {
    private String model;
    private List<MessageDTO> messages;

    public ChatGPTRequestDTO(String model, String systemPrompt, String userPrompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new MessageDTO("system", systemPrompt));
        this.messages.add(new MessageDTO("user", userPrompt));
    }
}
