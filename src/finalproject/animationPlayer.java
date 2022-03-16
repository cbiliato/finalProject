package finalproject;
class Shape
{
    void Hide(int start)
    {   
        
    }
    void Show(int start)
    {
        
    }
    void Jump(int start, int x, int y)
    {
        
    }
    void ChangeColour(int start)
    {
        
    }
}
class Circle extends Shape
        {
    int r;
    int x;
    int y;
    String colour;
    int border;
    String borderColour;
    
    Circle(int r, int x, int y, String colour, int border, String borderColour)
    {
        this.r=r;
        this.x=x;
        this.y=y;
        this.colour=colour;
        this.border=border;
        this.borderColour=borderColour;
    }
    @Override
    void Hide(int start)
    {
        super.Hide(start);
    }
    @Override
    void Show(int start)
    {
        super.Show(start);
    }
    @Override
    void Jump(int start, int x, int y)
    {
        super.Jump(start, x, y);
    }
    @Override
    void ChangeColour(int start)
    {
        super.ChangeColour(start);
    }
}
class Rectangle extends Shape
{
    int x;
    int y;
    int border; 
    String colour;
    String borderColour;
    int length;
    int width;
    
    Rectangle(int x, int y, int border, String colour, String borderColour, int length, int width)
    {
        this.x=x;
        this.y=y;
        this.border=border;
        this.colour=colour;
        this.borderColour=borderColour;
        this.length=length;
        this.width=width;
    }
    @Override
    void Hide(int start)
    {
        super.Hide(start);
    }
    @Override
    void Show(int star)
    {
        super.Show(star);
    }
    @Override
    void Jump(int start, int x, int y)
    {
        super.Jump(start, x, y);
    }
    @Override
    void ChangeColour(int start)
    {
        super.ChangeColour(start);
    }
}
class Line extends Shape
{
    int startX;
    int startY;
    int endX;
    int endY;
    String colour;
    int border;
    
    Line(int startX, int startY, int endX, int endY, String colour, int border)
    {
        this.startX=startX;
        this.startY=startY;
        this.endX=endX;
        this.endY=endY;
        this.colour=colour;
        this.border=border;
    }
    @Override
    void Show(int start)
    {
        super.Show(start);
    }
    @Override
    void Hide(int start)
    {
        super.Hide(start);
    }
    @Override
    void Jump(int start, int x, int y)
    {
        super.Jump(start, x, y);
    }
    @Override
    void ChangeColour(int start)
    {
        super.ChangeColour(start);
    }
}
class AnimationPlayer
{
    AnimationPlayer()
    {
        
    }
    void loadAnimationFromFile(String FileName)
    {
        
    }
    void run()
    {
        
    }
}
public class animationPlayer {
    public static void main(String[] args) {

    }  
}
