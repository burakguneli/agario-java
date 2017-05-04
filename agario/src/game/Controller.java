package game;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Random;

public class Controller {
    public ArrayList<Food> dots = new ArrayList<Food>();
    public ArrayList<PoisonedFood> Poisoneddots = new ArrayList<PoisonedFood>();
    MainCircle b = new MainCircle(50,50,40);
    GameFrame frame = new GameFrame("Agario");
    Label l = new Label("0");
    int mouseX = 0;
    int mouseY = 0;
    static int score = 0;
    static int xDis = 0;
    static int yDis = 0;
    int control_point = 0;
    public static void main(String[] args){
        new Controller().startGame();
    }
    public void startGame(){
        frame.addMouseMotionListener(new MyMouseMoveListener());
        frame.add(l,BorderLayout.NORTH);
        Refresh refresh = new Refresh();
        Thread t = new Thread(refresh);
        t.start();

        while(true){
            try{
                Random r = new Random();
                Thread.sleep(r.nextInt(100));

                double sizeIncrease = 20;
                b.x += xDis / sizeIncrease;
                b.y += yDis / sizeIncrease;

                while(control_point==0){
	                for(int i = 0; i < 30; i++) {

                        int randX = r.nextInt(1300);
                        int randY = r.nextInt(800);

                        Food d = new Food(randX, randY);
                        dots.add(d);
                        frame.add(d);
                    }
                    for(int i = 0; i < 5; i++){

	                	int randX = r.nextInt(1300);
	                    int randY = r.nextInt(800);

	                	PoisonedFood pd = new PoisonedFood(randX,randY);
                        Poisoneddots.add(pd);
	                	frame.add(pd);

	                    control_point = 1;
	                }
                }
                frame.repaint();
            }catch(Exception e){

            }
        }
    }
    class Refresh implements Runnable{
        public void run() {
            while(true){
                Rectangle r = new Rectangle(b.x,b.y,b.size,b.size);
                synchronized(dots){
                	for (int i=0; i < dots.size(); i++) {
                		Food d = dots.get(i);
                        Rectangle r1 = new Rectangle(d.x,d.y,d.size,d.size);
                        if(r1.intersects(r)){ //yeme kismi
                        	dots.remove(i);
                            b.size += 10;
                            l.setText(String.valueOf(Integer.parseInt(l.getText())+1));
                            score += 1;
                        }
                	}
                }
                synchronized(Poisoneddots){
	                for (int i=0; i < Poisoneddots.size(); i++) {
	            		PoisonedFood pd = Poisoneddots.get(i);
	                    Rectangle r1 = new Rectangle(pd.x,pd.y,pd.size,pd.size);
	                    if(r1.intersects(r)){ //poison yeme kismi
	                    	Poisoneddots.remove(i);
	                        b.size -= 15;
	                        l.setText(String.valueOf(Integer.parseInt(l.getText())+1));
	                        score -= 1;
	                    }
	            	}
                }
                frame.repaint();
            }
        }
    }
    class MyMouseMoveListener extends MouseMotionAdapter{
        public void mouseMoved(MouseEvent m){
            mouseX = m.getX();
            mouseY = m.getY();
            xDis = mouseX-b.x;
            yDis = mouseY-b.y;
        }
    }
    class GameFrame extends Frame{
		private static final long serialVersionUID = -7346138389831648823L;
        GameFrame(String s){
            super(s);
            setBounds(0,0,1400,1000);
            add(b);
            setVisible(true);
        }
        public void paint(Graphics g){
            b.paint(g);
            synchronized(dots){
                for(Food d : dots){
                	d.paint(g);
                }
            }
            synchronized(Poisoneddots){
	            for(PoisonedFood pd : Poisoneddots){
	            	pd.paint(g);
	            }
            }
        }
    }
}