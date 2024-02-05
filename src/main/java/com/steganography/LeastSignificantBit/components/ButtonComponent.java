package com.steganography.LeastSignificantBit.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;

public class ButtonComponent extends Div {
    private Button primaryButton;
    public ButtonComponent(String text) {
        primaryButton = new Button(text);
        primaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(primaryButton);
    }

    public Button getPrimaryButton() {
        return primaryButton;
    }
}
