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
    private String sortTopic;
    private String sortOrganizer;
    private String sortTime;
}