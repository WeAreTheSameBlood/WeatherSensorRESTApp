package sensorrestserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sensorrestserver.models.Sensor;

@Repository
public interface SensorRepo extends JpaRepository<Sensor, Integer> {

    boolean existsByNameEquals(String name);
}
