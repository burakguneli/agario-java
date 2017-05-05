package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.Random;

public class Controller {
    public ArrayList<Food> dots = new ArrayList<Food>();
    public ArrayList<PoisonedFood> Poisoneddots = new ArrayList<PoisonedFood>();
    MainCircle hero = new MainCircle(650,450,40);
    MyFrame GameFrame = new MyFrame();
    JLabel label = new JLabel("0");
    JLabel userName = new JLabel();
    JLabel LabelSpeed = new JLabel();
    int mouseX = 0;
    int mouseY = 0;
    static int score = 0;
    static int xDistance = 0;
    static int yDistance = 0;
    double eatCount = 0.1;
    double speed = 40;
    double calculatedSpeed = 0;
    int control_point = 0;
    public static void main(String[] args){
        new Controller().startGame();
    }
    public void startGame(){
        String name = JOptionPane.showInputDialog(GameFrame, "What's your name?");
        userName.setText(name);
        LabelSpeed.setText(String.valueOf(calculatedSpeed));
        GameFrame.getContentPane().addMouseMotionListener(new MyMouseMoveListener());
        GameFrame.getContentPane().add(label,BorderLayout.NORTH);
        GameFrame.getContentPane().add(userName,BorderLayout.NORTH);
        GameFrame.getContentPane().add(LabelSpeed,BorderLayout.NORTH);
        Refresh refresh = new Refresh();
        Thread t = new Thread(refresh);
        t.start();
        while(true){
            try{
                Random r = new Random();
                Thread.sleep(r.nextInt(10));

                boolean control_point2 = hero.x <1300 && hero.x >0 && hero.y<850 && hero.y >0;
                if(control_point2 == true) {
                    hero.x += xDistance / speed / eatCount;
                    hero.y += yDistance / speed / eatCount;
                    calculatedSpeed = speed / eatCount;
                    LabelSpeed.setText(String.valueOf(calculatedSpeed));
                }else{
                    hero.x = 700;
                    hero.y = 400;
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

            }catch(Exception e){}
        }

    }
    class Refresh implements Runnable{
        public void run() {
            while(true){
                Rectangle blobsRectangle = new Rectangle(hero.x, hero.y, hero.size, hero.size);
                for (int i=0; i < dots.size(); i++) {
                    Food food = dots.get(i);
                    Rectangle dotsRectangle = new Rectangle(food.x, food.y, food.size, food.size);
                    if(dotsRectangle.intersects(blobsRectangle)){ //yeme kismi
                        dots.remove(i);
                        hero.size += 10;
                        label.setText(String.valueOf(Integer.parseInt(label.getText())+1));
                        score += 1;
                        eatCount +=0.2;

                        if(dots.size()==0){
                            JOptionPane.showMessageDialog(null, "Kazandiniz", "Oyun Sonu", JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);
                        }
                    }
                }
                for (int i=0; i < Poisoneddots.size(); i++) {
                    PoisonedFood pd = Poisoneddots.get(i);
                    Rectangle poisonedDotsRectangle = new Rectangle(pd.x,pd.y,pd.size,pd.size);
                    if(poisonedDotsRectangle.intersects(blobsRectangle)){ //poison yeme kismi
                        Poisoneddots.remove(i);
                        hero.size -= 30;

                        if(hero.size < 1){
                            JOptionPane.showMessageDialog(null, "Cok Fazla Poison Yedin", "Oyun Sonu", JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);
                        }

                        label.setText(String.valueOf(Integer.parseInt(label.getText())-1));
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
            xDistance = mouseX - hero.x;
            yDistance = mouseY - hero.y;
        }
    }

    public class MyFrame extends JFrame{

        public MyFrame(){
            super("Agario");

            setContentPane(new DrawPane());

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            setSize(1400, 900);

            add(hero);

            setVisible(true);
        }

        class DrawPane extends JPanel{
            public void paintComponent(Graphics g){
                hero.paint(g);
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