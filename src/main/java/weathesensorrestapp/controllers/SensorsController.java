package weathesensorrestapp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import weathesensorrestapp.dto.SensorDTO;
import weathesensorrestapp.models.Sensor;
import weathesensorrestapp.servises.SensorService;
import weathesensorrestapp.util.ErrorResponse;
import weathesensorrestapp.dto.MapperDTO;
import weathesensorrestapp.util.SensorExceptions.SensorNameNotAvailableException;
import weathesensorrestapp.util.SensorExceptions.SensorNotCreatedException;

import java.util.List;

@RestController
@RequestMapping("/sensors")
@CrossOrigin(origins = "http://localhost:4200")
public class SensorsController {

    private final SensorService sensorService;
    private final MapperDTO mapperDTO;

    @Autowired
    public SensorsController(SensorService sensorService,
                             MapperDTO mapperDTO) {
        this.sensorService = sensorService;
        this.mapperDTO = mapperDTO;
    }

    @GetMapping()
    public List<SensorDTO> getSensor() {
        return mapperDTO.mappingData(sensorService.findAll(), SensorDTO.class);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createNewSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError e : errors) {
                errorMsg.append(e.getField())
                        .append(" - ")
                        .append(e.getDefaultMessage())
                        .append(";");
            }
            throw new SensorNotCreatedException(errorMsg.toString());
        }

        sensorService.save(mapperDTO.mappingData(sensorDTO, Sensor.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler({SensorNotCreatedException.class, SensorNameNotAvailableException.class})
    private ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}