package study.arduino.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.arduino.service.ExcelExportService;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
public class ExcelExportController {

    @Autowired
    private ExcelExportService excelExportService;

    @GetMapping("/export-excel")
    public ResponseEntity<InputStreamResource> exportToExcel() throws IOException {
        ByteArrayInputStream in = excelExportService.exportDataToExcel();
        //서비스에 만든 엑셀 호출
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=data.xlsx");
        //이거 엑셀 파일 이라고 http 설정
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(new InputStreamResource(in));//접속한 클라이언트에 파일 다운로드
    }
}