import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

import static java.lang.Math.abs;

public class DrawingCanvas extends JComponent{
    private int width = 1000;
    private int height = 500;

    // Image in which we're going to draw
    private Image image;
    // Graphics2D object ==> used to draw on
    private Graphics2D g2;
    // Mouse coordinates
    private int currentX, currentY, oldX, oldY;



    public DrawingCanvas(){

    }

    protected void paintComponent(Graphics g) {
        if (image == null) {
            // image to draw null ==> we create
            image = createImage(getSize().width, getSize().height);
            g2 = (Graphics2D) image.getGraphics();

            // clear draw area
            clear();
        }

        g.drawImage(image, 0, 0, null);
    }
    public void clear() {
        g2.setPaint(Color.white);
        // draw white on entire draw area to clear
        g2.fillRect(0, 0, getSize().width, getSize().height);
        g2.setPaint(Color.black);
        repaint();
    }




    void drawLineMouse(){
        System.out.println("Rysowanie linii myszką");

    }

    void drawLineXY(int x1, int y1, int x2, int y2){


        System.out.println("Rysowanie linii XY z X1: " + x1 + " Y1: " + y1 + " do X2: " + x2 + " Y2: "+y2);
        g2.drawLine(x1,height-y1-81,x2,height-y2-81);
        repaint();

    }

    void drawRectangleMouse(){
        System.out.println("Rysowanie prostokąta myszką");
    }

    void drawRectangleXY(int x1, int y1, int x2, int y2){
        if (g2 != null) {
        System.out.println("Rysowanie prostokąta XY z X1: " + x1 + " Y1: " + y1 + " do X2: " + x2 + " Y2: "+y2);
        g2.drawRect(x1,y1-81,abs(x1-x2),abs(y1-y2)-81);
        repaint();
        }
    }

    void drawCircleMouse(){
        System.out.println("Rysowanie kółka myszką");
    }
    void drawCircleXY(int x1, int y1, int x2, int y2){

        System.out.println("Rysowanie kółka XY z X1: " + x1 + " Y1: " + y1 + " R: " + x2);
        drawCircle( x1, y1, x2);
    }
    public void drawCircle(int x, int y, int r) {
        x = x-(r/2);
        y = y-(r/2);
        g2.drawOval(x,height - y-181,r,r);
        repaint();
    }
    void saveCanvas(){
        System.out.println("Zapisywanie");
    }

    void loadCanvas(){
        System.out.println("Wczytywanie");
    }



}
