import javax.swing.JFrame;
class SuperPaint{
    public static void main(String[] args) {
        DrawFrame application = new DrawFrame();
        application.setSize( 480, 320 ); 
        application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        application.setVisible( true ); 
    }
}
