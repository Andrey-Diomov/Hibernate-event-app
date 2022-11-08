package by.diomov.event.mapper;

import by.diomov.event.dto.EventDTO;
import by.diomov.event.dto.PatchEventDTO;
import by.diomov.event.entity.Event;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EventServiceMapper {
    private final ModelMapper mapper;

    public EventDTO convertToDTO(Event event) {
        return mapper.map(event, EventDTO.class);
    }

    public Event convertToEntity(EventDTO eventDTO) {
        return mapper.map(eventDTO, Event.class);
    }

    public Event convertToEntity(PatchEventDTO patchEventDTO) {
        return mapper.map(patchEventDTO, Event.class);
    }
}