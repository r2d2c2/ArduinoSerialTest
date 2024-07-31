package study.arduino.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.arduino.entity.ArduinoData;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ArduinoDataRepositoryTest {
    @Autowired
    private ArduinoDataRepository arduinoDataRepository;
    @Test
    public void save(){
        //given

        //1~100 까지 가상의 데이터 저장
        for (int i = 1; i < 100; i++) {
            arduinoDataRepository.save(ArduinoData.builder().data(i+"").datadate(LocalDateTime.now()).build());
        }
        //when
        ArduinoData fine=arduinoDataRepository.findByData("10");

        //then
        assertEquals(fine.getData(),"10");
    }
}