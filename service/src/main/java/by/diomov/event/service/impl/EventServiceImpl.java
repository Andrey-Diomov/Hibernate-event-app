package by.diomov.event.service.impl;

import by.diomov.event.dto.EventDTO;
import by.diomov.event.dto.ParametersEventQueryDTO;
import by.diomov.event.dto.PatchEventDTO;
import by.diomov.event.entity.Event;
import by.diomov.event.exception.EventNotFoundException;
import by.diomov.event.mapper.EventServiceMapper;
import by.diomov.event.mapper.ParametersEventQueryServiceMapper;
import by.diomov.event.parameter.ParametersEventQuery;
import by.diomov.event.repository.EventRepository;
import by.diomov.event.service.EventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventServiceMapper eventMapper;
    private final ParametersEventQueryServiceMapper queryMapper;

    @Override
    public EventDTO create(EventDTO eventDTO) {
        Event event = eventMapper.convertToEntity(eventDTO);
        return eventMapper.convertToDTO(eventRepository.create(event));
    }

    @Override
    public EventDTO getById(long id) {
        return eventMapper.convertToDTO(eventRepository.get(id)
                .orElseThrow(() -> new EventNotFoundException(id)));
    }

    @Override
    public List<EventDTO> getAll(ParametersEventQueryDTO parametersDTO) {
        ParametersEventQuery parameters = queryMapper.convertToEntity(parametersDTO);
        return eventRepository.get(parameters).stream()
                .map(eventMapper::convertToDTO)
                .collect(toList());
    }

    @Override
    public EventDTO patch(PatchEventDTO patchEventDTO, long id) {
        Event event = eventRepository.get(id).orElseThrow(() -> new EventNotFoundException(id));
        Event patchEvent = eventMapper.convertToEntity(patchEventDTO);
        return eventMapper.convertToDTO(eventRepository.patch(event, patchEvent));
    }

    @Override
    public void delete(long id) {
        Event event = eventRepository.get(id).orElseThrow(() -> new EventNotFoundException(id));
        eventRepository.delete(event);
    }
}