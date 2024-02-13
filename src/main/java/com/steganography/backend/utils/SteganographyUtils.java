package com.steganography.backend.utils;

import com.steganography.ui.components.UploadComponent;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

    /**
     * Method to read the image from the memory buffer attached to the upload component
     *
     * @param uploadComponent the upload component
     * @return buffered image
     */
    public static BufferedImage readBufferedImage(UploadComponent uploadComponent) {
        try {
            BufferedImage bufferedImage = ImageIO.read(uploadComponent.getMemoryBuffer().getInputStream());

            String filename = uploadComponent.getMemoryBuffer().getFileName();
            if (!filename.endsWith("png")) {
                BufferedImage converted = new BufferedImage(
                        bufferedImage.getWidth(),
                        bufferedImage.getHeight(),
                        BufferedImage.TYPE_INT_ARGB);
                converted.getGraphics().drawImage(bufferedImage, 0, 0, null);
                return converted;
            }
            return bufferedImage;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}