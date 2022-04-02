package finalproject;

import java.util.ArrayList;

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
import javafx.animation.KeyFrame;
import javafx.util.Duration;

abstract class Shape {

    javafx.scene.shape.Shape shape;

    int x;
    int y;
    int r;
    String colour = "0,0,255";
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
    ArrayList<Effect> effects = new ArrayList<Effect>(0);

    Shape() {
        //updates everytime an object is made
        count++;
    }

    abstract void draw(Group root);
}

class Circle extends Shape {

    javafx.scene.shape.Circle circle1 = new javafx.scene.shape.Circle();

    Circle() {

    }

    @Override
    void draw(Group root) {
        Color c = Color.web("rgb(" + colour + ")");
        circle1.setRadius(r);
        circle1.setVisible(true);
        circle1.setFill(c);
        circle1.setCenterX(x);
        circle1.setCenterY(y);

        root.getChildren().add(circle1);
    }
}

class Rectangle extends Shape {

    javafx.scene.shape.Rectangle rectangle1 = new javafx.scene.shape.Rectangle();

    Rectangle() {
        //debug
        //System.out.print(count);

    }

    @Override
    void draw(Group root) {
        Color c = Color.web("rgb(" + colour + ")");
        rectangle1.setX(x);
        rectangle1.setY(y);
        rectangle1.setVisible(true);
        rectangle1.setFill(c);
        rectangle1.setWidth(length);
        rectangle1.setHeight(width);

        root.getChildren().add(rectangle1);
    }
}

class Line extends Shape {

    javafx.scene.shape.Line line1 = new javafx.scene.shape.Line();

    Line() {

    }

    @Override
    void draw(Group root) {
        Color c = Color.web("rgb(" + colour + ")");
        line1.setVisible(true);
        line1.setStroke(c);
        line1.setStartX(startX);
        line1.setStartY(startY);
        line1.setEndX(endX);
        line1.setEndY(endY);

        root.getChildren().add(line1);
    }

}

class Effect {

    private int start;

    void play() {
    }

    int getStart() {
        return start;
    }

    void setStart(int start) {
        if (start > 0) {
            this.start = start;
        }
    }
}

class Show extends Effect {

    void play(javafx.scene.shape.Shape shape, Timeline timeline) {
        //first parameter is start time
        KeyFrame showFrame = new KeyFrame(Duration.seconds(ap.frames / ap.speed), event -> {
            timeline.stop();
            shape.setVisible(true);
        }
        );

        //adds keyframe to timeline
        timeline.getKeyFrames().add(showFrame);

    }
}

class Hide extends Effect {

}

class Jump extends Effect {

    private int x;
    private int y;

    int getX() {
        return x;
    }

    void setX(int x) {
        if (x > 0) {
            this.x = x;
        }
    }

    int getY() {
        return y;
    }

    void setY(int y) {
        if (y > 0) {
            this.y = y;
        }
    }

}

class ChangeColour extends Effect {

    Color setColour(String colour1, int start) {
        String rgb = "rgb(";
        Color c = Color.web(rgb + colour1 + ")");
        return c;
    }

}

class ap {

    static int frames;
    static int speed;
    static int numObjs;

    public Shape[] loadAnimationFromFile(String FileName) {
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
                    shapes[i].effects.add(new Hide());
                    //sets start variable
                    shapes[i].effects.get(shapes[i].effects.size() - 1).setStart(Integer.parseInt(info[i][j + 1].substring(7)));
                    j += 2;
                } else if (info[i][j].contains("Show")) {
                    shapes[i].effects.add(new Show());
                    //sets start variable
                    shapes[i].effects.get(shapes[i].effects.size() - 1).setStart(Integer.parseInt(info[i][j + 1].substring(7)));
                    j += 2;
                } else if (info[i][j].contains("Jump")) {
                    //shapes[i].Jump(Integer.parseInt(info[i][j + 1].substring(7)), Integer.parseInt(info[i][j + 2].substring(3)), Integer.parseInt(info[i][j + 3].substring(3)));
                    shapes[i].effects.add(new Jump());
                    //sets variables start,x,y
                    shapes[i].effects.get(shapes[i].effects.size() - 1).setStart(Integer.parseInt(info[i][j + 1].substring(7)));
                    ((Jump) shapes[i].effects.get(shapes[i].effects.size() - 1)).setX(Integer.parseInt(info[i][j + 2].substring(3)));
                    ((Jump) shapes[i].effects.get(shapes[i].effects.size() - 1)).setY(Integer.parseInt(info[i][j + 3].substring(3)));
                    j += 4;
                } else if (info[i][j].contains("Change")) {
                    shapes[i].effects.add(new ChangeColour());

                    shapes[i].effects.get(shapes[i].effects.size() - 1).setStart(Integer.parseInt(info[i][j + 1].substring(7)));
                    //((ChangeColour) shapes[i].effects.get(shapes[i]));
                    //shapes[i].ChangeColour(Integer.parseInt(info[i][j + 1].substring(7)), info[i][j + 2].substring(8));
                    //j += 3;
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
        System.out.print(shapes[0].colour);
        return (shapes);

    }

    void run() {

    }
}

public class animationPlayer extends Application {

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);
        ap a1 = new ap();
        Shape[] shapes = a1.loadAnimationFromFile("Users/phant/OneDrive/Desktop/finalProjectRepo/finalProject/animation1.txt");
        //launch(args);
        int i;
        for (i = 0; i < shapes.length; i++) {
            shapes[i].draw(root);
        }
        primaryStage.setScene(scene);
        primaryStage.show();

        //parameter is frame rate
        Timeline timeline = new Timeline(ap.speed);
        timeline.setCycleCount(1);

        //first parameter is start time
        KeyFrame stopFrame = new KeyFrame(Duration.seconds(ap.frames / ap.speed), event -> {
            timeline.stop();
            primaryStage.close();
        }
        );

        //adds keyframe to timeline
        timeline.getKeyFrames().add(stopFrame);

        Show show = new Show();
        show.play(((Circle)shapes[0]).circle1, timeline);

        timeline.play();

    }

    public static void main(String[] args) {
        ap a1 = new ap();
        //Shape[] shapes = a1.loadAnimationFromFile("/Users/giannacasselli/Downloads/animation1.txt");
        //System.out.print(shapes[1].x);
        launch(args);

    }
}
