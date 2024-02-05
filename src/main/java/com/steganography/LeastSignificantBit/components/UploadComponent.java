package com.steganography.LeastSignificantBit.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;

public class UploadComponent extends Div {
    private Upload upload;
    private MemoryBuffer memoryBuffer;
    public UploadComponent() {
        memoryBuffer = new MemoryBuffer();
        upload = new Upload(memoryBuffer);
        upload.setAcceptedFileTypes("application/png", ".png");


        upload.addFileRejectedListener(event -> {
            String errorMessage = event.getErrorMessage();

            Notification notification = Notification.show(errorMessage, 5000,
                    Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        });
        add(upload);
    }

    public void setUploadComponentWidth(String width) {
        upload.setWidth(width);
    }
    public MemoryBuffer getMemoryBuffer() {
        return memoryBuffer;
    }
    public Upload getUpload() {
        return upload;
    }
}
