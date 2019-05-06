import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import javax.swing.JPanel;
/*Alston Luo
 * Nov. 14th 2018
 * a shape class 
 */

abstract class FillableShape extends Shape {
    private boolean filled;

    //constructor that takes in variables
    public FillableShape ( int x1, int y1, int x2, int y2, Color color, boolean filled) {
        super(x1,y1,x2,y2,color);
        setFilled(filled);
    } 

    //accessor for the filled instance
    public boolean getFilled() {
        return this.filled;
    }
    //mutator that changes the filled value
    public void setFilled(boolean filled){
        this.filled = filled;
    }
    //returns the upperleft x value
    public int getUpperLeftX() {
        return Math.min(getX1(),getX2());
    }
    //return the upperleft y value
    public int getUpperLeftY() {
        return Math.min(getY1(),getY2());
    }
    //returns the width of the shape
    public int getWidth() {
        return Math.abs(getX1() - getX2());
    }
    //returns the height of the shape
    public int getHeight() {
        return Math.abs(getY1() - getY2());
    }
}
