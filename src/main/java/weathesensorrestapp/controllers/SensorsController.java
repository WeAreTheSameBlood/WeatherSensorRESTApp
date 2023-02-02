package weathesensorrestapp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
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
import weathesensorrestapp.util.SensorExceptions.SensorNameNotAvailableException;
import weathesensorrestapp.util.SensorExceptions.SensorNotCreatedException;
import weathesensorrestapp.util.SensorExceptions.SensorNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorsController(SensorService sensorService,
                             ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<Sensor> getSensors(){
        return sensorService.findAll();
    }

    @GetMapping("/{id}")
    public Sensor getOneSensor(@PathVariable("id") int id) {
        return sensorService.findById(id);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> createNewSensor(@RequestBody @Valid SensorDTO sensorDTO,
                                                      BindingResult bindingResult){
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

        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(SensorNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(
                "Sensor with this id not found.",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
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
