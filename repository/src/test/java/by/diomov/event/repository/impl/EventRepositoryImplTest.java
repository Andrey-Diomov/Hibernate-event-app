package by.diomov.event.repository.impl;

import by.diomov.event.config.RepositoryHibernateConfig;
import by.diomov.event.config.TestSpringHibernateConfig;
import by.diomov.event.entity.Event;
import by.diomov.event.parameter.ParametersEventQuery;
import by.diomov.event.repository.EventRepository;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional("transactionalManager")
@ContextConfiguration(classes = {RepositoryHibernateConfig.class, EventRepositoryImpl.class, TestSpringHibernateConfig.class})
public class EventRepositoryImplTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private EventRepository eventRepository;

    @Test
    public void testCreate() {
        Timestamp dataTime = new Timestamp(System.currentTimeMillis());
        Event event = new Event(null, "New_Topic", "Some_Description",
                "Name", dataTime, "Vitebsk");

        Event saved = eventRepository.create(event);

        assertEquals(event.getId(), saved.getId());
        assertEquals(event.getTopic(), saved.getTopic());
        assertEquals(event.getDescription(), saved.getDescription());
        assertEquals(event.getOrganizer(), saved.getOrganizer());
        assertEquals(event.getTime(), saved.getTime());
        assertEquals(event.getLocation(), saved.getLocation());
    }

    @Test
    public void testGetById() {
        long eventId = 2;
        Event event = eventRepository.get(eventId).orElse(null);

        assertEquals("Hotels", event.getTopic());
        assertEquals("Travelling abroad", event.getDescription());
        assertEquals("Masha", event.getOrganizer());
        assertEquals("Brest", event.getLocation());
    }

    @Test
    public void testGetAll() {
        List<Event> events = eventRepository.get(new ParametersEventQuery());
        assertEquals(3, events.size());
    }

    @Test
    public void testPatch() {
        Timestamp dataTime = new Timestamp(System.currentTimeMillis());
        Event patch = new Event(null, "New_Topic", "Some_Description",
                "Name", dataTime, "Vitebsk");

        Event eventToUpdate = eventRepository.get(1).get();
        Event updatedEvent = eventRepository.patch(eventToUpdate, patch);

        assertEquals(patch.getTopic(), updatedEvent.getTopic());
        assertEquals(patch.getOrganizer(), updatedEvent.getOrganizer());
        assertEquals(patch.getDescription(), updatedEvent.getDescription());
        assertEquals(patch.getLocation(), updatedEvent.getLocation());
    }

    @Test
    public void testDeleteById() {
        long id = 1;
        Event event = sessionFactory.getCurrentSession().get(Event.class, id);
        eventRepository.delete(event);
        Optional<Event> result = Optional.ofNullable(sessionFactory.getCurrentSession().get(Event.class, id));
        assertFalse(result.isPresent());
    }

    @Test
    void testGetByTopic() {
        ParametersEventQuery query = new ParametersEventQuery();
        query.setTopic("Sport");

        List<Event> events = eventRepository.get(query);

        assertEquals(1, events.size());
        assertEquals(3, events.get(0).getId());
        assertEquals("Sport", events.get(0).getTopic());
        assertEquals("Gym visiting for 1 month", events.get(0).getDescription());
        assertEquals("Sergey", events.get(0).getOrganizer());
        assertEquals("Kiev", events.get(0).getLocation());
    }

    @Test
    void testGetByOrganizer() {
        ParametersEventQuery query = new ParametersEventQuery();
        query.setOrganizer("Bob");

        List<Event> events = eventRepository.get(query);

        assertEquals(1, events.size());
        assertEquals(1, events.get(0).getId());
        assertEquals("Thai spa", events.get(0).getTopic());
        assertEquals("Complex of services", events.get(0).getDescription());
        assertEquals("Bob", events.get(0).getOrganizer());
        assertEquals("Minsk", events.get(0).getLocation());
    }

    @Test
    void testGetByPartTopicAndOrganizer() {
        ParametersEventQuery query = new ParametersEventQuery();
        query.setTopic("Ho");
        query.setOrganizer("Ma");

        List<Event> events = eventRepository.get(query);

        assertEquals(1, events.size());
        assertEquals(2, events.get(0).getId());
        assertEquals("Hotels", events.get(0).getTopic());
        assertEquals("Travelling abroad", events.get(0).getDescription());
        assertEquals("Masha", events.get(0).getOrganizer());
        assertEquals("Brest", events.get(0).getLocation());
    }
}