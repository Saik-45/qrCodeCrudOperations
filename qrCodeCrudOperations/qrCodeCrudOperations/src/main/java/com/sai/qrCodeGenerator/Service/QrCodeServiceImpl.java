package com.sai.qrCodeGenerator.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.sai.qrCodeGenerator.Entity.QrCodeEntity;
import com.sai.qrCodeGenerator.Repository.QrCodeRepository;
import com.sai.qrCodeGenerator.Service.GenerateRandomQrIdService;
import com.sai.qrCodeGenerator.Service.QrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class QrCodeServiceImpl implements QrCodeService {

    @Autowired
    private QrCodeRepository qrCodeRepository;

    @Autowired
    private GenerateRandomQrIdService generateRandomQrIdService;

    @Override
    public byte[] generateQrCodeImage(QrCodeEntity qrCodeEntity) {
        // Generate QR code text based on the fields of the QrCodeEntity
        String qrCodeText = qrCodeEntity.getName() + "\n"
                + qrCodeEntity.getAddress() + "\n"
                + qrCodeEntity.getColor() + "\n"
                + qrCodeEntity.getPhoneNumber() + "\n"
                + qrCodeEntity.getQrId();

        if (StringUtils.isEmpty(qrCodeText)) {
            throw new IllegalArgumentException("QR Code Text cannot be empty");
        }

        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.MARGIN, 1);

            BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, 200, 200, hints);

            BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            BufferedImage resizedImage = resize(qrCodeImage, 200, 200);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "png", outputStream);

            return outputStream.toByteArray();
        } catch (IOException | WriterException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to generate QR code", e);
        }
    }

    private BufferedImage resize(BufferedImage img, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, img.getType());
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(img, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }

    @Override
    public void uploadData(QrCodeEntity qrCodeEntity) {
        String generatedQrId = generateRandomQrIdService.generateRandomQrId();
        qrCodeEntity.setQrId(generatedQrId);
        qrCodeRepository.save(qrCodeEntity);
    }

    @Override
    public QrCodeEntity getDataByQrId(String qrId) {
        QrCodeEntity qrCodeEntity = qrCodeRepository.findByQrId(qrId);
        if (qrCodeEntity == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "QR code with ID " + qrId + " not found.");
        }
        return qrCodeEntity;
    }

    @Override
    public void updateByQrId(String qrId, QrCodeEntity qrCodeEntity) {
        QrCodeEntity existingQrCode = qrCodeRepository.findByQrId(qrId);
        if (existingQrCode == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "QR code with ID " + qrId + " not found.");
        }
        // Update fields of existing QR code entity
        existingQrCode.setName(qrCodeEntity.getName());
        existingQrCode.setAddress(qrCodeEntity.getAddress());
        existingQrCode.setColor(qrCodeEntity.getColor());
        existingQrCode.setPhoneNumber(qrCodeEntity.getPhoneNumber());
        qrCodeRepository.save(existingQrCode);
    }

    @Override
    public void deleteByQrId(String qrId) {
        QrCodeEntity existingQrCode = qrCodeRepository.findByQrId(qrId);
        if (existingQrCode == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "QR code with ID " + qrId + " not found.");
        }
        qrCodeRepository.delete(existingQrCode);
    }

    @Override
    public void patchByQrId(String qrId, Map<String, Object> updates) {
        QrCodeEntity existingQrCode = qrCodeRepository.findByQrId(qrId);
        if (existingQrCode == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "QR code with ID " + qrId + " not found.");
        }
        // Apply updates from the map to the existing QR code entity
        for (Map.Entry<String, Object> entry : updates.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            switch (key) {
                case "name":
                    existingQrCode.setName((String) value);
                    break;
                case "address":
                    existingQrCode.setAddress((String) value);
                    break;
                case "color":
                    existingQrCode.setColor((String) value);
                    break;
                case "phoneNumber":
                    existingQrCode.setPhoneNumber((String) value);
                    break;
                // Add more cases for other fields as needed
                default:
                    throw new IllegalArgumentException("Invalid field: " + key);
            }
        }
        qrCodeRepository.save(existingQrCode);
    }



}
