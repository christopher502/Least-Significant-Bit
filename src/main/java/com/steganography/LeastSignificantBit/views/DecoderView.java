package com.steganography.LeastSignificantBit.views;

import com.steganography.LeastSignificantBit.components.*;
import com.steganography.LeastSignificantBit.services.SteganographyService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

@Route("decoder")
public class DecoderView extends VerticalLayout {
    private UploadComponent uploadComponent;
    private ComboBoxComponent comboBoxComponent;
    private ButtonComponent decodeButtonComponent;

    public DecoderView() {
        initializePageLayout();
        addDecodeButtonHandler();
    }

    private void initializePageLayout() {
        setHeightFull();
        setWidthFull();
        setAlignItems(Alignment.CENTER);

        uploadComponent = new UploadComponent();
        uploadComponent.setUploadComponentWidth("350px");

        comboBoxComponent = new ComboBoxComponent("Number of bits used to encode");
        comboBoxComponent.setComboBoxWidth("350px");

        decodeButtonComponent = new ButtonComponent("DECODE");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(comboBoxComponent, uploadComponent, decodeButtonComponent);

        Div mainBlock = new Div();
        mainBlock.add(verticalLayout);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(mainBlock);
        horizontalLayout.setHeightFull();
        horizontalLayout.setAlignItems(Alignment.CENTER);

        this.add(horizontalLayout);
    }

    private void addDecodeButtonHandler() {
        Button primaryButton = decodeButtonComponent.getPrimaryButton();
        primaryButton.addClickListener(event -> {
            try {
                if (comboBoxComponent.getComboBox().getValue() == null || comboBoxComponent.getComboBox().getValue().isEmpty()) {
                    showErrorNotification(primaryButton, "Bits number cannot be empty");
                } else if (uploadComponent.getMemoryBuffer().getInputStream().available() == 0) {
                    showErrorNotification(primaryButton, "Upload an image!");
                } else {
                    SteganographyService steganographyService = new SteganographyService();

                    String decodeMessage = steganographyService.decodeMessage(
                            readBufferedImage(),
                            Integer.valueOf(comboBoxComponent.getComboBox().getValue())
                    );
                    DialogComponent dialogComponent = new DialogComponent(decodeMessage);
                    dialogComponent.getDialog().open();

                    uploadComponent.getUpload().clearFileList();
                    comboBoxComponent.getComboBox().setValue("");
                }
            } catch (Exception exception) {
                System.out.println(exception);
                System.out.println(exception.getMessage());
                showErrorNotification(primaryButton, "We were unable to decode the hidden message");
            }
        });
    }

    private void showErrorNotification(Button primaryButton, String message) {
        Notification notification = NotificationWarningComponent.show(message);
        notification.addDetachListener(detachEvent -> primaryButton.setEnabled(true));
        uploadComponent.getUpload().clearFileList();
        comboBoxComponent.getComboBox().setValue("");
    }

    private BufferedImage readBufferedImage() {
        try {
            BufferedImage bufferedImage = ImageIO.read(uploadComponent.getMemoryBuffer().getInputStream());
            return bufferedImage;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
