package by.diomov.event.mapper;

import by.diomov.event.dto.ParametersEventQueryDTO;
import by.diomov.event.parameter.ParametersEventQuery;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ParametersEventQueryServiceMapper {
    private final ModelMapper mapper;

    public ParametersEventQuery convertToEntity(ParametersEventQueryDTO parameters) {
        return mapper.map(parameters, ParametersEventQuery.class);
    }
}