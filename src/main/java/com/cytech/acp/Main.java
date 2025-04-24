package com.cytech.acp;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.math3.linear.RealMatrix;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // 1. Charger l'image
            BufferedImage original = ImageUtils.loadImage("src/resources/images/train/originals/trulimero_trulichina.jpg");
            
            // 2. Ajouter du bruit
            BufferedImage noisy = ImageUtils.addNoise(original, 20.0);
            ImageUtils.saveImage(noisy, "src/resources/images/train/noisy/trulimero_trulichina_noisy.jpg");
            
            // 3. Extraire les patchs
            List<ImagePatch> patches = PatchUtils.extractPatches(noisy, 8, 4);
            
            // 4. Vectoriser les patchs
            List<VectorizedPatch> vectorPatches = new ArrayList<>();
            for (ImagePatch patch : patches) {
                double[] vector = new double[8 * 8];
                int idx = 0;
                for (int[] row : patch.data) {
                    for (int val : row) {
                        vector[idx++] = val;
                    }
                }
                vectorPatches.add(new VectorizedPatch(vector, patch.x, patch.y, 8));
            }
            
            // 5. ACP et débruitage
            List<double[]> vectors = new ArrayList<>();
            for (VectorizedPatch vp : vectorPatches) vectors.add(vp.vector);
            
            RealMatrix projection = PCAProcessor.performPCA(vectors);
            double[] mean = PCAProcessor.computeMean(vectors);
            
            // 6. Choisir le type de seuillage
            System.out.println("Choisissez le type de seuillage (hard/soft) : ");
            String thresholdType = scanner.nextLine().trim();
            System.out.println("Entrez la valeur du seuil : ");
            double threshold = scanner.nextDouble();
            
            // Appliquer le seuillage
            List<double[]> thresholdedVectors = applyThresholding(vectors, thresholdType, threshold);
            
            // Reconstruire les patchs vectorisés
            List<VectorizedPatch> thresholdedPatches = new ArrayList<>();
            for (int i = 0; i < vectorPatches.size(); i++) {
                thresholdedPatches.add(new VectorizedPatch(thresholdedVectors.get(i), 
                                                           vectorPatches.get(i).x, 
                                                           vectorPatches.get(i).y, 
                                                           vectorPatches.get(i).size));
            }
            
            // 7. Reconstruire l'image
            BufferedImage reconstructed = PatchUtils.reconstruct(thresholdedPatches, 
                noisy.getWidth(), noisy.getHeight());
            String path = "src/resources/images/train/reconstructed/trulimero_trulichina.jpg";
            System.out.println("Saving image to: " + path);
            ImageUtils.saveImage(reconstructed, path);
            
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to save image: " + e.getMessage());
        }
    }

    private static List<double[]> applyThresholding(List<double[]> vectors, String thresholdType, double threshold) {
        List<double[]> thresholdedVectors = new ArrayList<>();
        for (double[] vector : vectors) {
            double[] thresholdedVector = new double[vector.length];
            for (int i = 0; i < vector.length; i++) {
                if ("hard".equalsIgnoreCase(thresholdType)) {
                    thresholdedVector[i] = Denoiser.hardThreshold(vector[i], threshold);
                } else if ("soft".equalsIgnoreCase(thresholdType)) {
                    thresholdedVector[i] = Denoiser.softThreshold(vector[i], threshold);
                } else {
                    throw new IllegalArgumentException("Invalid threshold type: " + thresholdType);
                }
            }
            thresholdedVectors.add(thresholdedVector);
        }
        return thresholdedVectors;
    }
}