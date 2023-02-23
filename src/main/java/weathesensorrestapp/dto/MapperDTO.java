package weathesensorrestapp.dto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MapperDTO {

    private final ModelMapper mapper;

    @Autowired
    public MapperDTO(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public <T> T mappingData(Object obj, Class<T> outputType) {
        return mapper.map(obj, outputType);
    }

    public <E, T> List<T> mappingData(List<E> listObj, Class<T> outputType) {
        List<T> outputList = new ArrayList<>();
        listObj.forEach(obj -> outputList.add(mapper.map(obj, outputType)));

        return outputList;
    }
}