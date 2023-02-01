package sensorrestserver.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
    private Sensor sensor;

    @Column(name = "update_time")
    @NotEmpty(message = "Update time not should be empty")
    private LocalDateTime updateTime;

    @Column(name = "temperature_celsius")
    @NotEmpty(message = "Temperature not should be empty")
    private float temperatureCelsius;

    @Column(name = "raining")
    @NotEmpty(message = "Raining not should be empty")
    private boolean raining;

    public Measurement(Sensor sensor, LocalDateTime updateTime, float temperatureCelsius, boolean raining) {
        this.sensor = sensor;
        this.updateTime = updateTime;
        this.temperatureCelsius = temperatureCelsius;
        this.raining = raining;
    }
}
