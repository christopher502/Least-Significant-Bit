package com.steganography.backend.services;

import com.steganography.backend.utils.SteganographyUtils;

import java.awt.image.BufferedImage;

public class DecodeService {
    /**
     * Method used to decode message from a buffered image
     *
     * @param bufferedImage buffered image to be decoded
     * @param bitsNumber    number of bits used on encryption
     * @return
     */
    public String decodeMessage(BufferedImage bufferedImage, Integer bitsNumber) {
        String extractedBinaryMessage = extractBinaryMessage(bufferedImage, bitsNumber);
        return parseBinaryString(extractedBinaryMessage);
    }

    /**
     * Method to convert binary values intro string
     *
     * @param binaryString string that contains binary values
     * @return human readable text values
     */
    public static String parseBinaryString(String binaryString) {
        StringBuilder result = new StringBuilder();
        try {
            for (int i = 0; i < binaryString.length(); i += 8) {
                String binaryChar = binaryString.substring(i, i + 8);
                char character = (char) Integer.parseInt(binaryChar, 2);
                result.append(character);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result.toString();
    }

    /**
     * Method to get encoded message length
     *
     * @param bufferedImage buffered image which stores a message
     * @param bitsNumber    numbers of bits used while encoding
     * @return decimal values of message length
     */
    private Integer getMessageLength(BufferedImage bufferedImage, Integer bitsNumber) {
        int totalPixels = (int) Math.ceil(8 / (4 * bitsNumber));
        StringBuilder messageLengthBinary = getBinaryValues(bufferedImage, bitsNumber, totalPixels);

        return Integer.parseInt(messageLengthBinary.substring(0, 8), 2);
    }

    /**
     * Method to extract binary representation of the message
     *
     * @param bufferedImage buffered image which stores a message
     * @param bitsNumber    numbers of bits used while encoding
     * @return binary representation of the message
     */

    private String extractBinaryMessage(BufferedImage bufferedImage, Integer bitsNumber) {
        Integer messageLength = getMessageLength(bufferedImage, bitsNumber);
        int totalPixels = (int) Math.ceil((8 * (messageLength + 1)) / (4 * bitsNumber)) + 1;
        StringBuilder extractedMessageBinary = getBinaryValues(bufferedImage, bitsNumber, totalPixels);

        return extractedMessageBinary.substring(8, ((messageLength + 1) * 8));
    }

    /**
     * Common method to extract binary values from buffered image
     *
     * @param bufferedImage buffered image which stores a message
     * @param bitsNumber    numbers of bits used while encoding
     * @param totalPixels   numbers of pixels to be parsed
     * @return string that contains binary representation of values stored in the range of @totalPixels
     */
    private StringBuilder getBinaryValues(BufferedImage bufferedImage, Integer bitsNumber, Integer totalPixels) {
        StringBuilder binaryValues = new StringBuilder();
        try {
            OUTER:
            for (int width = 0, pixelsCounter = 1; width < bufferedImage.getWidth(); width++)
                for (int height = 0; height < bufferedImage.getHeight(); height++) {
                    SteganographyUtils.extractRgbaChannels(bufferedImage.getRGB(width, height))
                            .forEach(sb -> binaryValues.append(sb.substring(sb.length() - bitsNumber)));

                    if (pixelsCounter++ == totalPixels) break OUTER;
                }
        } catch(Exception exception) {
            exception.printStackTrace();
        }
        return binaryValues;
    }
}