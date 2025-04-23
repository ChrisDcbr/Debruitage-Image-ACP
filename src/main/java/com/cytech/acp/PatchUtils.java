package com.cytech.acp;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class PatchUtils {

    public static ArrayList<double[]> extractPatches(BufferedImage img, int patchSize) {
        int width = img.getWidth();
        int height = img.getHeight();
        ArrayList<double[]> patches = new ArrayList<>();

        for (int y = 0; y <= height - patchSize; y += patchSize) {
            for (int x = 0; x <= width - patchSize; x += patchSize) {
                double[] patch = new double[patchSize * patchSize];
                int idx = 0;
                for (int j = 0; j < patchSize; j++) {
                    for (int i = 0; i < patchSize; i++) {
                        patch[idx++] = img.getRaster().getSample(x + i, y + j, 0);
                    }
                }
                patches.add(patch);
            }
        }
        return patches;
    }
}
