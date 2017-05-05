package game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Random;

public class Food extends Component{
    public int x;
    public int y;
    public Color c;
    public int size;
    Food(int x, int y){
        this.x = x;
        this.y = y;
        Random rand = new Random();
        int red = rand.nextInt(255);
        int green = rand.nextInt(255);
        int blue = rand.nextInt(255);
        this.c = new Color(red,green,blue);
        this.size = rand.nextInt(50);
        while(this.size<25){
            this.size = rand.nextInt(50);
        }
    }
    public void paint(Graphics g){
        g.setColor(c);
        g.fillOval(this.x, this.y, this.size, this.size);
    }
}