package by.diomov.event.repository.impl;

import by.diomov.event.entity.Event;
import by.diomov.event.parameter.ParametersEventQuery;
import by.diomov.event.repository.EventRepository;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Repository
@AllArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private static final String TOPIC = "topic";
    private static final String ORGANIZER = "organizer";
    private static final String TIME = "time";
    private static final String LIKE_OPERATOR_FORMAT = "%%%s%%";
    private static final String DEFAULT_SORTING_MODE = "ASC";

    private final SessionFactory sessionFactory;

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Event create(Event event) {
        Long id = (Long) getCurrentSession().save(event);
        event.setId(id);
        return event;
    }

    @Override
    public Optional<Event> get(long id) {
        return Optional.ofNullable(getCurrentSession().find(Event.class, id));
    }

    @Override
    public List<Event> get(ParametersEventQuery parameters) {
        return getCurrentSession()
                .createQuery(createCriteriaQuery(parameters))
                .list();
    }

    @Override
    public Event patch(Event event, Event patch) {
        String topic = patch.getTopic();
        if (topic != null) {
            event.setTopic(topic);
        }
        String description = patch.getDescription();
        if (description != null) {
            event.setDescription(description);
        }
        String organizer = patch.getOrganizer();
        if (organizer != null) {
            event.setOrganizer(organizer);
        }

        String location = patch.getLocation();
        if (location != null) {
            event.setLocation(location);
        }

        getCurrentSession().update(event);
        return event;
    }

    @Override
    public void delete(Event event) {
        getCurrentSession().delete(event);
    }

    private CriteriaQuery<Event> createCriteriaQuery(ParametersEventQuery parameters) {
        CriteriaBuilder builder = getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);
        Root<Event> root = query.from(Event.class);

        List<Predicate> predicates = new ArrayList<>();

        if (isNotBlank(parameters.getTopic())) {
            predicates.add(builder.like(root.get(TOPIC),
                    format(LIKE_OPERATOR_FORMAT, parameters.getTopic())));
        }

        if (isNotBlank(parameters.getOrganizer())) {
            predicates.add(builder.like(root.get(ORGANIZER),
                    format(LIKE_OPERATOR_FORMAT, parameters.getOrganizer())));
        }

        if (isNotBlank(parameters.getSort())) {
            if (parameters.getSort().equalsIgnoreCase(DEFAULT_SORTING_MODE)) {
                query.orderBy(builder.asc(root.get(TIME)));
            } else {
                query.orderBy(builder.desc(root.get(TIME)));
            }
        }

        //query.orderBy(builder.asc(root.get(TOPIC)));
        return query.select(root).where(builder.and(predicates.toArray(Predicate[]::new)));
    }
}