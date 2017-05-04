package game;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.util.Random;

public class PoisonedFood extends Component{
	public int x;
    public int y;
    public Color c;
    public int size;
    PoisonedFood(int x, int y){
    	this.x = x;
        this.y = y;
        Random rand = new Random();
        int red = 0;
        int green = 0;
        int blue = 0;
        this.c = new Color(red,green,blue);
        this.size = 60;
    }
    public void paint(Graphics g){
        g.setColor(c);
        g.fillOval(this.x, this.y, this.size, this.size);
    }
}
