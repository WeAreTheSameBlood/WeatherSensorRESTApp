package weathesensorrestapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "measurements")
@Getter
@Setter
@NoArgsConstructor
public class Measurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measure_id")
    private int measureId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    @NotNull(message = "Sensor in Measurement.class is NULL")
    private Sensor sensor;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @Column(name = "temperature_celsius")
    @Min(value = -100, message = "Temperature should be between -100...+100°C")
    @Max(value = 100, message = "Temperature should be between -100...+100°C")
    private float temperatureCelsius;

    @Column(name = "raining")
    private boolean raining;

    public Measurement(Sensor sensor, LocalDateTime updateTime, float temperatureCelsius, boolean raining) {
        this.sensor = sensor;
        this.updateTime = updateTime;
        this.temperatureCelsius = temperatureCelsius;
        this.raining = raining;
    }
}