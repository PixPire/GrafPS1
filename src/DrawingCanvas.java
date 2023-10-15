import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.*;
import java.io.*;
import java.util.ArrayList;
import javax.sound.sampled.Line;
import javax.swing.*;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class DrawingCanvas extends JComponent{
    private int width = 1000;
    private int height = 500;

    private DrawnFigure selectedFigure = null;

    private int selectedFigureNumber;

    public int usageMode = 0; //0 -> idle, 1->draw Line, 2->draw rectangle, 3-> draw circle, 4-> resizeMouse, 5->resizeXY, 6-> moveMouse, 7-> moveXY
    private Boolean afterFirstClick = false;
    private int x1,y1,x2,y2;
    private int lastX=0;
    private int lastY=0;

    ArrayList<DrawnFigure> figures = new ArrayList<DrawnFigure>();


    // Image in which we're going to draw
    private Image image;
    // Graphics2D object ==> used to draw on
    private Graphics2D g2;
    // Mouse coordinates
    private int currentX, currentY, oldX, oldY;


    JTextField x1Text,y1Text,x2Text,y2Text;










    public DrawingCanvas(JLabel labelX, JLabel labelY){
        JFrame dialogFrame = new JFrame();
        JPanel XY1 = new JPanel();
        JButton DialogButton = new JButton("Potwierdź");
        dialogFrame.add(XY1);
        dialogFrame.setSize(500,100);


        JLabel infoLabel = new JLabel("info z danymi o obiekcie");

        x1Text=new JTextField("Wprowadź Nowe X");

        y1Text=new JTextField("Wprowadź Nowe Y");

        x2Text=new JTextField("Wprowadź nowe R");

        y2Text=new JTextField("Wprowadź nowe Y2");
        XY1.add(infoLabel);
        XY1.add(x1Text);
        XY1.add(y1Text);
        XY1.add(x2Text);
        XY1.add(y2Text);
        XY1.add(DialogButton);
        DialogButton.addActionListener(a -> confirmButtonPressed(selectedFigure));
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
               if(usageMode>0&&usageMode<4) {
                   if (!afterFirstClick) {
                       x1 = e.getX();
                       y1 = height - e.getY()-106;
                       System.out.println("Naciśnięto X1: " + x1 + " Y1: " + y1);
                       afterFirstClick = true;
                   } else {

                       x2 = e.getX();
                       y2 = height - e.getY()-106;
                       System.out.println("Naciśnięto X2: " + x2 + " Y2: " + y2);
                       afterFirstClick = false;
                       if (usageMode == 1) {
                           addLineXY(x1, y1, x2, y2);

                       }
                       if (usageMode == 2) {
                           addRectangleXY(x1, y1, x2, y2);

                       }
                       if (usageMode == 3) {
                           addCircleXY(x1,y1,2*(int)Math.sqrt((x1-currentX)*(x1-currentX)+(y1-currentY)*(y1-currentY)),currentY);

                       }

                   }
               }

                if(usageMode==4) {
                    if(!afterFirstClick) {
                        x1 = e.getX();
                        y1 = height - e.getY() - 106;
                        System.out.println("Naciśnięto X1: " + x1 + " Y1: " + y1);
                        searchFigure(x1, y1);
                        if (selectedFigure != null) afterFirstClick = true;
                    }else{
                        selectedFigure=null;
                        afterFirstClick=false;
                    }
                }

                if(usageMode==5) {
                    if(!afterFirstClick) {
                        x1 = e.getX();
                        y1 = height - e.getY() - 106;
                        System.out.println("Naciśnięto X1: " + x1 + " Y1: " + y1);
                        searchFigure(x1, y1);
                        if (selectedFigure != null) {
                            afterFirstClick = true;
                        }
                        if(afterFirstClick){
                            dialogFrame.setVisible(true);


                            if(usageMode==5&&selectedFigure!=null){
                                x1Text.setText("Rozciągnięcie w X");
                                y1Text.setText("Rozciągnięcie w Y");
                                x1Text.setVisible(true);
                                y1Text.setVisible(true);
                                x2Text.setVisible(false);
                                y2Text.setVisible(false);
                                for (DrawnFigure figure:figures
                                ) {if(figure==selectedFigure){

                                    if(selectedFigure.type==1){
                                        infoLabel.setText("Wybrano Linię o X1: "+ selectedFigure.x1 + " Y1: "+selectedFigure.y1+ " X2: "+selectedFigure.x2 + " Y2: " + selectedFigure.y2);
                                        x1Text.setText("Rozciągnięcie w X");
                                        y1Text.setText("Rozciągnięcie w Y");

                                    }
                                    if(selectedFigure.type==2){
                                        infoLabel.setText("Wybrano Prostokąt o X1: "+ selectedFigure.x1 + " Y1: "+selectedFigure.y1+ " X2: "+selectedFigure.x2 + " Y2: " + selectedFigure.y2);
                                        x1Text.setText("Rozciągnięcie w X");
                                        y1Text.setText("Rozciągnięcie w Y");
                                    }
                                    if(selectedFigure.type==3){
                                        infoLabel.setText("Wybrano Okrąg o X1: "+ selectedFigure.x1 + " Y1: "+selectedFigure.y1+ " R: "+selectedFigure.x2);
                                    x1Text.setText("Nowe R");
                                    y1Text.setVisible(false);
                                    x2Text.setVisible(false);
                                    y2Text.setVisible(false);


                                    }
                                    clear();
                                }

                                }
                            }
                        }
                        afterFirstClick=false;
                    }
                }
               if(usageMode==6) {
                   if(!afterFirstClick) {
                       x1 = e.getX();
                       y1 = height - e.getY() - 106;
                       System.out.println("Naciśnięto X1: " + x1 + " Y1: " + y1);
                       searchFigure(x1, y1);
                       if (selectedFigure != null) afterFirstClick = true;
                   }else{
                       selectedFigure=null;
                       afterFirstClick=false;
                   }
               }

                if(usageMode==7) {
                    if(!afterFirstClick) {
                        x1 = e.getX();
                        y1 = height - e.getY() - 106;
                        System.out.println("Naciśnięto X1: " + x1 + " Y1: " + y1);
                        searchFigure(x1, y1);
                        if (selectedFigure != null) {
                            afterFirstClick = true;
                        }
                        if(afterFirstClick){
                            dialogFrame.setVisible(true);


                            if(usageMode==7&&selectedFigure!=null){
                                x1Text.setVisible(true);
                                x2Text.setVisible(true);
                                x1Text.setText("Przeniesienie X");
                                y1Text.setText("Przeniesienie Y");
                                x2Text.setVisible(false);
                                y2Text.setVisible(false);
                                for (DrawnFigure figure:figures
                                ) {if(figure==selectedFigure){

                                    if(selectedFigure.type==1){
                                        infoLabel.setText("Wybrano Linię o X1: "+ selectedFigure.x1 + " Y1: "+selectedFigure.y1+ " X2: "+selectedFigure.x2 + " Y2: " + selectedFigure.y2);


                                    }
                                    if(selectedFigure.type==2){
                                        infoLabel.setText("Wybrano Prostokąt o X1: "+ selectedFigure.x1 + " Y1: "+selectedFigure.y1+ " X2: "+selectedFigure.x2 + " Y2: " + selectedFigure.y2);

                                    }
                                    if(selectedFigure.type==3){
                                        infoLabel.setText("Wybrano Okrąg o X1: "+ selectedFigure.x1 + " Y1: "+selectedFigure.y1+ " R: "+selectedFigure.x2);


                                    }
                                    clear();
                                }

                                }
                            }
                        }
                        afterFirstClick=false;
                    }
                }




            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                lastX=currentX;
                lastY=currentY;
                currentX=e.getX();
                labelX.setText("X: "+currentX);
                currentY=height-e.getY()-106;
                labelY.setText("Y: "+currentY);
                int differenceX=lastX-currentX;
                int differenceY=lastY-currentY;


                if(usageMode==1&& afterFirstClick){clear();
                drawLineXY(x1,y1,currentX,currentY);}

                if(usageMode==2&& afterFirstClick){clear();
                    drawRectangleXY(x1,y1,currentX,currentY);}

                if(usageMode==3&& afterFirstClick){clear();
                    drawCircleXY(x1,y1,2*(int)Math.sqrt((x1-currentX)*(x1-currentX)+(y1-currentY)*(y1-currentY)),currentY);}

                if(usageMode==4&&selectedFigure!=null){
                    for (DrawnFigure figure:figures
                    ) {if(figure==selectedFigure){

                       if(figure.type==1){
                           figure.x1=figure.x1-differenceX;
                           figure.y1=figure.y1-differenceY;
                           figure.x2=figure.x2+differenceX;
                           figure.y2=figure.y2+differenceY;
                       }
                       if(figure.type==2){
                           figure.x1=figure.x1+differenceX;
                           figure.y1=figure.y1-differenceY;
                           figure.x2=figure.x2-differenceX;
                           figure.y2=figure.y2+differenceY;
                       }

                        if(figure.type==3){
                            x1=figure.x1;
                            y1=figure.y1;
                            figure.x2=2*(int)Math.sqrt((x1-currentX)*(x1-currentX)+(y1-currentY)*(y1-currentY));
                        }


                        clear();
                        return;
                    }

                    }
                }


                if(usageMode==6&&selectedFigure!=null){
                    for (DrawnFigure figure:figures
                         ) {if(figure==selectedFigure){

                            figure.x1=figure.x1-differenceX;
                            figure.y1=figure.y1-differenceY;
                            if(figure.type!=3)figure.x2=figure.x2-differenceX;
                            figure.y2=figure.y2-differenceY;

                        clear();
                        return;
                    }

                    }
                }
                //System.out.println("X: " + currentX + " Y: " + currentY);
            }
        });
    }


    int MousePositionOnCanvasX(){
        return currentX;
    }

    int MousePositionOnCanvasY(){
        return currentY;
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
        paintAll();
        repaint();

    }


void paintAll(){
    for (DrawnFigure figure:figures
    ) {
        switch (figure.type) {
            case 1 -> drawLineXY(figure.x1, figure.y1, figure.x2, figure.y2);
            case 2 -> drawRectangleXY(figure.x1, figure.y1, figure.x2, figure.y2);
            case 3 -> drawCircleXY(figure.x1, figure.y1, figure.x2, figure.y2);
        }

    }
}


void WypiszAll(){
    for (DrawnFigure figure:figures
    ) {
        switch (figure.type) {
            case 1 -> System.out.print("Nr: " + figures.indexOf(figure)+ "typ: linia X1: " + figure.x1 + "Y1: "+figure.y1);
            case 2 -> System.out.print("Nr: " + figures.indexOf(figure)+ "typ: prostokąt X1: " + figure.x1 + "Y1: "+figure.y1);
            case 3 -> System.out.print("Nr: " + figures.indexOf(figure)+ "typ: okrąg X1: " + figure.x1 + "Y1: "+figure.y1);
        }

    }
}
    void drawLineMouse(){
        System.out.println("Rysowanie linii myszką");
        usageMode=1;
    }

    void addLineXY(int x1, int y1, int x2, int y2){
        figures.add(new DrawnFigure(1,x1,y1,x2,y2));
        System.out.println("dodano linie do bazy o x1: " + x1 + "y1: "+y1 +"x2: " + x2 + "y2: "+y2);

        DrawnFigure lastFigure = figures.get(figures.size()-1);
        System.out.println("X1: "+lastFigure.x1 + " Y1: " +lastFigure.y1);
        clear();
        WypiszAll();
    }
    void addRectangleXY(int x1, int y1, int x2, int y2){
        figures.add(new DrawnFigure(2,x1,y1,x2,y2));
        clear();
    }
    void addCircleXY(int x1, int y1, int x2, int y2){
        figures.add(new DrawnFigure(3,x1,y1,x2,y2));
        clear();
    }
    void drawLineXY(int x1, int y1, int x2, int y2){

        System.out.println("Rysowanie linii XY z X1: " + x1 + " Y1: " + y1 + " do X2: " + x2 + " Y2: "+y2);
        g2.drawLine(x1,height-y1-106,x2,height-y2-106);
        repaint();

    }

    void drawRectangleMouse(){
        System.out.println("Rysowanie prostokąta myszką");
        usageMode=2;
    }

    void drawRectangleXY(int x1, int y1, int x2, int y2){
        if (g2 != null) {
        System.out.println("Rysowanie prostokąta XY z X1: " + x1 + " Y1: " + y1 + " do X2: " + x2 + " Y2: "+y2 +"o szerokości: "+abs(x1-x2)+" o wysokości:"+abs(y1-y2));
        drawLineXY(x1,y1,x1,y2);
        drawLineXY(x1,y2,x2,y2);
        drawLineXY(x2,y2,x2,y1);
        drawLineXY(x2,y1,x1,y1);
        repaint();
        }
    }

    void drawCircleMouse(){
        System.out.println("Rysowanie kółka myszką");
        usageMode=3;
    }
    void drawCircleXY(int x1, int y1, int x2, int y2){

        System.out.println("Rysowanie kółka XY z X1: " + x1 + " Y1: " + y1 + " R: " + x2);
        drawCircle( x1, y1, x2);
    }
    public void drawCircle(int x, int y, int r) {
        x = x-(r/2);
        y = y+(r/2);
        g2.drawOval(x,height - y-105,r,r);
        repaint();
    }
    void saveCanvas(){
        try {

            FileOutputStream fos = new FileOutputStream("mydb.fil");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(figures);
            oos.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Zapisywanie");
        clear();

    }

    void loadCanvas(){
        try {
            FileInputStream fis = new FileInputStream("mydb.fil");
            ObjectInputStream ois = new ObjectInputStream(fis);
            figures = (ArrayList<DrawnFigure>)ois.readObject();
            ois.close();
        }
        catch (IOException e) {
            System.out.println("***catch ERROR***");
            e.printStackTrace();

        }
        catch (ClassNotFoundException e) {
            System.out.println("***catch ERROR***");
            e.printStackTrace();
        }
        System.out.println("Wczytywanie");
        clear();
    }

    void searchFigure(int x, int y){
        for (DrawnFigure figure:figures
             ) {
            System.out.println("Sprawdzam Figure o typie: "+figure.type +" X1: "+figure.x1 + " Y1: "+figure.y1+" X2: "+figure.x2 + " Y2: "+figure.y2 );
            System.out.println("Miejsce kliknięcia myszką X1:" + x + " Y1: "+y);
            if(figure.type==1){//line
                Line2D line = new Line2D.Double();
                line.setLine(figure.x1,figure.y1,figure.x2,figure.y2);
                if(line.ptSegDist(x,y)<=2){
                    selectedFigure=figure;

                    System.out.println("Znaleziono Linie!");
                    return;
                }
            }
            if(figure.type==2){//rectangle
                Line2D line1 = new Line2D.Double();
                Line2D line2 = new Line2D.Double();
                Line2D line3 = new Line2D.Double();
                Line2D line4 = new Line2D.Double();
                line1.setLine(figure.x1, figure.y1, figure.x1, figure.y2);
                line2.setLine(figure.x1, figure.y2, figure.x2, figure.y2);
                line3.setLine(figure.x2, figure.y2, figure.x2, figure.y1);
                line4.setLine(figure.x2, figure.y1, figure.x1, figure.y1);
                if((line1.ptSegDist(x,y)<=2)||(line2.ptSegDist(x,y)<=2)||(line3.ptSegDist(x,y)<=2)||(line4.ptSegDist(x,y)<=2)){
                    System.out.println("Znaleziono Prostokąt!");
                    selectedFigure=figure;
                    return;
                }
            }
            if(figure.type==3){//circle
                if(figure.x2+10>2*(int)Math.sqrt((x-figure.x1)*(x-figure.x1)+(y-figure.y1)*(y-figure.y1))&&figure.x2-10<2*(int)Math.sqrt((x-figure.x1)*(x-figure.x1)+(y-figure.y1)*(y-figure.y1))){
                    selectedFigure=figure;
                    System.out.println("Znaleziono Koło!");
                    return;
                }
            }
        }
        System.out.println("Nie znaleziono Figury!");
    }

    void confirmButtonPressed(DrawnFigure figure) {
        if (usageMode == 5 && figure != null) {
            for (DrawnFigure singleFigure : figures
            ) {
                if (figure == singleFigure) {
                    System.out.println("ROZCIĄGNIĘCIE XY");
                    if(singleFigure.type!=3) {
                        if(singleFigure.x1>singleFigure.x2){
                            singleFigure.x1 = singleFigure.x1 + Integer.parseInt(x1Text.getText());
                            singleFigure.x2 = singleFigure.x2 - Integer.parseInt(x1Text.getText());
                        }else{
                            singleFigure.x1 = singleFigure.x1 - Integer.parseInt(x1Text.getText());
                            singleFigure.x2 = singleFigure.x2 + Integer.parseInt(x1Text.getText());
                        }
                        if(singleFigure.y1>singleFigure.y2){
                            singleFigure.y1 = singleFigure.y1 + Integer.parseInt(y1Text.getText());
                            singleFigure.y2 = singleFigure.y2 - Integer.parseInt(y1Text.getText());
                        }else{
                            singleFigure.y1 = singleFigure.y1 - Integer.parseInt(y1Text.getText());
                            singleFigure.y2 = singleFigure.y2 + Integer.parseInt(y1Text.getText());
                        }


                    }else{
                        singleFigure.x2 = Integer.parseInt(x1Text.getText());

                    }
                    clear();
                }
            }
        }
        if (usageMode == 7 && figure != null) {
            for (DrawnFigure singleFigure : figures
            ) {
                if (figure == singleFigure) {
                    System.out.println("ZMIANA POZYCJI XY");
                    if(singleFigure.type!=3) {
                        singleFigure.x1 = singleFigure.x1 + Integer.parseInt(x1Text.getText());
                        singleFigure.y1 = singleFigure.y1 + Integer.parseInt(y1Text.getText());
                        singleFigure.x2 = singleFigure.x2 + Integer.parseInt(x1Text.getText());
                        singleFigure.y2 = singleFigure.y2 + Integer.parseInt(y1Text.getText());
                    }else{
                        singleFigure.x1 = singleFigure.x1 + Integer.parseInt(x1Text.getText());
                        singleFigure.y1 = singleFigure.y1 + Integer.parseInt(y1Text.getText());
                    }
                    clear();
                }
            }
        }
        selectedFigure=null;
    }
}
