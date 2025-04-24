package com.cytech.acp;

/**
 * Représente un patch d'image avec sa position
 */
public class ImagePatch {
    public final int[][] data;
    public final int x;
    public final int y;
    
    public ImagePatch(int[][] data, int x, int y) {
        this.data = data;
        this.x = x;
        this.y = y;
    }
}
