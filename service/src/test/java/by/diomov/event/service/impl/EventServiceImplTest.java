package by.diomov.event.service.impl;

import by.diomov.event.dto.EventDTO;
import by.diomov.event.entity.Event;
import by.diomov.event.exception.EventNotFoundException;
import by.diomov.event.mapper.EventServiceMapper;
import by.diomov.event.mapper.ParametersEventQueryServiceMapper;
import by.diomov.event.repository.impl.EventRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import java.sql.Timestamp;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class EventServiceImplTest {
    private final EventRepositoryImpl eventMock = mock(EventRepositoryImpl.class);
    private final EventServiceImpl eventService = new EventServiceImpl(eventMock,
            new EventServiceMapper(new ModelMapper()),
            new ParametersEventQueryServiceMapper(new ModelMapper()));

    private Event event;
    private EventDTO eventDTO;

    @BeforeEach
    public void initEvent() {
        Timestamp dataTime = new Timestamp(System.currentTimeMillis());

        event = new Event();
        event.setId(1L);
        event.setTopic("Step by step");
        event.setDescription("Aim to something");
        event.setOrganizer("Bob");
        event.setTime(dataTime);
        event.setLocation("Paris");

        eventDTO = new EventDTO();
        eventDTO.setId(1L);
        eventDTO.setTopic("Step by step");
        eventDTO.setDescription("Aim to something");
        eventDTO.setOrganizer("Bob");
        eventDTO.setTime(dataTime);
        eventDTO.setLocation("Paris");
    }

    @Test
    public void testGetById() {
        long eventId = 1;

        when(eventMock.get(eventId)).thenReturn(Optional.of(event));

        EventDTO actualEventDTO = eventService.getById(eventId);

        assertEquals(eventDTO.getId(), actualEventDTO.getId());
        assertEquals(eventDTO.getTopic(), actualEventDTO.getTopic());
        assertEquals(eventDTO.getDescription(), actualEventDTO.getDescription());
        assertEquals(eventDTO.getOrganizer(), actualEventDTO.getOrganizer());
        assertEquals(eventDTO.getTime(), actualEventDTO.getTime());
        assertEquals(eventDTO.getLocation(), actualEventDTO.getLocation());
    }

    @Test
    public void testGetByIdToNonExistingId() {
        long nonExistingId = 1000;
        when(eventMock.get(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(EventNotFoundException.class, () -> eventService.getById(nonExistingId));
    }

    @Test
    public void testDeleteById() {
        long eventId =1;

        when(eventMock.get(eventId)).thenReturn(Optional.of(event));
        eventService.delete(eventId);
        verify(eventMock, times(1)).delete(event);
    }

    @Test
    public void testDeleteByNonExistingId() {
        long nonExistingId = 1000;
        when(eventMock.get(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(EventNotFoundException.class, () -> eventService.delete(nonExistingId));
    }
}