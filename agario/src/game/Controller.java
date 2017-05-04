package game;

import javax.swing.*;
import java.awt.BorderLayout;
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
    MainCircle b = new MainCircle(650,450,40);
    MyFrame GameFrame = new MyFrame();
    Label l = new Label("0");
    int mouseX = 0;
    int mouseY = 0;
    static int score = 0;
    static int xDis = 0;
    static int yDis = 0;
    double speed = 40;
    int control_point = 0;
    public static void main(String[] args){
        new Controller().startGame();
    }
    public void startGame(){
        GameFrame.getContentPane().addMouseMotionListener(new MyMouseMoveListener());
        GameFrame.getContentPane().add(l,BorderLayout.NORTH);
        Refresh refresh = new Refresh();
        Thread t = new Thread(refresh);
        t.start();

        while(true){
            try{
                Random r = new Random();
                Thread.sleep(r.nextInt(10));

                boolean control_point2 = b.x <1300 && b.x >0 && b.y<850 && b.y >0;
                if(control_point2 == true) {
                    b.x += xDis / speed;
                    b.y += yDis / speed;
                }else{
                    b.x = 750;
                    b.y = 450;
                }

                while(control_point==0){
	                for(int i = 0; i < 30; i++) {

                        int randX = r.nextInt(1300);
                        int randY = r.nextInt(800);

                        Food food = new Food(randX, randY);
                        dots.add(food);
                        GameFrame.getContentPane().add(food);
                    }
                    for(int i = 0; i < 5; i++){

	                	int randX = r.nextInt(1300);
	                    int randY = r.nextInt(800);

	                	PoisonedFood pd = new PoisonedFood(randX,randY);
                        Poisoneddots.add(pd);
                        GameFrame.getContentPane().add(pd);

	                    control_point = 1;
	                }
                }
                GameFrame.getContentPane().repaint();
            }catch(Exception e){}
        }
    }
    class Refresh implements Runnable{
        public void run() {
            while(true){
                Rectangle blobsRectangle = new Rectangle(b.x, b.y, b.size, b.size);
                for (int i=0; i < dots.size(); i++) {
                    Food food = dots.get(i);
                    Rectangle dotsRectangle = new Rectangle(food.x, food.y, food.size, food.size);
                    if(dotsRectangle.intersects(blobsRectangle)){ //yeme kismi
                        dots.remove(i);
                        b.size += 10;
                        l.setText(String.valueOf(Integer.parseInt(l.getText())+1));
                        score += 1;
                    }
                }
                for (int i=0; i < Poisoneddots.size(); i++) {
                    PoisonedFood pd = Poisoneddots.get(i);
                    Rectangle poisonedDotsRectangle = new Rectangle(pd.x,pd.y,pd.size,pd.size);
                    if(poisonedDotsRectangle.intersects(blobsRectangle)){ //poison yeme kismi
                        Poisoneddots.remove(i);
                        b.size -= 15;
                        l.setText(String.valueOf(Integer.parseInt(l.getText())+1));
                        score -= 1;
                    }
                }
                GameFrame.getContentPane().repaint();
            }
        }
    }
    class MyMouseMoveListener extends MouseMotionAdapter{
        public void mouseMoved(MouseEvent m){
            mouseX = m.getX();
            mouseY = m.getY();
            xDis = mouseX - b.x;
            yDis = mouseY - b.y;

        }
    }/*
    class MainFrame extends Frame{
		private static final long serialVersionUID = -7346138389831648823L;
        MainFrame(String s){
            super(s);
            setBounds(0,0,1400,1000);
            add(b);
            setVisible(true);
        }
        public void paint(Graphics g){
            b.paint(g);
            l.paint(g);
            synchronized(dots){
                for(Food food : dots){
                    food.paint(g);
                }
            }
            synchronized(Poisoneddots){
	            for(PoisonedFood pd : Poisoneddots){
	            	pd.paint(g);
	            }
            }
        }
    }*/

    public class MyFrame extends JFrame{

        public MyFrame(){
            super("Agario");

            setContentPane(new DrawPane());

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            setSize(1400, 900);

            add(b);

            setVisible(true);
        }

        class DrawPane extends JPanel{
            public void paintComponent(Graphics g){
                b.paint(g);
                synchronized(dots){
                    for(Food food : dots){
                        food.paint(g);
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
}