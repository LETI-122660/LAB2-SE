package com.example.pdf.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("pdfview")
@PageTitle("Pdf")
@Menu(order = 3, icon = "vaadin:clipboard-check", title = "Pdf")
public class PdfView extends VerticalLayout {

    public PdfView() {
        Button exportButton = new Button("Exportar PDF", event -> {
            Notification.show("PDF gerado! Clique no link para baixar.");
        });

        // CORREÇÃO: Codificar o parâmetro data para URL
        String encodedData = java.net.URLEncoder.encode("Exemplo de dados", java.nio.charset.StandardCharsets.UTF_8);
        Anchor downloadLink = new Anchor("/api/pdf/report?data=" + encodedData, "Baixar PDF");
        downloadLink.getElement().setAttribute("target", "_blank");

        add(exportButton, downloadLink)
    }
}