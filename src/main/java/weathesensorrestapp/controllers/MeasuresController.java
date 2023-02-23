package weathesensorrestapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import weathesensorrestapp.dto.MapperDTO;
import weathesensorrestapp.dto.MeasurementsDTO;
import weathesensorrestapp.models.Measurement;
import weathesensorrestapp.servises.MeasurementsService;
import weathesensorrestapp.util.ErrorResponse;
import weathesensorrestapp.util.MeasureExceptions.MeasureErrorIncorrectFields;
import weathesensorrestapp.util.MeasureExceptions.MeasureErrorIncorrectSensorNameException;

import java.util.List;

@RestController
@RequestMapping("/measurements")
@CrossOrigin(origins = "http://localhost:4200")
public class MeasuresController {

    private final MeasurementsService measuresService;
    private final MapperDTO mapperDTO;

    @Autowired
    public MeasuresController(MeasurementsService measuresService, MapperDTO mapperDTO) {
        this.measuresService = measuresService;
        this.mapperDTO = mapperDTO;
    }

    @GetMapping
    public List<MeasurementsDTO> getAllMeasures() {
        return mapperDTO.mappingData(measuresService.findAll(), MeasurementsDTO.class);
    }

    @GetMapping("/bySensorName")
    public List<MeasurementsDTO> getMeasuresForSensor(
            @RequestParam(required = true) String sensorName) {
        return mapperDTO.mappingData(
                measuresService.findAllBySensorName(sensorName), MeasurementsDTO.class);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasure(
            @RequestBody @Valid MeasurementsDTO measurementsDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new MeasureErrorIncorrectFields(errorMsg.toString());
        }
        measuresService.addMeasure(mapperDTO.mappingData(measurementsDTO, Measurement.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public int getRainyDaysCount(
            @RequestParam(required = false) String name) {
        if (name == null)
            throw new MeasureErrorIncorrectSensorNameException("Sensor name should be named!");
        return measuresService.getRainyDaysCountForSensor(name);
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