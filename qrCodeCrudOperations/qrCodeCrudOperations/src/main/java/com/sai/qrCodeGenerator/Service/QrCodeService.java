package com.sai.qrCodeGenerator.Service;

import com.sai.qrCodeGenerator.Entity.QrCodeEntity;
import org.springframework.stereotype.Service;

@Service
public interface QrCodeService {
    byte[] generateQrCodeImage(QrCodeEntity qrCodeEntity);
    void uploadData(QrCodeEntity qrCodeEntity);
    QrCodeEntity getDataByQrId(String qrId);
}
