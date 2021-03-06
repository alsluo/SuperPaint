import java.awt.Color;
import java.awt.Graphics;
/*Alston Luo
 * Nov. 14th 2018
 * a oval class 
 */

public class Rectangle extends FillableShape{
    private boolean filled;
    
    //constructor that takes in variables
    public Rectangle( int x1, int y1, int x2, int y2, Color color, boolean filled) {
        super(x1, y1, x2, y2, color, filled);
        this.filled = filled;
    } 
    @Override
    public void draw( Graphics g){
        g.setColor( getColor() );
        if (getFilled()){
            g.fillRect( getX1(), getY1(), getX2() - getX1(), getY2() - getY1() );
        }
        else {
            g.drawRect( getX1(), getY1(), getX2() - getX1(), getY2() - getY1() );
        }
    }
}
