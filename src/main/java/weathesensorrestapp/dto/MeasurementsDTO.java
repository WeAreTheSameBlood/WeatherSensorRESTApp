package sensorrestserver.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


public class MeasurementsDTO {

    @NotEmpty(message = "Sensor id not should be empty")
    @Size(min = 1, message = "Sensor id should be more than 0")
    private int sensorId;

    @NotEmpty(message = "Temperature not should be empty")
    private float temperatureCelsius;

    @NotEmpty(message = "Raining not should be empty")
    private boolean raining;
}
