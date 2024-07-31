package study.arduino.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.arduino.entity.ArduinoData;

public interface ArduinoDataRepository extends JpaRepository<ArduinoData,Long> {
    ArduinoData findByData(String number);
}
