package sensorrestserver.servises;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sensorrestserver.models.Sensor;
import sensorrestserver.repositories.SensorRepo;
import sensorrestserver.util.SensorExceptions.SensorNameNotAvailableException;
import sensorrestserver.util.SensorExceptions.SensorNotFoundException;

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

    public Sensor findById(int id) {
        Sensor sensor = sensorRepo.findById(id).orElseThrow(SensorNotFoundException::new);
//        System.out.println(sensor.getMeasurementList());
        return sensor;
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