package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.scene.layout.CircularPane;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    CircularPane cirPane;
    ArrayList<Cerchio> circles;
    ArrayList<Test> tests;
    int indexTest;
    int point,startingPoint,iteration,startTest;
    long t1,t2;
    Stage pS;


    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        pS=primaryStage;
        indexTest=0;
        tests= new ArrayList<>();
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setOnCloseRequest(e -> close(primaryStage));
        circles=new ArrayList<>();
        cirPane=new CircularPane();
        double dist1=500.0;
        double dist2=400.0;
        double dist3=200.0;

        double targSize1=25.0;
        double targSize2=10.0;
        startTest=0;
        tests.add(new Test(dist1,targSize1));
        tests.add(new Test(dist1,targSize2));
        tests.add(new Test(dist2,targSize1));
        tests.add(new Test(dist2,targSize2));
        tests.add(new Test(dist3,targSize1));
        tests.add(new Test(dist3,targSize2));
        cirPane.setDiameter(tests.get(indexTest).getDistance());

        for (int i=0;i<9;i++){
            circles.add(new Cerchio((int) tests.get(indexTest).getTarSize(),new Color(0, 0, 0, 0.1686),i,this));
            cirPane.getChildren().add(circles.get(i).getCircle());
        }



        BorderPane bP=new BorderPane(cirPane);
        bP.setStyle("-fx-background-color: white;");
        Scene scene1=new Scene(bP, 600, 600);

        primaryStage.setTitle("Fitts’s Law");
        primaryStage.setScene(scene1);
        Random rand = new Random();
        startingPoint=rand.nextInt(9);
        point=startingPoint;
        iteration=0;
        startAlgo();
        primaryStage.show();

    }

    public void startAlgo() {
        if(point==(startingPoint+5)%9 && iteration!=1){
            if(indexTest<tests.size()-1){
                    point=startingPoint;
                    indexTest++;
                    circles.get(point).select();
                    point=(point+5)%9;
                    changeTest();
                    iteration=1;
                } else analizeResults();
        }else{
            circles.get(point).select();
            point=(point+5)%9;
            iteration++;
        }

    }

    private void analizeResults() {

        String csvFile = "results.csv";
        FileWriter writer = null;
        try {
            writer = new FileWriter(csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            for(int i=0;i<tests.size();i++){
                tests.get(i).printRes();
                CSVUtils.writeLine(writer, Arrays.asList(String.valueOf(tests.get(i).distance), String.valueOf(tests.get(i).getTarSize()), tests.get(i).getTimeMs(), String.valueOf(tests.get(i).id)));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        close(pS);

    }

    private void changeTest() {
        for (int i=0;i<9;i++) circles.get(i).changeDiam((int) tests.get(indexTest).getTarSize());
        cirPane.setDiameter(tests.get(indexTest).getDistance());
    }

    private void close(Stage primaryStage) {
        primaryStage.close();
    }


    public void animateOut() {
        cirPane.setAnimationInterpolation(CircularPane::animateFromTheOriginWithFadeRotate);
        cirPane.animateOut();

    }

    public void mousePressed() {

        if(startTest==0){
            System.out.println(iteration+" "+indexTest);
            t1=System.nanoTime();
            if(iteration!=1)
                tests.get(indexTest).addTime(TimeUnit.NANOSECONDS.toMillis(t1 - t2));
            startTest=1;
        }else{
            t2=System.nanoTime();
            tests.get(indexTest).addTime(TimeUnit.NANOSECONDS.toMillis(t2 - t1));
            startTest=0;
        }


    }
    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
