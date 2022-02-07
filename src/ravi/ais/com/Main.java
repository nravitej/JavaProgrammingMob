package ravi.ais.com;

import ravi.ais.datamodel.MNISTImage;
import ravi.ais.datamodel.MNISTPImage;
import ravi.ais.services.CentroidClassifier;
import ravi.ais.services.MNISTImageProcessor;
import ravi.ais.services.MNISTReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {



        public static void main(String[] args) throws FileNotFoundException {
            MNISTImageProcessor processor = new MNISTImageProcessor();
            CentroidClassifier classifier=new CentroidClassifier();
            MNISTReader reader = new MNISTReader();
            List<MNISTImage> images = reader.readImages(new File("src/mnist_train.csv"), 100000);

            Map<Double, List<MNISTImage>> imagesByLabel = images.stream().collect(Collectors.groupingBy(MNISTImage::getLabel));
            //System.out.println(imagesByLabel);

            List<MNISTImage> listOfOnes = imagesByLabel.get(1.0);

            //System.out.println(imagesByLabel.get(8.0));

            List<MNISTImage> listOfZeros = imagesByLabel.get(0.0);
            MNISTImage centroidFor1 = processor.computeCentroid(1.0, listOfOnes);

            Map<Double, MNISTImage> centroids = new LinkedHashMap<>();

            //System.out.println(imagesByLabel.entrySet());

            for (Map.Entry<Double, List<MNISTImage>> entry : imagesByLabel.entrySet()) {
                MNISTImage centroid = processor.computeCentroid(entry.getKey(), entry.getValue());
                centroids.put(centroid.getLabel(), centroid);
            }

            Map<Double, MNISTImage> StandardDeviations = new LinkedHashMap<>();

            for (Map.Entry<Double, List<MNISTImage>> entry : imagesByLabel.entrySet()) {
                MNISTImage StandardDeviation = processor.computeSD(entry.getKey(), entry.getValue(),centroids.get(entry.getKey()));
                StandardDeviations.put(StandardDeviation.getLabel(), StandardDeviation);
            }


/*
        System.out.println(centroids.get(1.0));
        System.out.println("SD"+StandardDeviations.get(5.0).getLabel());
        MNISTImageProcessor.displayImage(StandardDeviations.get(5.0));

 */

            List<MNISTPImage> pimages;


            //MNISTImageProcessor.displayPImage(StandardDeviations.get(8.0).getPixels());




            //System.out.println(processor.computeDistance(listOfOnes.get(0), centroidFor1));
            //System.out.println(processor.computeDistance(listOfZeros.get(0), centroidFor1));
            System.out.println(listOfOnes.get(0));
            List<MNISTImage> imagestest = reader.readImages(new File("src/mnist_test.csv"), 11000);


            //Calling the Image classifier function

            pimages=classifier.Imageclassifier(StandardDeviations,centroids,imagestest);

            int predicted=classifier.predict(centroids,imagestest.get(0).getPixels());
            System.out.println("The predicted value of the image"+predicted);
            System.out.println("The real value of the image"+imagestest.get(0).getLabel());
            System.out.println("Below is the analyzed Image");
            MNISTImageProcessor.displayPImage(imagestest.get(0).getPixels());
            System.out.println("\n");


      /*      for (MNISTImage image : images) {
                double[] dist = new double[centroids.size()];
                double[] SDdist=new double[StandardDeviations.size()];
                for (double i = 0.0; i < centroids.size(); i = i + 1.0) {

                    double distance = processor.computeDistance(image, centroids.get(i));
                    double Sdistance = processor.computeDistance(image, StandardDeviations.get(i));
                    dist[(int) i] = distance;
                    SDdist[(int) i] = Sdistance;
                }



                double min = Arrays.stream(dist).min().orElseThrow();
                double Smin= Arrays.stream(SDdist).min().orElseThrow();
                double imin = Arrays.stream(dist).boxed().collect(Collectors.toList()).indexOf(min);
                double Simin = Arrays.stream(SDdist).boxed().collect(Collectors.toList()).indexOf(Smin);
                MNISTPImage pimage=new MNISTPImage(image.getLabel(), image.getPixels(), imin,Simin);
                //System.out.println(pimage.getLabel());
                pimages.add(pimage);

            }
      */
/*
        System.out.println(pimages.get(0).getLabel());
        System.out.println(pimages.get(0).getPlabel());
        System.out.println(pimages.get(0).getSDlabel());
        MNISTImageProcessor.displayPImage(pimages.get(0).getPixels());
        MNISTImageProcessor.displayImage(centroids.get(pimages.get(0).getPlabel()));
        MNISTImageProcessor.displayImage(StandardDeviations.get(pimages.get(0).getSDlabel()));

 */

            double[][] ConfusionMatrix= new double[10][10];
            double[][] SDConfusionMatrix=new double[10][10];
            for (int i=0;i<10;i=i+1)
            {
                for(int j=0;j<10;j=j+1)
                {
                    ConfusionMatrix[i][j]=0.0;
                    SDConfusionMatrix[i][j]=0.0;
                }
            }

            for(MNISTPImage pimage:pimages)
            {
                ConfusionMatrix[(int) pimage.getLabel()][(int) pimage.getPlabel()] = 1.0+ConfusionMatrix[(int) pimage.getLabel()][(int) pimage.getPlabel()];
                SDConfusionMatrix[(int) pimage.getLabel()][(int) pimage.getSDlabel()] = 1.0+ConfusionMatrix[(int) pimage.getLabel()][(int) pimage.getSDlabel()];
            }
            System.out.println(images.size());
            System.out.println("................"+"\n");
            System.out.println("Self Evaluation Confusion Matrix for Centroid Oriented recognization");
            System.out.println("................"+"\n");


            double CentroidAccuracy= 0.0;

            for (int i=0;i<10;i=i+1)
            {
                for(int j=0;j<10;j=j+1)
                {
                    System.out.print((int)ConfusionMatrix[i][j]+"\t");
                    if(i==j)
                    {
                        CentroidAccuracy=CentroidAccuracy+ConfusionMatrix[i][j];
                    }
                }
                System.out.println("\n");
            }

            System.out.println("................"+"\n");
            System.out.println("Self evaluated Centroid Oriented Accuracy:"+(CentroidAccuracy/ pimages.size()));
            System.out.println("................"+"\n");



            System.out.println("................"+"\n");
            System.out.println("Self Analyzed Confusion Matrix for Standard deviation oriented recognization");
            System.out.println("................"+"\n");

            double SDAccuracy= 0.0;

            for (int i=0;i<10;i=i+1)
            {
                for(int j=0;j<10;j=j+1)
                {
                    System.out.print((int)SDConfusionMatrix[i][j]+"\t");
                    if(i==j)
                    {
                        SDAccuracy=SDAccuracy+SDConfusionMatrix[i][j];
                    }

                }
                System.out.println("\n");
            }

            System.out.println("................"+"\n");
            System.out.println("Self evaluated StandardDeviation Oriented Accuracy:"+(SDAccuracy/ pimages.size()));
            System.out.println("................"+"\n");

        }



    }

