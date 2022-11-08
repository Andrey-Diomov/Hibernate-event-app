package by.diomov.event.service;

import by.diomov.event.dto.EventDTO;
import by.diomov.event.dto.ParametersEventQueryDTO;
import by.diomov.event.dto.PatchEventDTO;
import java.util.List;

public interface EventService {
    EventDTO create(EventDTO entity);

    EventDTO getById(long id);

    List<EventDTO> getAll(ParametersEventQueryDTO parameters);

    EventDTO patch(PatchEventDTO patchEventDTO, long id);

    void delete(long id);
}