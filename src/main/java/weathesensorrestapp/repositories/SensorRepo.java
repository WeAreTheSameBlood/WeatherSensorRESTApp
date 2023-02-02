package weathesensorrestapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weathesensorrestapp.models.Sensor;

import java.util.Optional;

@Repository
public interface SensorRepo extends JpaRepository<Sensor, Integer> {

    boolean existsByNameEquals(String name);

    Optional<Sensor> findByName(String name);
}