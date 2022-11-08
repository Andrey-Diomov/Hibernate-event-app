package by.diomov.event.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PatchEventDTO {

    @Size(min = 3, max = 250)
    private String topic;

    @Size(min = 3, max = 250)
    private String description;

    @Size(min = 3, max = 250)
    private String organizer;
}