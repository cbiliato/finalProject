package finalproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.Timeline;

abstract class Shape {

    javafx.scene.shape.Shape shape;

    int x;
    int y;
    int r;
    String colour;
    int start;
    int border;
    //set default border colour in case not specified
    String borderColour = "0,255,0";
    int length;
    int width;
    int startX;
    int startY;
    int endX;
    int endY;
    //made this to make sure objects were actually being created
    //in the load animation mathod
    static int count;

    Shape() {
        //updates everytime an object is made
        count++;
    }

    abstract void draw(Group root);

    void Hide(int start) {

    }

    void Show(int start) {

    }

    void Jump(int start, int x, int y) {

    }

    void ChangeColour(int start, String colour1) {

    }
}

class Circle extends Shape {

    javafx.scene.shape.Circle circle1 = new javafx.scene.shape.Circle();

    Circle() {

    }

    @Override
    void draw(Group root) {
        circle1.setRadius(r);
        circle1.setVisible(true);
        circle1.setFill(Color.BLUE);
        circle1.setCenterX(x);
        circle1.setCenterX(y);

        root.getChildren().add(circle1);
    }

    @Override
    void Hide(int start) {
        super.Hide(start);
        circle1.setVisible(false);
    }

    @Override
    void Show(int start) {
        super.Show(start);
    }

    @Override
    void Jump(int start, int x, int y) {
        super.Jump(start, x, y);
    }

    @Override
    void ChangeColour(int start, String colour1) {
        super.ChangeColour(start, colour1);
    }
}

class Rectangle extends Shape {

    Rectangle() {
        //debug
        //System.out.print(count);

    }

    void draw(Group root) {

    }

    @Override
    void Hide(int start) {
        super.Hide(start);
    }

    @Override
    void Show(int star) {
        super.Show(star);
    }

    @Override
    void Jump(int start, int x, int y) {
        super.Jump(start, x, y);
    }

    @Override
    void ChangeColour(int start, String colour1) {
        super.ChangeColour(start, colour1);
    }
}

class Line extends Shape {

    Line() {

    }

    void draw(Group root) {
    }

    @Override
    void Show(int start) {
        super.Show(start);
    }

    @Override
    void Hide(int start) {
        super.Hide(start);
    }

    @Override
    void Jump(int start, int x, int y) {
        super.Jump(start, x, y);
    }

    @Override
    void ChangeColour(int start, String colour1) {
        super.ChangeColour(start, colour1);
    }
}

class ap {

    int frames;
    int speed;
    int numObjs;

    void loadAnimationFromFile(String FileName) {
        //there a LOT
        //im gonna comment in case it gets too confusing
        //text me if something doesnt make sense

        //create new file to be read
        File newfile = new File(FileName);
        //create empty string to read the file to
        String data = "";
        try {
            //read all lines of the file to the string
            Scanner reader = new Scanner(newfile);
            while (reader.hasNextLine()) {
                data += reader.nextLine();
                data += "\n";
            }
            //catch if file is not found
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ap.class.getName()).log(Level.SEVERE, null, ex);
        }
        //debug
        System.out.print(data);
        //create array of strings from original data file
        //this file will be separated by two new line characters
        //i.e. the specs of the animation, and each object in the animation
        String[] objs = data.split("\n\n");
        //the specs of the animation will be put into its own array of strings
        //so data can be extracted
        //split at new line character
        String[] specs = objs[0].split("\n");
        //total number of frames is always first line
        frames = Integer.parseInt(specs[0].substring(8, '\n' + 1));
        //speed is always second
        speed = Integer.parseInt(specs[1].substring(7, '\n' - 1));
        //total number of objects is always third
        numObjs = Integer.parseInt(specs[2].substring(0));
        //System.out.print(numObjs);
        //now that the specs data has been extracted, move on to 
        //the actual objects data

        //create array of objects of type shape
        //this is the parent class so each category of shape
        //can be specified when data is read
        Shape[] shapes;
        //size of shape array is the number of objects as specified
        //in the specs data
        shapes = new Shape[numObjs];
        //this is where it gets confusing, bare with me

        //create an array of arrays of strings
        //first index represents object number
        //second index is line number within that objects info block in .txt file
        String[][] info = new String[numObjs][];
        int i;
        //split each object into array of strings
        //split by new line character
        for (i = 1; i <= numObjs; i++) {
            info[i - 1] = objs[i].split("\n");
        }
        //debug
        //System.out.print(info[1][0]);

        //now, we create the objects and put them into the shapes array
        for (i = 0; i < numObjs; i++) {
            //since first line is always blank, second index starts at 1, not 0
            switch (info[i][1]) {
                case ("Circle"):
                    shapes[i] = new Circle();
                    break;
                case ("Rect"):
                    shapes[i] = new Rectangle();
                    break;
                case ("Line"):
                    shapes[i] = new Line();
                    break;
                default:
                    break;
            }

        }
        //now, we extrapolate the data on a case by case basis
        int j;
        //loop to index through the number of shapes
        for (i = 0; i < numObjs; i++) {
            //second loop to index through the individual line of info
            //for each shape
            //loop index starts at 2 to skip unnecessary info
            for (j = 2; j < info[i].length; j++) {
                //cases for different info types
                //assign info to shape object
                if ((info[i][j].contains("r:")) && !(info[i][j].contains("c")) && !(info[i][j].contains("b"))) {
                    shapes[i].r = Integer.parseInt(info[i][j].substring(3));
                } else if (info[i][j].contains("colour:")) {
                    shapes[i].colour = info[i][j].substring(8);
                } else if (info[i][j].contains("Hide")) {
                    shapes[i].Hide(Integer.parseInt(info[i][j + 1].substring(7)));
                    j += 2;
                } else if (info[i][j].contains("Show")) {
                    shapes[i].Show(Integer.parseInt(info[i][j + 1].substring(7)));
                    j += 2;
                } else if (info[i][j].contains("Jump")) {
                    shapes[i].Jump(Integer.parseInt(info[i][j + 1].substring(7)), Integer.parseInt(info[i][j + 2].substring(3)), Integer.parseInt(info[i][j + 3].substring(3)));
                    j += 4;
                } else if (info[i][j].contains("Change")) {
                    shapes[i].ChangeColour(Integer.parseInt(info[i][j + 1].substring(7)), info[i][j + 2].substring(8));
                    j += 3;
                } else if (info[i][j].contains("x:")) {
                    shapes[i].x = Integer.parseInt(info[i][j].substring(3));
                } else if (info[i][j].contains("y:")) {
                    shapes[i].y = Integer.parseInt(info[i][j].substring(3));
                } else if (info[i][j].contains("effect")) {
                    j++;
                } else if (info[i][j].contains("length")) {
                    shapes[i].length = Integer.parseInt(info[i][j].substring(8));
                } else if (info[i][j].contains("width")) {
                    shapes[i].width = Integer.parseInt(info[i][j].substring(7));
                } else if ((info[i][j].contains("border")) && !(info[i][j].contains("l"))) {
                    shapes[i].border = Integer.parseInt(info[i][j].substring(8));
                } else if ((info[i][j].contains("border")) && (info[i][j].contains("l"))) {
                    shapes[i].borderColour = info[i][j].substring(15);
                } else if (info[i][j].contains("startX")) {
                    shapes[i].startX = Integer.parseInt(info[i][j].substring(8));
                } else if (info[i][j].contains("startY")) {
                    shapes[i].startY = Integer.parseInt(info[i][j].substring(8));
                } else if (info[i][j].contains("endX")) {
                    shapes[i].endX = Integer.parseInt(info[i][j].substring(6));
                } else if (info[i][j].contains("endY")) {
                    shapes[i].endY = Integer.parseInt(info[i][j].substring(6));
                }
            }
        }
        //debug
        //System.out.print(shapes[1].border);
    }

    void run() {

    }
}

public class animationPlayer extends Application {

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);

        Circle c1 = new Circle();
        c1.r = 20;
        c1.y = 1;
        c1.x = 1;
        c1.draw(root);

        primaryStage.show();

    }

    public static void main(String[] args) {
        ap a1 = new ap();
        a1.loadAnimationFromFile("/Users/phant/OneDrive/Desktop/finalProjectRepo/finalProject/" + "animation1.txt");
        launch(args);

    }
}
