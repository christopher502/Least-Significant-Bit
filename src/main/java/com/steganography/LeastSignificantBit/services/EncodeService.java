package com.steganography.LeastSignificantBit.services;

import com.steganography.LeastSignificantBit.utils.SteganographyUtils;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.stream.Collectors;

public class EncodeService {
    private Integer currentMessageIndex = 0;

    /**
     * Method used to encode a message into a buffered image
     *
     * @param image         in which message will be encrypted
     * @param messageBinary message to be encrypted
     * @param bitsNumber    bits number to per channel to be used
     * @return buffered image with updated RGBA values
     */

    public BufferedImage encodeMessage(BufferedImage image, String messageBinary, Integer bitsNumber) {
        int widthLimit = image.getWidth();
        int heightLimit = image.getHeight();

        for (int width = 0; width < widthLimit; width++) {
            for (int height = 0; height < heightLimit; height++) {
                int imageRGB = image.getRGB(width, height);

                List<StringBuilder> rgbaChannelsBinary = SteganographyUtils.extractRgbaChannels(imageRGB);
                List<Integer> updatedChannels = updateRgbaChannels(rgbaChannelsBinary, messageBinary, bitsNumber);

                int modifiedRGB = buildModifiedRGB(updatedChannels);
                image.setRGB(width, height, modifiedRGB);

                if (isMessageCompletelyEncoded(messageBinary))
                    return image;
            }
        }
        return image;
    }

    /**
     * Method used to update RGBA bits values
     *
     * @param rgbaChannelsBinary initial rgba values
     * @param message            message to be encoded
     * @param bitsNumber         number of bits to be used
     * @return updated RBGA bits values
     */
    private List<Integer> updateRgbaChannels(List<StringBuilder> rgbaChannelsBinary, String message, Integer bitsNumber) {
        int channelSize = rgbaChannelsBinary.size();
        int messageLength = message.length();

        for (int i = 0; i < channelSize && currentMessageIndex < messageLength; i++) {
            StringBuilder channel = rgbaChannelsBinary.get(i);
            int startIndex = channel.length() - bitsNumber;

            for (int index = startIndex; index < channel.length() && currentMessageIndex < messageLength; index++) {
                channel.setCharAt(index, message.charAt(currentMessageIndex++));
            }
        }

        return rgbaChannelsBinary.stream()
                .map(value -> Integer.parseInt(value.toString(), 2))
                .collect(Collectors.toList());
    }

    /**
     * Method used to build single pixel RGBA
     *
     * @param updatedChannels list with decimal values for (A,R,G,B)
     * @return pixel value
     */
    private static int buildModifiedRGB(List<Integer> updatedChannels) {
        return (updatedChannels.get(0) << 24) | (updatedChannels.get(1) << 16) |
                (updatedChannels.get(2) << 8) | updatedChannels.get(3);
    }

    /**
     * Method to check if message was fully parsed and encoded
     *
     * @param messageBinary message that is encoded
     * @return true | false
     */
    private boolean isMessageCompletelyEncoded(String messageBinary) {
        return currentMessageIndex == messageBinary.length();
    }
}