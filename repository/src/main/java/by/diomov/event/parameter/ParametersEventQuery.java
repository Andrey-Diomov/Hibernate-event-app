package by.diomov.event.parameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametersEventQuery {
    private String topic;
    private String organizer;
    private String sortTopic;
    private String sortOrganizer;
    private String sortTime;
}