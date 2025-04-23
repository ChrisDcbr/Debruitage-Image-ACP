package com.cytech.acp;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            String inputPath = "src/resources/images/train/originals/Ballerina_Cappucina.png";
            String outputPath = "src/resources/images/train/noisy/Ballerina_Cappucina.png";
            double sigma = 20.0;
            int patchSize = 8;

            System.out.println("Chargement de l'image...");
            BufferedImage original = ImageUtils.loadGrayscaleImage(inputPath);

            System.out.println("Ajout du bruit...");
            BufferedImage noisy = ImageUtils.addGaussianNoise(original, sigma);
            ImageUtils.saveImage(noisy, outputPath);

            System.out.println("Extraction des patchs...");
            ArrayList<double[]> patches = PatchUtils.extractPatches(noisy, patchSize);

            System.out.println("Patchs extraits : " + patches.size());
            System.out.println("Taille d'un patch vectorisé : " + patches.get(0).length);

        } catch (IOException e) {
            e.printStackTrace(); // Affiche l’erreur dans la console
        }
    }
}

