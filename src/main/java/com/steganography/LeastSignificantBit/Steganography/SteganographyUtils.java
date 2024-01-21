package com.steganography.LeastSignificantBit.Steganography;

import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SteganographyUtils {

    /**
     * Method to create a pixels structure map
     * @param image the image to parse pixels
     * @return map that contains pixel Location and PixelStructure - (R,G,B,A) values
     */
    public static Map<Location, PixelStructure> createPixelStructureMap(BufferedImage image) {
        Map<Location, PixelStructure> channelsMap = new LinkedHashMap<>();

        for (int locationX = 0; locationX < image.getWidth(); locationX++) {
            for(int locationY = 0; locationY < image.getHeight(); locationY++) {
                int extractedPixel = image.getRGB(locationX, locationY);

                List<String> rgbaChannels = extractRgbaChannels(extractedPixel);
                channelsMap.put(
                        new Location(locationX, locationY),
                        new PixelStructure(rgbaChannels)
                );
            }
        }
        return channelsMap;
    }

    /**
     * Method to extract RGBA channels values from a pixel
     * @param pixel pixel data
     * @return a list of 4 elements : (R,G,B,A) binary representation in string format
     */
    private static List<String> extractRgbaChannels(int pixel) {
        return List.of((pixel >> 16) & 0xFF,(pixel >> 8) & 0xFF, pixel & 0xFF, (pixel >> 24) & 0xFF)
                .stream()
                .map(value -> String.format("%8s", Integer.toBinaryString(value)).replace(' ', '0'))
                .collect(Collectors.toList());
    }

    /**
     * Method to convert each character from a string into binary representation
     * @param message the text to be converted
     * @return a list with the binary representation of each character in the message
     */
    public static List<String> convertStringToBinary(String message) {
        List<String> messageBinary = new ArrayList<>();
        message.chars().forEach(character -> messageBinary.add(
                String.format("%8s", Integer.toBinaryString(character)).replace(' ', '0')
        ));
        return messageBinary;
    }

    /**
     * Method to convert binary value into decimal
     * @param data the 8 bits of binary value
     * @return decimal representation of binary value
     */
    public static int convertBinaryToDecimal(byte[] data) {
        String concatenatedBinaryValues = "";
        for(int i = 0; i < data.length; i++)
            concatenatedBinaryValues += data[i];

        return Integer.parseInt(concatenatedBinaryValues, 2);
    }

    /**
     * Method to create a buffered image
     * @param channels rgba channels data
     * @param width width of image
     * @param height width of image
     * @param type type of image
     * @return created BufferedImage object
     */
    public static BufferedImage createBufferedImage(Map<Location, PixelStructure> channels, int width, int height, int type) {
        BufferedImage image = new BufferedImage(width, height, type);

        channels.forEach( (location, pixelStructure) -> {
            int r = convertBinaryToDecimal(pixelStructure.getChannels()[0]);
            int g = convertBinaryToDecimal(pixelStructure.getChannels()[1]);
            int b = convertBinaryToDecimal(pixelStructure.getChannels()[2]);
            int a = convertBinaryToDecimal(pixelStructure.getChannels()[3]);

            image.setRGB(location.locationX(),
                    location.locationY(),
                    (a << 24) | (r << 16) | (g << 8) | b);
        });
        return image;
    }

    /**
     * Method to update rbga channels bits data
     * @param image image to extract pixels
     * @param messageBinary list of binary representation of each character from a string
     * @param nrOfBits number of bits per channel that will be updated
     * @return updated Map<Location, PixelStructure>
     */
    public static Map<Location, PixelStructure> updateRgbaChannelsData(BufferedImage image, List<String> messageBinary, int nrOfBits) {
        String combinedBinaryCharacters = messageBinary.stream().collect(Collectors.joining());
        Map<Location, PixelStructure> channelsMap = SteganographyUtils.createPixelStructureMap(image);

        int messageBitPositionCounter = 0;
        OUTER_LOOP : for (PixelStructure pixelStructure : channelsMap.values()) {
            byte[][] pixelChannels = pixelStructure.getChannels();

            for (int i = 0; i < pixelChannels.length; i++) {
                for( int j = nrOfBits; j > 0; j--) {
                    byte imageExtractedBit = pixelChannels[i][pixelChannels[0].length - j];
                    byte messageExtractedBit = Byte.valueOf(String.valueOf(combinedBinaryCharacters.charAt(messageBitPositionCounter++)));

                    if (imageExtractedBit != messageExtractedBit) {
                        pixelChannels[i][pixelChannels[0].length - j]  = messageExtractedBit;
                    }

                    if (messageBitPositionCounter == combinedBinaryCharacters.length())
                        break OUTER_LOOP;
                }
            }

        }
        return channelsMap;
    }
}
