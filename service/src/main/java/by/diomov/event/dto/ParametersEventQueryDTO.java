package by.diomov.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametersEventQueryDTO {
    private String topic;
    private String organizer;
    private Timestamp time;
    private String sort;
}