package com.steganography.ui.views;

import com.steganography.backend.services.SteganographyService;
import com.steganography.backend.utils.SteganographyUtils;
import com.steganography.ui.components.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@PageTitle("Encode")
@Route("encode")
public class EncoderView extends VerticalLayout {
    private TextAreaComponent textAreaComponent;
    private UploadComponent uploadComponent;
    private ComboBoxComponent comboBoxComponent;
    private ButtonComponent encodeButtonComponent;

    public EncoderView() {
        initializePageLayout();
        addEncodeButtonHandler();
    }

    private void initializePageLayout() {
        setHeightFull();
        setWidthFull();
        setAlignItems(Alignment.CENTER);

        textAreaComponent = new TextAreaComponent();
        textAreaComponent.setTextAreaSize("350px", "100px");

        uploadComponent = new UploadComponent();
        uploadComponent.setUploadComponentWidth("350px");

        comboBoxComponent = new ComboBoxComponent("Number of bits to be changed:");
        comboBoxComponent.setComboBoxWidth("350px");

        encodeButtonComponent = new ButtonComponent("ENCODE");

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(textAreaComponent, comboBoxComponent, uploadComponent, encodeButtonComponent);

        Div mainBlock = new Div();
        mainBlock.add(verticalLayout);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(mainBlock);
        horizontalLayout.setHeightFull();
        horizontalLayout.setAlignItems(Alignment.CENTER);

        this.add(horizontalLayout);
    }

    private void addEncodeButtonHandler() {
        Button primaryButton = encodeButtonComponent.getPrimaryButton();
        primaryButton.addClickListener(clickEvent -> {
            primaryButton.setEnabled(false);

            try {
                if (textAreaComponent.getTextArea().getValue().isEmpty()) {
                    showErrorNotification(primaryButton, "Message cannot be empty");
                } else if (comboBoxComponent.getComboBox().getValue() == null || comboBoxComponent.getComboBox().getValue().isEmpty()) {
                    showErrorNotification(primaryButton, "Bits number cannot be empty");
                } else if (uploadComponent.getMemoryBuffer().getInputStream().available() == 0) {
                    showErrorNotification(primaryButton, "Upload an image!");
                } else {
                    SteganographyService steganographyService = new SteganographyService();

                    BufferedImage encodedImage = steganographyService.encodeMessage(
                            SteganographyUtils.readBufferedImage(uploadComponent),
                            Integer.parseInt(comboBoxComponent.getComboBox().getValue()),
                            textAreaComponent.getTextArea().getValue());

                    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                        ImageIO.write(encodedImage, "png", byteArrayOutputStream);

                        StreamResource resource = new StreamResource("image.png",
                                () -> new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

                        Anchor anchor = new Anchor(resource, "");
                        anchor.getElement().setAttribute("download", "encodedImage.png");
                        add(anchor);
                        anchor.getElement().executeJs("this.click()");

                        primaryButton.setEnabled(true);
                        uploadComponent.getUpload().clearFileList();
                        textAreaComponent.getTextArea().setValue("");
                        comboBoxComponent.getComboBox().setValue("");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }

    private void showErrorNotification(Button primaryButton, String message) {
        Notification notification = NotificationWarningComponent.show(message);
        notification.addDetachListener(detachEvent -> primaryButton.setEnabled(true));
    }
}