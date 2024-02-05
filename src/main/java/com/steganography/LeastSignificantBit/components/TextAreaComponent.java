package com.steganography.LeastSignificantBit.components;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.TextArea;

public class TextAreaComponent extends Div {
    private TextArea textArea;
    public TextAreaComponent() {
        textArea = new TextArea();
        textArea.setWidthFull();
        textArea.setLabel("Message to be encoded");
        textArea.setValue("");
        add(textArea);
    }

    public void setTextAreaSize(String width, String height) {
        textArea.setWidth(width);
        textArea.setHeight(height);
    }

    public TextArea getTextArea() {
        return textArea;
    }

}
