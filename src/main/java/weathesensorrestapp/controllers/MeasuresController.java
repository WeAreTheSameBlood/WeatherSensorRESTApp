package weathesensorrestapp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import weathesensorrestapp.dto.MeasurementsDTO;
import weathesensorrestapp.models.Measurement;
import weathesensorrestapp.servises.MeasurementsService;
import weathesensorrestapp.util.MeasureExceptions.MeasureErrorIncorrectFields;
import weathesensorrestapp.util.ErrorResponse;
import weathesensorrestapp.util.MeasureExceptions.MeasureErrorIncorrectSensorNameException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasuresController {

    private final MeasurementsService measuresService;
    private final ModelMapper mapper;

    @Autowired
    public MeasuresController(MeasurementsService measuresService, ModelMapper mapper) {
        this.measuresService = measuresService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<MeasurementsDTO> getAllMeasures() {
        return convertWithMapper(measuresService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasure(@RequestBody @Valid MeasurementsDTO measurementsDTO,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for ( FieldError error : errors ) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasureErrorIncorrectFields(errorMsg.toString());
        }
        measuresService.addMeasure(convertWithMapper(measurementsDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount(@RequestParam(required = false) String name) {
        System.out.println(name);
        if (name == null) throw new MeasureErrorIncorrectSensorNameException("Sensor name should be named!");
        return measuresService.getRainyDaysCountForSensor(name);
    }

    private Measurement convertWithMapper(MeasurementsDTO measurementsDTO) {
        return mapper.map(measurementsDTO, Measurement.class);
    }

    private List<MeasurementsDTO> convertWithMapper(List<Measurement> measurementList) {
        List<MeasurementsDTO> DTOList = new ArrayList<>();
        measurementList.forEach(mL -> DTOList.add(mapper.map(mL, MeasurementsDTO.class)));
//        for (Measurement measure : measurementList){
//            DTOList.add(mapper.map(measure, MeasurementsDTO.class));
//        }
        return DTOList;
    }

    @ExceptionHandler({MeasureErrorIncorrectSensorNameException.class,
                       MeasureErrorIncorrectFields.class})
    private ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}