package com.sai.qrCodeGenerator.Service;

import com.sai.qrCodeGenerator.Entity.QrCodeEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface QrCodeService {
    byte[] generateQrCodeImage(QrCodeEntity qrCodeEntity);
    void uploadData(QrCodeEntity qrCodeEntity);
    QrCodeEntity getDataByQrId(String qrId);
    void updateByQrId(String qrId, QrCodeEntity qrCodeEntity);
    void deleteByQrId(String qrId);
    void patchByQrId(String qrId, Map<String, Object> updates);
}
