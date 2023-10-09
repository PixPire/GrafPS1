
import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame f = new JFrame();


        f.setSize(1000,500);
        f.setTitle("Tutaj Była Zmiana");



        JPanel mainPanel = new JPanel();
        JPanel toolsPanel = new JPanel();
        JPanel canvasPanel = new JPanel();
        toolsPanel.setLayout(new BorderLayout());


        JToolBar toolBar = new JToolBar();
        toolsPanel.add(toolBar, BorderLayout.PAGE_START);

        JButton toolbarButton1 = new JButton();
        toolbarButton1.setIcon(new ImageIcon("LineMouse.gif"));

        JButton toolbarButton2 = new JButton();
        toolbarButton2.setIcon(new ImageIcon("CircleMouse.gif"));

        JButton toolbarButton3 = new JButton("Prost");
        toolbarButton3.setIcon(new ImageIcon("LineMouse.gif"));

        JButton toolbarButton4 = new JButton();
        toolbarButton4.setIcon(new ImageIcon("LineXY.gif"));

        JButton toolbarButton5 = new JButton("OkrągXY");
        toolbarButton5.setIcon(new ImageIcon("LineMouse.gif"));

        JButton toolbarButton6 = new JButton("ProstXY");
        toolbarButton6.setIcon(new ImageIcon("LineMouse.gif"));

        JButton toolbarButton7 = new JButton();
        toolbarButton7.setIcon(new ImageIcon("line.gif"));

        JButton toolbarButton8 = new JButton("Zapisz");
        toolbarButton8.setIcon(new ImageIcon("LineMouse.gif"));

        JButton toolbarButton9 = new JButton("Wczytaj");
        toolbarButton9.setIcon(new ImageIcon("LineMouse.gif"));


        toolBar.add(toolbarButton1);
        toolBar.add(toolbarButton2);
        toolBar.add(toolbarButton3);
        toolBar.add(toolbarButton4);
        toolBar.add(toolbarButton5);
        toolBar.add(toolbarButton6);
        toolBar.add(toolbarButton7);
        toolBar.add(toolbarButton8);
        toolBar.add(toolbarButton9);

        Container content = f.getContentPane();

        content.setLayout(new BorderLayout());
        DrawingCanvas dc = new DrawingCanvas();
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
                dc.drawCircleMouse();
                break;
            case 3:
                dc.drawRectangleMouse();
                break;
            case 4:
                f2.setVisible(true);

                break;
            case 5:
                x1Text.setText("Wprowadź X");
                y1Text.setText("Wprowadź Y");
                x2Text.setText("Wprowadź Średnicę");
                y2Text.setVisible(false);
                f2.setVisible(true);

                break;
            case 6:
                f2.setVisible(true);

                break;
            case 7:
                dc.saveCanvas();
                break;
            case 8:
                dc.loadCanvas();
                break;
            case 9:
                break;

        }
    }

    //Obsługa Dialog Boxa z podawaniem XY
    static void DialogButtonPressed(DrawingCanvas dc, int buttonNr, JTextField x1text, JTextField y1text, JTextField  x2text, JTextField  y2text){

        int x1 = Integer.parseInt(x1text.getText());
        int y1 = Integer.parseInt(y1text.getText());
        int x2 = Integer.parseInt(x2text.getText());
        int y2 = 0;
        if(buttonNr!=5){y2 = Integer.parseInt(y2text.getText());}


        switch (buttonNr){

            case 4:
                dc.drawLineXY(x1, y1, x2, y2);
                break;
            case 5:
                dc.drawCircleXY(x1, y1, x2, y2);
                break;
            case 6:
                dc.drawRectangleXY(x1, y1, x2, y2);
                break;
            case default:
                break;

        }

    }


}