package by.diomov.event.parameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametersEventQuery {
    private String topic;
    private String organizer;
    private Timestamp time;
    private String sort;
}