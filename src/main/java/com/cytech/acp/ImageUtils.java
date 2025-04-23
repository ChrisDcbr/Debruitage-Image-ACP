package com.cytech.acp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Random;

public class ImageUtils {

    public static BufferedImage loadGrayscaleImage(String path) throws IOException {
        BufferedImage img = ImageIO.read(new File(path));
        BufferedImage gray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        gray.getGraphics().drawImage(img, 0, 0, null);
        return gray;
    }

    public static void saveImage(BufferedImage img, String path) throws IOException {
        ImageIO.write(img, "png", new File(path));
    }

    public static BufferedImage addGaussianNoise(BufferedImage img, double sigma) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage noisy = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Random rand = new Random();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int gray = img.getRaster().getSample(x, y, 0);
                double noise = rand.nextGaussian() * sigma;
                int noisyVal = (int) Math.round(gray + noise);
                noisyVal = Math.max(0, Math.min(255, noisyVal));
                noisy.getRaster().setSample(x, y, 0, noisyVal);
            }
        }
        return noisy;
    }
}
