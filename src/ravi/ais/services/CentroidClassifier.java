package ravi.ais.services;

import ravi.ais.datamodel.MNISTImage;
import ravi.ais.datamodel.MNISTPImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CentroidClassifier {

    public List<MNISTPImage> Imageclassifier(Map<Double, MNISTImage> StandardDeviations, Map<Double, MNISTImage> centroids, List<MNISTImage> images)
    {
        MNISTImageProcessor processor = new MNISTImageProcessor();
        List<MNISTPImage> pimages = new ArrayList<MNISTPImage>();
        for (MNISTImage image : images) {
            double[] dist = new double[centroids.size()];
            double[] SDdist = new double[StandardDeviations.size()];
            for (double i = 0.0; i < centroids.size(); i = i + 1.0) {

                double distance = processor.computeDistance(image, centroids.get(i));
                double Sdistance = processor.computeDistance(image, StandardDeviations.get(i));
                dist[(int) i] = distance;
                SDdist[(int) i] = Sdistance;
            }


            double min = Arrays.stream(dist).min().orElseThrow();
            double Smin = Arrays.stream(SDdist).min().orElseThrow();
            double imin = Arrays.stream(dist).boxed().collect(Collectors.toList()).indexOf(min);
            double Simin = Arrays.stream(SDdist).boxed().collect(Collectors.toList()).indexOf(Smin);
            MNISTPImage pimage = new MNISTPImage(image.getLabel(), image.getPixels(), imin, Simin);
            //System.out.println(pimage.getLabel());
            pimages.add(pimage);

        }
      return  pimages;
    }

    public int predict( Map<Double, MNISTImage> centroids,double[][] image)
    {
        MNISTImageProcessor processor = new MNISTImageProcessor();
        double[] dist = new double[centroids.size()];

        for (double i = 0.0; i < centroids.size(); i = i + 1.0) {

            double distance = processor.computeDistancePredict(image, centroids.get(i));
            dist[(int) i] = distance;

        }
        double min = Arrays.stream(dist).min().orElseThrow();
        double imin = Arrays.stream(dist).boxed().collect(Collectors.toList()).indexOf(min);

        return (int) imin;
    }


}
