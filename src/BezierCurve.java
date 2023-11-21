import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class BezierCurve implements Serializable {



    LinkedList<Integer> pointsX = new LinkedList<>();
    LinkedList<Integer> pointsY = new LinkedList<>();

    int[] xDim = new int[60];
    int[] yDim = new int[60];


    public BezierCurve(){

    }


    public void addPoint(int x, int y){
        if(pointsX.size()<=1){
            pointsX.add(x);
            pointsY.add(y);
        }else{
            int tempX, tempY;
            int lastIndex=pointsX.size()-1;
            tempX=pointsX.get(lastIndex);
            tempY=pointsY.get(lastIndex);
            pointsX.remove(lastIndex);
            pointsY.remove(lastIndex);
            pointsX.add(tempX);
            pointsY.add(tempY);
            pointsX.add(x);
            pointsY.add(y);
        }
    }
    public void removePoint(int index){
        pointsX.remove(index);
        pointsY.remove(index);
    }








}
