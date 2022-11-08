package by.diomov.event.controller;

import by.diomov.event.dto.EventDTO;
import by.diomov.event.dto.ParametersEventQueryDTO;
import by.diomov.event.dto.PatchEventDTO;
import by.diomov.event.exception.WrongDataException;
import by.diomov.event.exception.WrongIdException;
import by.diomov.event.service.EventService;
import by.diomov.event.util.ParseUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

/**
 * RestController EventController
 * support CRUD operation
 *
 * @author Andrey Diomov
 * @version 1.0
 */
@RestController
@AllArgsConstructor
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    /**
     * Creates new event by received data.
     *
     * @param eventDTO, the received information validated and then mapped to the corresponding DTO.
     * @return eventDTO with field id, assigned to the event in the database.
     * @throws WrongDataException, If any problems occur during validation.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDTO save(@RequestBody @Valid EventDTO eventDTO,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new WrongDataException(bindingResult);
        }
        return eventService.create(eventDTO);
    }

    /**
     * @param pathId of the event we want to get.
     * @return event
     * @throws WrongIdException, If the received id is negative.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventDTO getById(@PathVariable("id") String pathId) {
        long id = ParseUtils.parseId(pathId);
        if (id < 1) {
            throw new WrongIdException(id);
        }
        return eventService.getById(id);
    }

    /**
     * Gets a list of events by  the provided parameters.
     * <p>
     * The following parameters are expected :
     * 1) topic
     * 2) organizer
     * 3) sortTopic(ASC/DESC; ASC by default)
     * 4) sortOrganizer(ASC/DESC; ASC by default)
     * 5) sortTime(ASC/DESC; ASC by default)
     * Unknown parameters will be ignored.
     *
     * @return a list of events which match the provided parameters.
     * If there are no provided parameters then return a list of all events.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventDTO> get(
            @RequestParam(value = "topic", required = false) String topic,
            @RequestParam(value = "organizer", required = false) String organizer,
            @RequestParam(value = "sortTopic", required = false) String sortTopic,
            @RequestParam(value = "sortOrganizer", required = false) String sortOrganizer,
            @RequestParam(value = "sortTime", required = false) String sortTime) {

        ParametersEventQueryDTO parametersDTO = ParametersEventQueryDTO.builder()
                .topic(topic)
                .organizer(organizer)
                .sortTopic(sortTopic)
                .sortOrganizer(sortOrganizer)
                .sortTime(sortTime)
                .build();

        return eventService.getAll(parametersDTO);
    }

    /**
     * Updates only fields, that pass in request, others should not be updated.
     *
     * @param pathId of event.The request body contains fields for updating.
     * @return EventDTO with all fields. The fields received in the request have been updated.
     * @throws WrongDataException, If any problems occur during validation.
     * @throws WrongIdException, If the received id is negative.
     */
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventDTO patch(@PathVariable("id") String pathId,
                          @RequestBody @Valid PatchEventDTO patchEventDTO,
                          BindingResult bindingResult) {
        long id = ParseUtils.parseId(pathId);
        if (id < 1) {
            throw new WrongIdException(id);
        }

        if (bindingResult.hasErrors()) {
            throw new WrongDataException(bindingResult);
        }
        return eventService.patch(patchEventDTO, id);
    }

    /**
     * @param pathId of the event we want to delete.
     * @throws WrongIdException, If the received id is negative.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") String pathId) {
        long id = ParseUtils.parseId(pathId);
        if (id < 1) {
            throw new WrongIdException(id);
        }
        eventService.delete(id);
    }
}