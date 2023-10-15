import java.io.Serializable;

public class DrawnFigure implements Serializable {
    int type; //1 - line, 2-rectangle, 3-circle
    int x1, y1, x2, y2;


    public DrawnFigure(int type, int x1, int y1, int x2, int y2){

        this.type = type;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }


}
