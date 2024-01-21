package com.steganography.LeastSignificantBit;

import com.steganography.LeastSignificantBit.Steganography.Location;
import com.steganography.LeastSignificantBit.Steganography.PixelStructure;
import com.steganography.LeastSignificantBit.Steganography.SteganographyService;
import com.steganography.LeastSignificantBit.Steganography.SteganographyUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootApplication
public class LeastSignificantBitApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeastSignificantBitApplication.class, args);

		String imageLocation = "src/main/resources/experiment.png";

		SteganographyService steganographyService = new SteganographyService();

		try {
			BufferedImage encryptedImage = steganographyService.encodeMessage("5Hello", imageLocation, 8);
			ImageIO.write(encryptedImage, "png", new File("src/main/resources/test5.png"));

			String decryptedMessage = steganographyService.decodeMessage(encryptedImage, 8);
			System.out.println(decryptedMessage);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}
}
