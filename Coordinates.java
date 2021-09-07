import java.io.Serializable;

public class Coordinates implements Serializable {
    private Integer x;
    private int y;

    public void setX(Integer x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getY() {
        return y;
    }
    public Integer getX() {
        return x;
    }
}
