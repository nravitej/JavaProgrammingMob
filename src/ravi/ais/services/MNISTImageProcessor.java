package ravi.ais.services;

import ravi.ais.datamodel.MNISTImage;

import java.util.List;

public class MNISTImageProcessor {

    public MNISTImage computeCentroid(Double label, List<MNISTImage> images) {
        MNISTImage centroid = new MNISTImage(label, new double[28][28]);
        int size = images.size();
        for (MNISTImage image : images) {
            double[][] pixels = image.getPixels();
            for (int i = 0; i < pixels.length; i++) {
                for (int j = 0; j < pixels[i].length; j++) {
                    centroid.getPixels()[i][j] = centroid.getPixels()[i][j] + pixels[i][j] / size;
                }
            }
        }
        return centroid;
    }

    public double computeDistance(MNISTImage image1, MNISTImage image2){
        double distance = 0.0;

        double[][] image1Pixels = image1.getPixels();
        double[][] image2Pixels = image2.getPixels();

        for (int i = 0 ; i < MNISTReader.MAX_ROW; i++){
            for (int j = 0 ; j < MNISTReader.MAX_COL; j++){

                    distance = distance + Math.pow(image2Pixels[i][j] - image1Pixels[i][j], 2);

            }

        }

        return Math.sqrt(distance);
    }

    public double computeDistancePredict(double[][] image1Pixels, MNISTImage image2){
        double distance = 0.0;


        double[][] image2Pixels = image2.getPixels();

        for (int i = 0 ; i < MNISTReader.MAX_ROW; i++){
            for (int j = 0 ; j < MNISTReader.MAX_COL; j++){
                distance = distance + Math.pow(image2Pixels[i][j] - image1Pixels[i][j],2);
            }

        }

        return Math.sqrt(distance);
    }

    public MNISTImage computeSD(Double label, List<MNISTImage> images, MNISTImage CentroidImage) {
        MNISTImage SD = new MNISTImage(label, new double[28][28]);
        int size = images.size();
        for (MNISTImage image : images) {
            double[][] pixels = image.getPixels();
            double[][] cpixels= CentroidImage.getPixels();
            for (int i = 0; i < pixels.length; i++) {
                for (int j = 0; j < pixels[i].length; j++) {
                    SD.getPixels()[i][j] = SD.getPixels()[i][j]+ Math.sqrt(Math.pow(cpixels[i][j] - pixels[i][j],2) / (size-1));
                }
            }


        }
        return SD;
    }

    public static void displayImage(MNISTImage image) {
        double[][] pixels = image.getPixels();
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                if (pixels[i][j] < 128) {
                    System.out.print("..");
                } else {
                    System.out.print("xx");
                }
            }
            System.out.println();
        }
    }


    public static void displayPImage(double[][] pixels) {

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                if (pixels[i][j] < 120) {
                    System.out.print("..");
                } else {
                    System.out.print("xx");
                }
            }
            System.out.println();
        }
    }



}
