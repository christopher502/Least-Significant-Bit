package com.steganography.LeastSignificantBit.utils;

import java.util.List;
import java.util.stream.Collectors;

public class SteganographyUtils {
    /**
     * Method to extract RGBA channels values from a pixel
     *
     * @param pixel pixel data
     * @return a list of 4 elements : (A,R,G,B) binary representation in string format
     */
    public static List<StringBuilder> extractRgbaChannels(int pixel) {
        return List.of((pixel >> 24) & 0xFF, (pixel >> 16) & 0xFF, (pixel >> 8) & 0xFF, pixel & 0xFF)
                .stream()
                .map(value -> new StringBuilder(String
                        .format("%8s", Integer.toBinaryString(value))
                        .replace(' ', '0')))
                .collect(Collectors.toList());
    }
}