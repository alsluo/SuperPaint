import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Shape;

public class DrawFrame extends JFrame {
    private JLabel statusBar = new JLabel();
    private JPanel widgetPnl = new JPanel();
    private JPanel drawPnl = new DrawPanel();
    private Shape currentShape = null;
    private LinkedList<Shape> shapes = new LinkedList<>();
    private DynamicStack<Shape> redoStk = new DynamicStack<>();
    private int selectedShape = 0;
    private String[] shapeNames = { "Line", "Oval", "Rectangle" };
    private int[] shapeValues = { 0, 1, 2 };
    private Color currentColor = Color.BLACK;
    private String[] colourNames = { "Black", "Red", "Green", "Blue", "Yellow", "Pink" };
    private Color[] colourValues = { Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.PINK };
    private JButton undoBt = new JButton("Undo");
    private JButton redoBt = new JButton("Redo");
    private JButton clearBt = new JButton("Clear");
    private JComboBox colourDpd = new JComboBox(colourNames);
    private JComboBox shapeDpd = new JComboBox(shapeNames);
    private boolean fill = false;
    private JCheckBox filledBx = new JCheckBox("Filled");
    private int startX, startY;
    
    
    // Constructor instantiates an array of 10 Random Line objects
    public DrawFrame(  ) {
        super("Super Paint Program");
        drawPnl.setBackground( Color.WHITE ); 
        
        // Sets the layout for the widget panel, and adds widgets into the widget panel
        widgetPnl.setLayout( new GridLayout(1, 6));
        widgetPnl.add(undoBt);
        widgetPnl.add(redoBt);
        widgetPnl.add(clearBt);
        widgetPnl.add(colourDpd);
        widgetPnl.add(shapeDpd);
        widgetPnl.add(filledBx);
        
        // Adds status bar, widgets panel, and draw panel within the frame
        add(widgetPnl, BorderLayout.NORTH );
        add(drawPnl, BorderLayout.CENTER );
        add(statusBar, BorderLayout.SOUTH );
        
        // Create and register listener for mouse and mouse motion events, button events, and items events.
        MouseEventListener drawPanelListener = new MouseEventListener();
        drawPnl.addMouseListener( drawPanelListener ); 
        drawPnl.addMouseMotionListener( drawPanelListener );
        
        // Create and register listener for mouse and mouse motion events, button events, and items events.
        ButtonListener buttonListener = new ButtonListener();
        undoBt.addActionListener(buttonListener);
        redoBt.addActionListener(buttonListener);
        clearBt.addActionListener(buttonListener);
        
        // Create and register listener for checkbox events.
        filledBx.setEnabled(false);
        filledBx.addItemListener(new CheckboxListener());
        
        // Create and register listener for combo box events.
        DropDownListener dropDownListener = new DropDownListener();
        colourDpd.addItemListener(dropDownListener);
        shapeDpd.addItemListener(dropDownListener);
    } 
    
    // Inner class to handle mouse events
    class MouseEventListener extends MouseAdapter {
        // Mouse press indicates a new line has been started
        @Override
        public void mousePressed( MouseEvent event ) {
            startX = event.getX();
            startY = event.getY();
            switch (selectedShape){
                case 0:
                    currentShape = new Line( startX, startY, startX, startY, currentColor );
                    break;
                case 1:
                    currentShape = new Oval( startX, startY, startX, startY, currentColor , fill);
                    break;
                case 2:
                    currentShape = new Rectangle( startX, startY, startX, startY, currentColor , fill);
                    break;
            }

            redoStk.clear();

            // Tell JVM to call paintComponent( g )
            repaint();
        } 

        // Mouse release indicates the new line is finished
        @Override
        public void mouseReleased( MouseEvent event ) {
            // Update ending coordinates and switch color to BLACK
            currentShape.setColor( currentColor );
            shapes.addFirst(currentShape);

            // Get ready for the next line to be drawn
            currentShape = null;
            repaint();            
        } 

        // As mouse is dragged, update ending coordinates of currentShape and statusBar
        @Override
        public void mouseDragged( MouseEvent event ) {
            setCurrentShapeXY( event.getX(), event.getY() );
            
            statusBar.setText( String.format( "Mouse at (%d, %d)", 
                        event.getX(), event.getY() ) );
            repaint();
        } 

        // As mouse is moved, just update the statusBar
        @Override
        public void mouseMoved( MouseEvent event ) {
            statusBar.setText( String.format( "Mouse at (%d, %d)", 
                        event.getX(), event.getY() ) );
        } 
    } 

    class CheckboxListener implements ItemListener {
        @Override
        public void itemStateChanged( ItemEvent e ) {
            if ( filledBx.isSelected() ) {
                fill = true;
            } 
            else {
                fill = false;
            }
        }
    }

    class DropDownListener implements ItemListener {
        @Override
        public void itemStateChanged( ItemEvent e ) {
            if ( e.getSource() == colourDpd ) {
                currentColor = colourValues[ colourDpd.getSelectedIndex() ];
            }
            else {
                int index = shapeDpd.getSelectedIndex();
                if (index == 0){
                    filledBx.setEnabled(false);
                }
                else {
                    filledBx.setEnabled(true);
                }
                selectedShape = shapeValues[ index ];
            }
        }
    }

    class ButtonListener implements ActionListener {
        @Override 
        public void actionPerformed( ActionEvent e ) {
            // removes and pushes the last shape drawn into undoStk, if shapes isn't empty
            if ( e.getSource() == undoBt ) {
                if ( !shapes.isEmpty() ){
                    redoStk.push(shapes.removeFirst());
                }
                else{
                    statusBar.setText( String.format( "Already undid everything"));
                }
            }
            // pops and add the last shape that had undergone an undo operation into shapes, if redoStk isn't empty
            else if ( e.getSource() == redoBt ) {
                if ( !redoStk.isEmpty() ){
                    shapes.addFirst(redoStk.pop());
                }
                else{
                    statusBar.setText( String.format( "Already undid everything"));
                }
            }
            // clears all shapes that is drawn and clears the redoStk
            else if ( e.getSource() == clearBt ){
                shapes.clear();
                redoStk.clear();
            }
            // repaints the frame
            repaint();
        }
    }

    class DrawPanel extends JPanel{
        @Override
        public void paintComponent( Graphics g ) {
            super.paintComponent( g );

            if( !shapes.isEmpty() ){
                shapes = new LinkedList<Shape>(); 
                Shape shape;
                for ( int count = 0; count < shape.size(); count++){
                    shape = shapes.removeLast();
                    shapes.addFirst(shape);
                    shape.draw(g);
                }
            }

            // if a line is in progress, draw it on top of all others
            if ( currentShape != null )
                currentShape.draw( g );
        }
    }
    private void setCurrentShapeXY( int x, int y ){
       if (startX < x){
           currentShape.setX1( x );
           currentShape.setX2( startX );
       }
       else {
           currentShape.setX1( startX );
           currentShape.setX2( x );
       }
       if (startY < y){
           currentShape.setY1( y );
           currentShape.setY2( startY );
       }
       else {
           currentShape.setY1( startY );
           currentShape.setY2( y );
       }
           
    }
} 
