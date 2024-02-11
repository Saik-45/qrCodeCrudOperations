package com.sai.qrCodeGenerator.Repository;

import com.sai.qrCodeGenerator.Entity.QrCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCodeEntity, Long> {
    QrCodeEntity findByQrId(String qrId);
}
