package com.cytech.acp;

import org.apache.commons.math3.linear.*;
import java.util.List;

/**
 * Classe pour le traitement ACP
 */
public class PCAProcessor {
    
    /**
     * Calcule la moyenne des vecteurs
     * @param vectors Liste de vecteurs
     * @return Vecteur moyen
     */
    public static double[] computeMean(List<double[]> vectors) {
        int dim = vectors.get(0).length;
        double[] mean = new double[dim];
        
        for (double[] v : vectors) {
            for (int i = 0; i < dim; i++) {
                mean[i] += v[i];
            }
        }
        
        for (int i = 0; i < dim; i++) {
            mean[i] /= vectors.size();
        }
        return mean;
    }
    
    /**
     * Effectue l'ACP sur les vecteurs
     * @param vectors Liste de vecteurs
     * @return Matrice de projection
     */
    public static RealMatrix performPCA(List<double[]> vectors) {
        RealMatrix covMatrix = computeCovariance(vectors);
        EigenDecomposition eig = new EigenDecomposition(covMatrix);
        return eig.getV();
    }
    
    private static RealMatrix computeCovariance(List<double[]> vectors) {
        double[] mean = computeMean(vectors);
        RealMatrix covMatrix = MatrixUtils.createRealMatrix(mean.length, mean.length);
        
        for (double[] v : vectors) {
            RealVector centered = MatrixUtils.createRealVector(v).subtract(MatrixUtils.createRealVector(mean));
            covMatrix = covMatrix.add(centered.outerProduct(centered));
        }
        
        return covMatrix.scalarMultiply(1.0 / vectors.size());
    }
}