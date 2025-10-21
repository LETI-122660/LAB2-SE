package iscteiul.qrcodes.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import iscteiul.qrcodes.QrcodesService;
import org.springframework.beans.factory.annotation.Autowired;

@Route("qrcode")
@PageTitle("QR Code Generator")
@Menu(order = 4, icon = "vaadin:qrcode", title = "QR Codes")
public class QrView extends VerticalLayout {

    private final QrcodesService qrCodeService;
    private Image qrCodeImage;

    @Autowired
    public QrView(QrcodesService qrCodeService) {
        this.qrCodeService = qrCodeService;

        TextArea contentField = new TextArea("QR Code Content");
        contentField.setPlaceholder("Enter text or URL to encode");
        contentField.setWidth("300px");
        contentField.setHeight("100px");

        Button generateButton = new Button("Generate QR Code", event -> {
            String content = contentField.getValue();
            if (content == null || content.trim().isEmpty()) {
                Notification.show("Please enter content for the QR code", 3000, Notification.Position.MIDDLE);
                return;
            }

            // Gera QR Code com dimensões fixas 300x300
            String qrCodeData = qrCodeService.generateQRCodeImage(content, 300, 300);

            if (qrCodeData.startsWith("data:image/png;base64,")) {
                if (qrCodeImage != null) {
                    remove(qrCodeImage);
                }
                qrCodeImage = new Image(qrCodeData, "Generated QR Code");
                qrCodeImage.setWidth("300px");
                qrCodeImage.setHeight("300px");
                add(qrCodeImage);

                // Salvar no banco de dados
                qrCodeService.save(new iscteiul.qrcodes.Qrcodes(content));

                Notification.show("QR Code generated successfully!", 3000, Notification.Position.MIDDLE);
            } else {
                Notification.show(qrCodeData, 5000, Notification.Position.MIDDLE);
            }
        });

        // Botão para limpar
        Button clearButton = new Button("Clear", event -> {
            contentField.clear();
            if (qrCodeImage != null) {
                remove(qrCodeImage);
                qrCodeImage = null;
            }
        });

        add(contentField, generateButton, clearButton);
        setAlignItems(Alignment.CENTER);
        setSpacing(true);
        setPadding(true);
    }
}
