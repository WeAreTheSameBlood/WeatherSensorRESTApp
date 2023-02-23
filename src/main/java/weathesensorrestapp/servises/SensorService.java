package weathesensorrestapp.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import weathesensorrestapp.models.Sensor;
import weathesensorrestapp.repositories.SensorRepo;
import weathesensorrestapp.util.SensorExceptions.SensorNameNotAvailableException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepo sensorRepo;

    @Autowired
    public SensorService(SensorRepo sensorRepo) {
        this.sensorRepo = sensorRepo;
    }

    public List<Sensor> findAll(){
        return sensorRepo.findAll();
    }

    @Transactional
    public void save(Sensor sensor) {
        if (sensorRepo.existsByNameEquals(sensor.getName()))
            throw new SensorNameNotAvailableException("This name is already in use.");
        else {
            sensor.setRegistrationTime(LocalDateTime.now());
            sensorRepo.save(sensor);
        }
    }
}