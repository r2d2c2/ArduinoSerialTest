package study.arduino.controller;

import com.fazecast.jSerialComm.SerialPort;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import study.arduino.entity.ArduinoData;
import study.arduino.repository.ArduinoDataRepository;

import java.time.LocalDateTime;

@RestController
public class SerialController {
    @Autowired
    private ArduinoDataRepository arduinoDataRepository;
    private SerialPort serialPort;

    @PostConstruct
    public void init() {
        //엑셀 셈플 데이터
        for (int i = 1; i < 11; i++) {
            arduinoDataRepository.save(ArduinoData.builder().data(i+"").datadate(LocalDateTime.now()).build());
        }


        SerialPort[] ports = SerialPort.getCommPorts();
        if (ports.length > 0) {
            serialPort = ports[0];
            // 첫 번째 포트 선택(보통 이걸로 연결된다)
        } else {
            serialPort = SerialPort.getCommPort("COM14");
            // 기본 포트 선택(필요한 경우 장치 관리자로 가서 이름 붙붙)
        }

        serialPort.setBaudRate(9600);
        if(!serialPort.openPort())
            return;//연결된 하드 없음
        serialPort.openPort();//포트 열기

        //하드웨어 정보 보기
        if (serialPort != null) {
            String info= "System Port Name: " + serialPort.getSystemPortName() + "\n" +
                    "Descriptive Port Name: " + serialPort.getDescriptivePortName() + "\n" +
                    "Port Description: " + serialPort.getPortDescription();
            System.out.println("하드웨어 정보 보기 = " + info);
        }
        // 계속 데이터 읽기
        new Thread(this::loop).start();
        // loop 메소드 쓰레드 돌림(java8에는 이런 문접 없어는데?)
    }

    public void loop() {// 아두이노에서 시리얼 정보 계속 읽기
        while (true) {// 무한 루프라서 쓰레드 필수
            if (serialPort.bytesAvailable() > 0) {
                byte[] readBuffer = new byte[serialPort.bytesAvailable()];
                serialPort.readBytes(readBuffer, readBuffer.length);
                arduinoDataRepository.save(ArduinoData.builder().data(new String(readBuffer)).datadate(LocalDateTime.now()).build());
            }
        }
    }

    @GetMapping("/read-data")//시리얼 text 받기 test
    public String readData() {
        if (serialPort.bytesAvailable() > 0) {
            byte[] readBuffer = new byte[serialPort.bytesAvailable()];
            serialPort.readBytes(readBuffer, readBuffer.length);
            arduinoDataRepository.save(ArduinoData.builder().data(new String(readBuffer)).datadate(LocalDateTime.now()).build());
            return new String(readBuffer);
        }
        return "No data available";
    }
}