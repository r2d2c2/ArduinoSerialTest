package study.arduino.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Getter
@Setter
public class ArduinoData {
    @Id@GeneratedValue
    private Long id;
    private String data;
    private LocalDateTime datadate;
}
