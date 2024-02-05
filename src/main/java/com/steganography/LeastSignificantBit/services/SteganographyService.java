package com.steganography.LeastSignificantBit.services;

import java.awt.image.BufferedImage;

public class SteganographyService {

    /**
     * Method used to encode a message into the buffered image
     *
     * @param bufferedImage buffered images used
     * @param bitsNumber    bits number used to encode
     * @param message       message to be encoded
     * @return new buffered image with encoded message into it
     */
    public BufferedImage encodeMessage(BufferedImage bufferedImage, int bitsNumber, String message) {
        message = createStringMessage(message);
        if (isEnoughPixels(bufferedImage, message, bitsNumber))
            return new EncodeService().encodeMessage(bufferedImage, message, bitsNumber);
        else
            throw new RuntimeException("Uploaded image cannot be used to encrypt message due to lack of pixels");
    }

    /**
     * Method to decode message from the buffered image
     *
     * @param bufferedImage buffered image used
     * @param bitsNumber    bits number used to encode
     * @return
     */
    public String decodeMessage(BufferedImage bufferedImage, Integer bitsNumber) {
        return new DecodeService().decodeMessage(bufferedImage, bitsNumber);
    }

    /**
     * Method used to adapt encryption message
     *
     * @param message message to be encrypted
     * @return decoded message from buffered image
     */
    private String createStringMessage(String message) {
        StringBuilder messageBinary = new StringBuilder();
        String messageLengthBinary = String.format("%8s", Integer.toBinaryString(message.length()))
                .replace(' ', '0');
        messageBinary.append(messageLengthBinary);

        message.chars().forEach(character -> messageBinary.append(
                String.format("%8s", Integer.toBinaryString(character)).replace(' ', '0')
        ));
        return messageBinary.toString();
    }

    /**
     * Method to check if buffered images contains enough pixels to store message
     *
     * @param image         buffered image which will be used to encrypt
     * @param messageBinary message to be encrypted
     * @param bitsNumber    bits number that will be used to encrypt
     * @return true | false
     */
    private boolean isEnoughPixels(BufferedImage image, String messageBinary, Integer bitsNumber) {
        return messageBinary.length() < ((image.getWidth() * image.getHeight()) / (4 * bitsNumber));
    }
}