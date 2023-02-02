package weathesensorrestapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import weathesensorrestapp.models.Measurement;
import weathesensorrestapp.models.Sensor;

import java.util.Set;

@Repository
public interface MeasurementsRepo extends JpaRepository<Measurement, Integer> {

     Set<Measurement> findAllBySensorAndRainingTrue(Sensor sensor);
}