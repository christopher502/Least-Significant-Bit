package com.steganography.LeastSignificantBit.components;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;

import java.util.List;

public class ComboBoxComponent extends Div {
    private ComboBox<String> comboBox;
    public ComboBoxComponent(String text) {
        this.setWidthFull();
        comboBox = new ComboBox<>(text);
        comboBox.setWidth(getMaxWidth());
        comboBox.setItems(List.of("1", "2", "3", "4", "5", "6", "7", "8"));
        add(comboBox);
    }

    public void setComboBoxWidth(String width) {
        comboBox.setWidth(width);
        this.setWidth(width);
    }

    public ComboBox<String> getComboBox() {
        return comboBox;
    }
}
