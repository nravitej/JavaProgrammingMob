package ravi.ais.datamodel;

public class MNISTPImage {

    double label;
    double[][] pixels=new double[28][28];
    double plabel;
    double SDlabel;

    public double getLabel() {
        return label;
    }

    public void setLabel(double label) {
        this.label = label;
    }

    public double[][] getPixels() {
        return pixels;
    }

    public void setPixels(double[][] pixels) {
        this.pixels = pixels;
    }

    public double getPlabel() {
        return plabel;
    }

    public void setPlabel(double plabel) {
        this.plabel = plabel;
    }

    public double getSDlabel() {
        return SDlabel;
    }

    public void setSDlabel(double SDlabel) {
        this.SDlabel = SDlabel;
    }

    public MNISTPImage(double label, double[][] pixels, double plabel, double SDlabel)
    {
        this.label=label;
        this.pixels=pixels;
        this.plabel=plabel;
        this.SDlabel=SDlabel;
    }

}
