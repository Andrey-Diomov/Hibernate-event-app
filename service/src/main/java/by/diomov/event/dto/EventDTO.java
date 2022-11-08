package by.diomov.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class EventDTO  {
    private Long id;

    @Size(min = 3, max = 150)
    @NotBlank
    private String topic;

    @Size(min = 3, max = 250)
    @NotBlank
    private String description;

    @Size(min = 3, max = 250)
    @NotBlank
    private String organizer;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp time;

    @Size(min = 3, max = 150)
    @NotBlank
    private String location;
}