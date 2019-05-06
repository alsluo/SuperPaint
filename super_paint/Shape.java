import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;
abstract class Shape {
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private Color color;
 
 public Shape ( int x1, int y1, int x2, int y2, Color color) {
        setX1(x1);
        setX2(x2);
        setY1(y1);
        setY2(y2);
        setColor(color);
    } 
    
    //contructor that doesn't accept variables
    
    //accessor for the x1 instance
    public int getX1() {
        return this.x1;
    }
    
    //accessor for the x2 instance
    public int getX2() {
        return this.x2;
    }
    
    //accessor for the y1 instance
    public int getY1() {
        return this.y1;
    }
    //accessor for the y2 instance
    public int getY2() {
        return this.y2;
    }
    //accessor for the filled instance
    public Color getColor(){
        return this.color;
    }
    //mutator for the x1 instance
    public void setX1(int newX1) {
        if (isValidInput(newX1)) {
            this.x1 = newX1;
        }
        else {
            this.x1 = 0;
        }
    }
    //mutator for the x2 instance
    public void setX2(int newX2) {
        if (isValidInput(newX2)) {
            this.x2 = newX2;
        }
        else {
            this.x2 = 0;
        }
    }
    //mutator for the y1 instance
    public void setY1(int newY1) {
        if (isValidInput(newY1)) {
            this.y1 = newY1;
        }
        else{
            this.y1 = 0;
        }
    }  
    //mutator for the y2 instance
    public void setY2(int newY2) {
        if (isValidInput(newY2)) {
            this.y2 = newY2;
        }
        else {
            this.y2 = 0;
        }
    } 
    //mutator that changes the filled value
    public void setColor( Color color){
        this.color = color;
    }
    //helper method that validates user input for coordinates
    private boolean isValidInput(int num) {
        if (num < 0){
            System.err.println("You have entered a value smaller than 0, defaulting to 0.");
            return false;
        }
        return true;
    }
    //toString method, returns the starting and ending x,y coord
    public String toString() {
        return String.format("Start(" + x1 +"," + y1 +") End("+ x2 +","+ y2 +")");
    }

    public abstract void draw(Graphics g);
}

