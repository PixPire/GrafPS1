
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Main {
    public static void main(String[] args) {

        JFrame f = new JFrame();


        f.setSize(1000,500);
        f.setTitle("Grafika Komputerowa Rysowanie Prymitywów");




        JPanel toolsPanel = new JPanel();

        toolsPanel.setLayout(new BorderLayout());


        JToolBar toolBar = new JToolBar();
        toolsPanel.add(toolBar, BorderLayout.PAGE_START);

        JButton toolbarButton1 = new JButton();
        toolbarButton1.setIcon(new ImageIcon("LineMouse.png"));

        JButton toolbarButton2 = new JButton();
        toolbarButton2.setIcon(new ImageIcon("RectangleMouse.png"));

        JButton toolbarButton3 = new JButton();
        toolbarButton3.setIcon(new ImageIcon("CircleMouse.png"));

        JButton toolbarButton4 = new JButton();
        toolbarButton4.setIcon(new ImageIcon("LineXY.png"));

        JButton toolbarButton5 = new JButton();
        toolbarButton5.setIcon(new ImageIcon("RectangleXY.png"));

        JButton toolbarButton6 = new JButton();
        toolbarButton6.setIcon(new ImageIcon("CircleXY.png"));

        JButton toolbarButton7 = new JButton();
        toolbarButton7.setIcon(new ImageIcon("ResizeMouse.png"));

        JButton toolbarButton8 = new JButton();
        toolbarButton8.setIcon(new ImageIcon("ResizeXY.png"));

        JButton toolbarButton9 = new JButton();
        toolbarButton9.setIcon(new ImageIcon("MoveMouse.png"));

        JButton toolbarButton10 = new JButton();
        toolbarButton10.setIcon(new ImageIcon("MoveXY.png"));

        JButton toolbarButton11 = new JButton();
        toolbarButton11.setIcon(new ImageIcon("Import.png"));

        JButton toolbarButton12 = new JButton();
        toolbarButton12.setIcon(new ImageIcon("Eksport.png"));


        toolBar.add(toolbarButton1);
        toolBar.add(toolbarButton2);
        toolBar.add(toolbarButton3);
        toolBar.add(toolbarButton4);
        toolBar.add(toolbarButton5);
        toolBar.add(toolbarButton6);
        toolBar.add(toolbarButton7);
        toolBar.add(toolbarButton8);
        toolBar.add(toolbarButton9);
        toolBar.add(toolbarButton10);
        toolBar.add(toolbarButton11);
        toolBar.add(toolbarButton12);

        JLabel labelX = new JLabel("X: ");
        JLabel labelY = new JLabel("Y: ");

        toolBar.add(labelX);
        toolBar.add(labelY);

        Container content = f.getContentPane();

        content.setLayout(new BorderLayout());
        DrawingCanvas dc = new DrawingCanvas(labelX,labelY);
        content.add(dc, BorderLayout.CENTER);
        content.add(toolsPanel, BorderLayout.NORTH);


        dc.setVisible(true);



        f.setVisible(true);

        toolbarButton1.addActionListener(e -> ButtonPressed(dc,1));
        toolbarButton2.addActionListener(e -> ButtonPressed(dc,2));
        toolbarButton3.addActionListener(e -> ButtonPressed(dc,3));
        toolbarButton4.addActionListener(e -> ButtonPressed(dc,4));
        toolbarButton5.addActionListener(e -> ButtonPressed(dc,5));
        toolbarButton6.addActionListener(e -> ButtonPressed(dc,6));
        toolbarButton7.addActionListener(e -> ButtonPressed(dc,7));
        toolbarButton8.addActionListener(e -> ButtonPressed(dc,8));
        toolbarButton9.addActionListener(e -> ButtonPressed(dc,9));
        toolbarButton10.addActionListener(e -> ButtonPressed(dc,10));
        toolbarButton11.addActionListener(e -> ButtonPressed(dc,11));
        toolbarButton12.addActionListener(e -> ButtonPressed(dc,12));



    }



    static void ButtonPressed(DrawingCanvas dc, int buttonNr){
        JFrame f2 = new JFrame();
        JPanel XY1 = new JPanel();
        JButton DialogButton = new JButton("Potwierdź");
        f2.add(XY1);
        f2.setSize(400,100);
        System.out.println("Wciśnięto przycisk nr " + buttonNr);

        JTextField x1Text,y1Text,x2Text,y2Text;

        x1Text=new JTextField("Wprowadź X1");

        y1Text=new JTextField("Wprowadź Y1");

        x2Text=new JTextField("Wprowadź X2");

        y2Text=new JTextField("Wprowadź Y2");

        XY1.add(x1Text);
        XY1.add(y1Text);
        XY1.add(x2Text);
        XY1.add(y2Text);
        XY1.add(DialogButton);
        DialogButton.addActionListener(e -> DialogButtonPressed(dc,buttonNr,x1Text,y1Text,x2Text,y2Text ));

        switch (buttonNr){
            case 1:
                dc.drawLineMouse();
                break;
            case 2:
                dc.drawRectangleMouse();
                break;
            case 3:
                dc.drawCircleMouse();
                break;
            case 4:
                f2.setVisible(true);
                break;
            case 5:
                f2.setVisible(true);
                break;
            case 6:
                x1Text.setText("Wprowadź X");
                y1Text.setText("Wprowadź Y");
                x2Text.setText("Wprowadź Średnicę");
                y2Text.setVisible(false);
                f2.setVisible(true);
                break;
            case 7:
                dc.usageMode=4;
                break;
            case 8:
                dc.usageMode=5;
                break;
            case 9:
                dc.usageMode=6;
                break;
            case 10:
                dc.usageMode=7;
                break;
            case 11:
                dc.loadCanvas();
                break;
            case 12:
                dc.saveCanvas();
                break;

        }
    }

    //Obsługa Dialog Boxa z podawaniem XY
    static void DialogButtonPressed(DrawingCanvas dc, int buttonNr, JTextField x1text, JTextField y1text, JTextField  x2text, JTextField  y2text){

        int x1 = Integer.parseInt(x1text.getText());
        int y1 = Integer.parseInt(y1text.getText());
        int x2 = Integer.parseInt(x2text.getText());
        int y2 = 0;
        if(buttonNr!=6){y2 = Integer.parseInt(y2text.getText());}


        switch (buttonNr){
            case 1:
                dc.usageMode=1;
                break;
            case 2:
                dc.usageMode=2;
                break;
            case 3:
                dc.usageMode=3;
                break;
            case 4:
                dc.usageMode=0;
                dc.addLineXY(x1, y1, x2, y2);
                break;
            case 5:
                dc.usageMode=0;
                dc.addRectangleXY(x1, y1, x2, y2);
                break;
            case 6:
                dc.usageMode=0;
                dc.addCircleXY(x1, y1, x2, y2);
                break;
            case 7:
                dc.usageMode=4;
                break;
            case 8:
                dc.usageMode=5;
                break;
            case 9:
                dc.usageMode=6;
                break;
            case 10:
                dc.usageMode=7;
                break;
            case 11, 12:
                dc.usageMode=0;
                break;
            case default:
                break;
        }

    }


}