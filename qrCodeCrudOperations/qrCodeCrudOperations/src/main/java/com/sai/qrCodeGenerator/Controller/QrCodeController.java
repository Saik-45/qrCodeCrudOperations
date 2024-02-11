package com.sai.qrCodeGenerator.Controller;

import com.sai.qrCodeGenerator.Entity.QrCodeEntity;
import com.sai.qrCodeGenerator.Service.QrCodeService;
import com.sai.qrCodeGenerator.Service.QrCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/qr")
public class QrCodeController {

    @Autowired
    private QrCodeServiceImpl qrCodeService;

    @GetMapping("/home")
    private String home(){
        return "Home Of Application...";
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateQrCode(@RequestBody @Validated QrCodeEntity qrCodeEntity) {
        try {
            byte[] qrCodeImage = qrCodeService.generateQrCodeImage(qrCodeEntity);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCodeImage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadData(@RequestBody @Validated QrCodeEntity qrCodeEntity) {
        qrCodeService.uploadData(qrCodeEntity);
        return ResponseEntity.ok("Data Uploaded Successfully In Db...");
    }

    @GetMapping(value = "/get/{qrId}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getQrCodeImageByQrId(@PathVariable String qrId) {
        QrCodeEntity qrCodeEntity = qrCodeService.getDataByQrId(qrId);
        if (qrCodeEntity != null) {
            byte[] qrCodeImage = qrCodeService.generateQrCodeImage(qrCodeEntity);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCodeImage);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "QR code with ID " + qrId + " not found.");
        }
    }

    @PostMapping("/update/{qrId}")
    public ResponseEntity<String> updateByQrId(@PathVariable String qrId, @RequestBody @Validated QrCodeEntity qrCodeEntity) {
        qrCodeService.updateByQrId(qrId, qrCodeEntity);
        return ResponseEntity.ok("QR code with ID " + qrId + " updated successfully...");
    }

    @DeleteMapping("/delete/{qrId}")
    public ResponseEntity<String> deleteByQrId(@PathVariable String qrId) {
        qrCodeService.deleteByQrId(qrId);
        return ResponseEntity.ok("QR code with ID " + qrId + " deleted successfully...");
    }

    @PatchMapping("/patch/{qrId}")
    public ResponseEntity<String> patchByQrId(@PathVariable String qrId, @RequestBody Map<String, Object> updates) {
        qrCodeService.patchByQrId(qrId, updates);
        return ResponseEntity.ok("QR code with ID " + qrId + " Partially Updated successfully...");
    }

}
