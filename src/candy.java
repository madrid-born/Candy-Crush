import javax.swing.*;
import java.awt.*;

public class candy{
    /*
    this is the which every candy of the game is an object of it
    each one has a place in array of the panel which is set to be "x" and "y" and a coordinate with "X" and "Y"
    a color and a type is the following property of each candy
    and more importantly an image that will appear in the panel
     */
    public int x;
    public int y;
    public int X;
    public int Y;
    public String type;
    public String color;
    public Image image;

    public candy(int x, int y, String color, String type) {
        this.x = x;
        this.y = y;
        this.X = x * 65;
        this.Y = y * 65;
        this.color = color;
        this.type = type;
        ImageIcon ii = new ImageIcon("pic//" + type + "-" + color + ".png");
        image = ii.getImage();
    }
}
//in this program we will never use the candy class, instead we use some inheritance of it as the following statements

class bomb extends candy {
    public bomb(int x, int y, String color) {
        super(x, y, color, "bomb");
    }
}

class vertical extends candy {
    public vertical(int x, int y, String color) {
        super(x, y, color, "vertical");
    }
}

class horizontal extends candy {
    public horizontal(int x, int y, String color) {
        super(x, y, color, "horizontal");
    }
}

class normal extends candy {
    public normal(int x, int y, String color) {
        super(x, y, color, "normal");
    }
}