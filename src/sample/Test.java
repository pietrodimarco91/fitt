package sample;

import java.util.ArrayList;

public class Test {

    double targetSize,distance;
    ArrayList<Long> timeMs;
    int id;

    public Test(double dist2, double targSize2) {
        this.targetSize=targSize2;
        this.distance=dist2;
        timeMs=new ArrayList<>();
    }

    public double getDistance() {
        return distance;
    }

    public double getTarSize() {
        return targetSize;
    }

    public void addTime(long l) {
        timeMs.add(l);
    }

    public void printRes() {
        System.out.println("target: "+targetSize+" distance: "+distance);
        long avg=0;
        for(int i=0;i<timeMs.size();i++)
            avg+=timeMs.get(i);
        System.out.println(avg);
        id=log((int) (distance/targetSize+1),2);
    }

    static int log(int x, int base)
    {
        return (int) (Math.log(x) / Math.log(base));
    }

    public String getTimeMs() {
        StringBuilder value = new StringBuilder();
        for(int i=0;i<timeMs.size();i++){
            value.append(timeMs.get(i));
            if(i+1!=timeMs.size())
            value.append(",");
        }
        return value.toString();
    }
}
