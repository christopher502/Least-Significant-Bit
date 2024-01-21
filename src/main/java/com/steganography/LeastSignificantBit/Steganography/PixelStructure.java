package com.steganography.LeastSignificantBit.Steganography;

import lombok.Data;

import java.util.List;

@Data
public class PixelStructure {
    /*
        'R' 00000000
        'G' 00000000
        'B' 00000000
        'A' 00000000
     */
    private byte[][] channels = new byte[4][8];
    public PixelStructure(List<String> channelsBinary) {
        for(int index = 0; index < channelsBinary.size(); index++) {
            String binary = channelsBinary.get(index);

            for(int column = 0; column < channels[0].length; column++) {
                channels[index][column] = Byte.valueOf(String.valueOf(binary.charAt(column)));
            }
        }
    }

    public void printPixelStructure() {
        for(int i = 0; i < channels.length; i++) {
            for (int j = 0; j < channels[0].length; j++) {
                System.out.print(channels[i][j] + " ");
            }
            System.out.println();
        }
    }
}