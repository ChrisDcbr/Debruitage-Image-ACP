package com.cytech.acp;

/**
 * Représente un patch vectorisé avec sa position
 */
public class VectorizedPatch {
    public final double[] vector;
    public final int x;
    public final int y;
    public final int size;
    
    public VectorizedPatch(double[] vector, int x, int y, int size) {
        this.vector = vector;
        this.x = x;
        this.y = y;
        this.size = size;
    }
}
