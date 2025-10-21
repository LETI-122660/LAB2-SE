package iscteiul.qrcodes;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Service
public class QrcodesService {

    private final QrcodesRepository repository;

    @Autowired
    public QrcodesService(QrcodesRepository repository) {
        this.repository = repository;
    }

    public Qrcodes save(Qrcodes qrcodes) {
        return repository.save(qrcodes);
    }

    public Slice<Qrcodes> getAll(Pageable pageable) {
        return repository.findAllBy(pageable);
    }

    public Qrcodes getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Método para gerar QR Code
    public String generateQRCodeImage(String content, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);

            byte[] qrCodeBytes = outputStream.toByteArray();
            String base64QRCode = Base64.getEncoder().encodeToString(qrCodeBytes);

            return "data:image/png;base64," + base64QRCode;

        } catch (WriterException | IOException e) {
            return "Error generating QR Code: " + e.getMessage();
        }
    }

    // Método adicional para gerar e salvar QR Code
    public String generateAndSaveQRCode(String content, int width, int height) {
        // Salva o conteúdo no banco de dados
        Qrcodes qrEntity = new Qrcodes(content);
        save(qrEntity);

        // Gera a imagem do QR Code
        return generateQRCodeImage(content, width, height);
    }
}
