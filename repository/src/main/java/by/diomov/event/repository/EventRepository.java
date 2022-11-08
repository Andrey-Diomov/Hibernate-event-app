package by.diomov.event.repository;

import by.diomov.event.entity.Event;
import by.diomov.event.parameter.ParametersEventQuery;
import java.util.List;
import java.util.Optional;

public interface EventRepository {

    Event create(Event event);

    Optional<Event> get(long id);

    List<Event> get(ParametersEventQuery parameters);

    Event patch(Event event, Event patch);

    void delete(Event event);
}