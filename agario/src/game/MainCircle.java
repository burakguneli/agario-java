package game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Random;

public class MainCircle extends Component{
    public int x;
    public int y;
    public int size;
    public static Color color;
    public static MainCircle blob = new MainCircle(650,450,40);
    MainCircle(int x, int y, int size){
        this.x = x;
        this.y = y;
        Random rand = new Random();
        int red = rand.nextInt(255);
        int green = rand.nextInt(255);
        int blue = rand.nextInt(255);
        this.color = new Color(red,green,blue);
        this.size = size;
    }
    public void paint(Graphics g){
        g.setColor(color);
        g.fillOval(x, y, size, size);
    }
}