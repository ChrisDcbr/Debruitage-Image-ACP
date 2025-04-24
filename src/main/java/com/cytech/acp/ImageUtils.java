package com.cytech.acp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Classe utilitaire pour la manipulation d'images
 */
public class ImageUtils {
    
    /**
     * Charge une image en niveaux de gris
     * @param path Chemin de l'image
     * @return Image en niveaux de gris
     * @throws IOException Si erreur de lecture
     */
    public static BufferedImage loadImage(String path) throws IOException {
        BufferedImage img = ImageIO.read(new File(path));
        BufferedImage gray = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        gray.getGraphics().drawImage(img, 0, 0, null);
        return gray;
    }
    
    /**
     * Sauvegarde une image
     * @param img Image à sauvegarder
     * @param path Chemin de destination
     * @throws IOException Si erreur d'écriture
     */
    public static void saveImage(BufferedImage img, String path) throws IOException {
        ImageIO.write(img, "png", new File(path));
    }
    
    /**
     * Ajoute un bruit gaussien à une image
     * @param original Image originale
     * @param sigma Écart-type du bruit
     * @return Image bruitée
     */
    public static BufferedImage addNoise(BufferedImage original, double sigma) {
        BufferedImage noisy = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        Random rand = new Random();
        
        for (int y = 0; y < original.getHeight(); y++) {
            for (int x = 0; x < original.getWidth(); x++) {
                int gray = original.getRaster().getSample(x, y, 0);
                double noise = rand.nextGaussian() * sigma;
                int noisyVal = Math.max(0, Math.min(255, (int) Math.round(gray + noise)));
                noisy.getRaster().setSample(x, y, 0, noisyVal);
            }
        }
        return noisy;
    }
}