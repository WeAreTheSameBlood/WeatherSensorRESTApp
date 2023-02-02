package weathesensorrestapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementsDTO {

    @NotNull(message = "Sensor in Measurement.class is NULL")
    private SensorDTO sensor;

    @NotNull
    @Min(value = -100, message = "Temperature should be between -100...+100°C")
    @Max(value = 100, message = "Temperature should be between -100...+100°C")
    private float temperatureCelsius;

    @NotNull
    private boolean raining;
}
