// src/main/java/com/example/Emails/EmailsView.java
package com.example.Emails.ui;

import com.example.Emails.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("emails")
public class EmailsView extends VerticalLayout {

    @Autowired
    public EmailsView(EmailsService emailsService) {
        TextField fromField = new TextField("Sender Email");
        PasswordField passwordField = new PasswordField("Sender Password");
        TextField toField = new TextField("Recipient Email");
        TextField subjectField = new TextField("Subject");
        TextArea bodyField = new TextArea("Message");

        Button sendButton = new Button("Send Email", event -> {
            String result = emailsService.sendEmail(
                    fromField.getValue(),
                    toField.getValue(),
                    subjectField.getValue(),
                    bodyField.getValue(),
                    passwordField.getValue()
            );
            Notification.show(result, 5000, Notification.Position.MIDDLE);
        });

        add(fromField, passwordField, toField, subjectField, bodyField, sendButton);
    }
}
