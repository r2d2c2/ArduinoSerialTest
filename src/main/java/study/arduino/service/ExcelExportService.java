package study.arduino.service;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.arduino.entity.ArduinoData;
import study.arduino.repository.ArduinoDataRepository;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ExcelExportService {

    @Autowired
    private ArduinoDataRepository arduinoDataRepository;

    public ByteArrayInputStream exportDataToExcel() throws IOException {
        List<ArduinoData> entities = arduinoDataRepository.findAll();
        // 데이터베이스 리스트로 저장
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Data");//엑셀 파일이름

            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Column1");// 엑셀 헤더
            headerRow.createCell(1).setCellValue("Column2");
            // 열의 가장 첫번째 행에 셀을 만들어서 값을 넣어줌

            int rowIdx = 1;//엑셀 헤더가 있어 1부터
            for (ArduinoData entity : entities) {//모든 리스트 엑셀로 저장
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(entity.getData());
                row.createCell(1).setCellValue(entity.getDatadate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                // 타입을 나름 알아서 설정해 주지만 date 타입은 format을 해줘야 함
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}