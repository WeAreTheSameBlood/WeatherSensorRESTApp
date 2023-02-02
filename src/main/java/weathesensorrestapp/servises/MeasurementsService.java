package weathesensorrestapp.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weathesensorrestapp.models.Measurement;
import weathesensorrestapp.models.Sensor;
import weathesensorrestapp.repositories.MeasurementsRepo;
import weathesensorrestapp.repositories.SensorRepo;
import weathesensorrestapp.util.MeasureExceptions.MeasureErrorIncorrectSensorNameException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MeasurementsService {

    private final MeasurementsRepo measureRepo;
    private final SensorRepo sensorRepo;

    @Autowired
    public MeasurementsService(MeasurementsRepo measureRepo, SensorRepo sensorRepo) {
        this.measureRepo = measureRepo;
        this.sensorRepo = sensorRepo;
    }

    public List<Measurement> findAll() {
        return measureRepo.findAll();
    }

    @Transactional
    public void addMeasure(Measurement measurement) {
        measurement.setUpdateTime(LocalDateTime.now());
        measurement.setSensor(sensorRepo
                .findByName(measurement.getSensor().getName())
                .orElseThrow(() -> new MeasureErrorIncorrectSensorNameException("Sensor with this name NOT FOND!")));

        measureRepo.save(measurement);
    }

    public int getRainyDaysCountForSensor(String sensorName) {
        return measureRepo.findAllBySensorAndRainingTrue(sensorRepo
                        .findByName(sensorName)
                        .orElseThrow(() -> new MeasureErrorIncorrectSensorNameException("Sensor with this name NOT FOND!")))
                .size();
    }
}