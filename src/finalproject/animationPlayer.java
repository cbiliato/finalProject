package finalproject;

import java.util.ArrayList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

/**
 * Abstract class Shape is the parent class of all the shapes, each shape 
 * contains components from the Shape class and applies the features to display
 * from the timeline to the scene.
 * 
 * @author Group 8
 * @version JDK 1.8.0_321
 * @since 2022-04-02
 */
abstract class Shape {
    javafx.scene.shape.Shape shape;

    int x;
    int y;
    int r;
    String colour = "0,0,255";
    int start;
    int border;
    String borderColour = "0,255,0";    //set default border colour in case not specified
    int length;
    int width;
    int startX;
    int startY;
    int endX;
    int endY;
    //so objects are actually created
    //in the load animation method
    static int count;
    ArrayList<Effect> effects = new ArrayList<Effect>(0);

    /**
     *
     */
    Shape() {
        //updates everytime an object is made
        count++;
    }


    /**
     * Creates a javafx shape using the attributes from the object.
     * @param root parent node to place the shape node onto
     */
    abstract void draw(Group root);    //needs to be specific shape


    /**
     * @return javafx shape object
     */
    abstract javafx.scene.shape.Shape getShape();    //general shape

}
/**
 * Circle is the child class of Shape parent class.
 * 
 * @author Group 8
 * @version JDK 1.8.0_321
 * @since 2022-04-02
 */
class Circle extends Shape {

    //couldnt call upon class circle because we already have circle class
    javafx.scene.shape.Circle circle1 = new javafx.scene.shape.Circle();
    Circle() {

    }


    @Override
    void draw(Group root) {    //attributes for circle
        Color c = Color.web("rgb(" + colour + ")"); //turn string into color value
        circle1.setRadius(r);
        circle1.setVisible(true);
        circle1.setFill(c);
        circle1.setCenterX(x);
        circle1.setCenterY(y);

        root.getChildren().add(circle1); //putting circle onto parent node
    }

    @Override
    javafx.scene.shape.Circle getShape() {
        return circle1;
    }
}
/**
 * Rectangle is the child class of Shape parent class.
 * 
 * @author Group 8
 * @version JDK 1.8.0_321
 * @since 2022-04-02
 */
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

    @Override
    javafx.scene.shape.Rectangle getShape() {
        return rectangle1;
    }
}
/**
 * Line is the child class of Shape parent class.
 * 
 * @author Group 8
 * @version JDK 1.8.0_321
 * @since 2022-04-02
 */
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

    @Override
    javafx.scene.shape.Line getShape() {
        return line1;
    }
}
/**
 * The Effect class is the parent class of all the effects, each effect
 * contains components from the Effect class and applies the features to display
 * from the timeline to the scene.
 * 
 * @author Group 8
 * @version JDK 1.8.0_321
 * @since 2022-04-02
 */
class Effect {

    private int start;

    /**
     * Creates a keyframe where the effect is applied to the shape.
     * @param shape javafx shape that gets effect applied to
     * @param timeline timeline of multiple frames where keyframe is added to
     */
    void play(javafx.scene.shape.Shape shape, Timeline timeline) {
    }

    /**
     * @return the start frame of the effect keyframe
     */
    int getStart() {
        return start;
    }

    /**
     * Sets the start frame of the effect keyframe.
     * @param start frame when the effect starts
     */
    void setStart(int start) {
        if (start > 0) {
            this.start = start;
        }
    }
}
/**
 * Show is the child class of Effects parent class. The Show class allows the
 * shape to appear on the screen.
 * 
 * @author Group 8
 * @version JDK 1.8.0_321
 * @since 2022-04-02
 */
class Show extends Effect {

    @Override
    void play(javafx.scene.shape.Shape shape, Timeline timeline) { //to put keyframe into the timeline
        //first parameter is start time
        KeyFrame showFrame = new KeyFrame(Duration.seconds(getStart()/ApplicationPlayer.speed), event -> {
            shape.setVisible(true);
        }
        );
        //adds keyframe to timeline
        timeline.getKeyFrames().add(showFrame);
    }
}
/**
 * Hide is the child class of Effects parent class. The Hide class allows the
 * shape to be removed from the screen.
 * 
 * @author Group 8
 * @version JDK 1.8.0_321
 * @since 2022-04-02
 */
class Hide extends Effect {

    @Override
    void play(javafx.scene.shape.Shape shape, Timeline timeline) {
        //first parameter is start time
        KeyFrame showFrame = new KeyFrame(Duration.seconds(getStart()/ApplicationPlayer.speed), event -> {
            shape.setVisible(false);
        }
        );
        //adds keyframe to timeline
        timeline.getKeyFrames().add(showFrame);
    }
}
/**
 * Jump is the child class of Effects parent class. The jump class allows for the 
 * shape to appear in another place on the screen.
 * 
 * @author Group 8
 * @version JDK 1.8.0_321
 * @since 2022-04-02
 */
class Jump extends Effect {

    private int x; //coordinates
    private int y;

    /**
     * @return x value after jump effect
     */
    int getX() {
        return x;
    }

    /**
     * Sets the x value of the jump effect.
     * @param x x value after jump effect
     */
    void setX(int x) {
        if (x > 0) {
            this.x = x;
        }
    }

    /**
     * @return y value after jump effect
     */
    int getY() {
        return y;
    }

    /**
     * Sets the y value of the jump effect.
     * @param y y value after jump effect
     */
    void setY(int y) {
        if (y > 0) {
            this.y = y;
        }
    }

    @Override
    void play(javafx.scene.shape.Shape shape, Timeline timeline) {
        //first parameter is start time
        KeyFrame showFrame = new KeyFrame(Duration.seconds(getStart()/ApplicationPlayer.speed), event -> {
            shape.relocate(x,y);
        });
        timeline.getKeyFrames().add(showFrame);
    }
}
/**
 * ChangeColour is the child class of Effects parent class. The ChangeColour class allows 
 * for the shape to appear as a different color.
 * 
 * @author Group 8
 * @version JDK 1.8.0_321
 * @since 2022-04-02
 */
class ChangeColour extends Effect {

    Color c;
    String colour;
    static int count = 0;

    /**
     *
     */
    ChangeColour() {
        count++;
        this.colour = "0,0,255";
    }

    /**
     * Sets the colour of the shape according to string parameter.
     * @param colour1 String of rgb values
     */
    void setColor(String colour1) {
        this.colour = colour1;
        this.c = Color.web("rgb(" + colour + ")");

    }
    @Override
    void play(javafx.scene.shape.Shape shape, Timeline timeline) {
        if(shape instanceof javafx.scene.shape.Circle)
        {
            KeyFrame showFrame = new KeyFrame(Duration.seconds(getStart()/ApplicationPlayer.speed), event -> {
            shape.setFill(c);
            }
            );
            timeline.getKeyFrames().add(showFrame);
        }
        else if(shape instanceof javafx.scene.shape.Rectangle)
        {
            KeyFrame showFrame = new KeyFrame(Duration.seconds(getStart()/ApplicationPlayer.speed), event -> {
            shape.setFill(c);
            }
            );
            timeline.getKeyFrames().add(showFrame);
        }
        else if(shape instanceof javafx.scene.shape.Line)
        {
            KeyFrame showFrame = new KeyFrame(Duration.seconds(getStart()/ApplicationPlayer.speed), event -> {
            shape.setStroke(c);
            }
            );
            timeline.getKeyFrames().add(showFrame);
        }
        timeline.play();
   }
}
/**
 * The ApplicationPlayer class takes on the information stored in the text file.
 * With this, it then extracts the data on a shape by shape basis and deciphers
 * the important information that the program needs to know.
 * 
 * @author Group 8
 * @version JDK 1.8.0_321
 * @since 2022-04-02
 */
class ApplicationPlayer {

    static int frames;
    static int speed;
    static int numObjs;

    /**
     * Creates shape objects and related effects. Stores attributes from the text file being read.
     * @param FileName string of file path for the text file being read
     * @return array of all shape objects
     */
    public Shape[] loadAnimationFromFile(String FileName) {
        //creating new file to be read
        File newfile = new File(FileName);
        //creating empty string to read the file to
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
            Logger.getLogger(ApplicationPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        //debug
        System.out.print(data);
        //create array of strings from original data file
        //this file will be separated by two new line characters
        //i.e. the specs of the animation, and each object in the animation
        try{
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

            //creating the objects and put them into the shapes array
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
                    if ((info[i][j].contains("r:")) && !(info[i][j].contains("c")) && !(info[i][j].contains("b"))&&!(info[i][j].contains("C"))) {
                        shapes[i].r = Integer.parseInt(info[i][j].substring(3));
                    } else if (info[i][j].contains("colour:")&&!(info[i][j].contains("Change"))) {
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
                        shapes[i].effects.add(new Jump());
                        //sets variables start,x,y
                        shapes[i].effects.get(shapes[i].effects.size() - 1).setStart(Integer.parseInt(info[i][j + 1].substring(7)));
                        ((Jump) shapes[i].effects.get(shapes[i].effects.size() - 1)).setX(Integer.parseInt(info[i][j + 2].substring(3)));
                        ((Jump) shapes[i].effects.get(shapes[i].effects.size() - 1)).setY(Integer.parseInt(info[i][j + 3].substring(3)));
                        j += 4;
                    }  else if (info[i][j].contains("Change")) {
                        shapes[i].effects.add(new ChangeColour());
                        ((ChangeColour)shapes[i].effects.get(shapes[i].effects.size() - 1)).setColor(info[i][j].substring(14));
                        shapes[i].effects.get(shapes[i].effects.size() - 1).setStart(Integer.parseInt(info[i][j + 1].substring(7)));
                        j +=4;
                    } else if (info[i][j].contains("x:")) {
                        shapes[i].x = Integer.parseInt(info[i][j].substring(3));
                    } else if (info[i][j].contains("y:")) {
                        shapes[i].y = Integer.parseInt(info[i][j].substring(3));
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
            return (shapes);
        }
        catch(Exception e)
        {
            System.out.println("Incorrect text file format.");
            return null;
        }
    }
}
/**
 * The public class FinalProject is the child class of import parent class 
 * Application.
 * 
 * @author Group 8
 * @version JDK 1.8.0_321
 * @since 2022-04-02
 */
public class animationPlayer extends Application {

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 600, 600);
        ApplicationPlayer a1 = new ApplicationPlayer();
        Shape[] shapes = a1.loadAnimationFromFile("/Users/carts/Downloads/animation1.txt");
        //launch(args);
        int i;
        /*for (i = 0; i < shapes.length; i++) {
            shapes[i].draw(root);
        }*/
        primaryStage.setScene(scene);
        primaryStage.show();

        //parameter is frame rate
        Timeline timeline = new Timeline(ApplicationPlayer.speed);
        timeline.setCycleCount(1);
        for (Shape shape : shapes) {
            shape.draw(root);
            for (Effect effect : shape.effects) {
                effect.play(shape.getShape(), timeline);
            }
        }
        //first parameter is start time
        KeyFrame stopFrame = new KeyFrame(Duration.seconds(ApplicationPlayer.frames / ApplicationPlayer.speed), event -> {
            timeline.stop();
            primaryStage.close();
        }
        );
        //adds keyframe to timeline
        timeline.getKeyFrames().add(stopFrame);
        timeline.play();        
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);

    }
}
