package com.steganography.LeastSignificantBit.Steganography;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
public class SteganographyService {

    public BufferedImage encodeMessage(String message, String imageLocation, int nrOfBits) throws Exception{
            BufferedImage bufferedImage = ImageIO.read(new File(imageLocation));
            List<String> messageBinary =   SteganographyUtils.convertStringToBinary(message);

            if (!isEnoughPixels(bufferedImage, messageBinary)) {
                throw new RuntimeException("Image have too few pixels to write all message");
            }

            Map<Location, PixelStructure> channelsMap = SteganographyUtils.updateRgbaChannelsData(
                    bufferedImage,
                    messageBinary,
                    nrOfBits);

            BufferedImage updatedBufferedImage = SteganographyUtils.createBufferedImage(
                    channelsMap,
                    bufferedImage.getWidth(),
                    bufferedImage.getHeight(),
                    BufferedImage.TYPE_INT_ARGB
            );
            return updatedBufferedImage;
    }

    public String decodeMessage(BufferedImage image, int nrOfBits) {
        Map<Location, PixelStructure> channels = SteganographyUtils.createPixelStructureMap(image);

        String binaryString = "";
        for (PixelStructure pixelStructure : channels.values()) {
            byte[][] ch = pixelStructure.getChannels();
            for(int i = 0 ; i < ch.length; i++) {
                for(int j = nrOfBits; j > 0; j--)
                    binaryString += ch[i][ch[0].length - j];
            }
        }

        String result = "";
        int messageLength = Integer.parseInt(binaryString.substring(8*0,(0+1)*8),2);


        for(int i  = 1; i < Integer.parseInt(String.valueOf((char)messageLength )) + 1 ; i++) {
            int a = Integer.parseInt(binaryString.substring(8*i,(i+1)*8),2);
            result += (char) a;
        }
        return result;
    }

    private boolean isEnoughPixels(BufferedImage image, List<String> messageBinary) {
        return messageBinary.size() * 8 > ( (image.getWidth() * image.getHeight()) / 4);
    }
}
