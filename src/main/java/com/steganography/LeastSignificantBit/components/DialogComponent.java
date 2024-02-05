package com.steganography.LeastSignificantBit.components;

import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.awt.image.BufferedImage;

public class DialogComponent extends Div {

    private Span status;
    private ConfirmDialog dialog;

    public DialogComponent(String decodedMessage) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        status = new Span();
        status.setVisible(false);

        dialog = new ConfirmDialog();
        dialog.setHeader("Decoded message");
        dialog.setText("Your decoded message is: " + decodedMessage);

        dialog.setConfirmText("OK");

        add(layout);
    }
    public ConfirmDialog getDialog() {
        return dialog;
    }

    private void startDownloading(BufferedImage bufferedImage) {
    }
}