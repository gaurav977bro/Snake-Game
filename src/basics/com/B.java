package basics.com;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;

public class B extends JPanel implements ActionListener{
    private boolean inGame = true;
    private int key;
    
    private Image apple;
    private Image head;
    private Image dot;

    private int dots;
    private int all_dots = 900;
    private int dot_size = 10;
    private int[] x = new int[all_dots];
    private int[] y = new int[all_dots];

    private int rand;
    private int rand_difference = 25;
    
    private int apple_x;
    private int apple_y;
    
    private Timer timer;

    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean rightDirection = true;
    private boolean leftDirection = false;
    
    

    B(){

        addKeyListener(new TAdapter());
        setFocusable(true);
        setPreferredSize(new Dimension(300,300));
        setBackground(Color.BLACK);
        loadImage();
        init();

    }
    public void loadImage(){
        ImageIcon  a = new ImageIcon(ClassLoader.getSystemResource("basics/com/icons/apple.png"));
        apple = a.getImage();
        ImageIcon h = new ImageIcon(ClassLoader.getSystemResource("basics/com/icons/head.png"));
        head = h.getImage();
        ImageIcon d = new ImageIcon(ClassLoader.getSystemResource("basics/com/icons/dot.png"));
        dot = d.getImage();
    }
    public void init(){
        dots = 3;
        for(int z = 0; z<dots; z++){
            x[z] = 50 - z*dot_size;
            y[z] = 50;
        }
        locateApple();
        timer = new Timer(250,this);
        timer.start();
    }
    public void locateApple(){
        rand = (int)(Math.random()*rand_difference);
        apple_x = rand * dot_size;
        rand = (int)(Math.random()*rand_difference);
        apple_y = rand * dot_size;

    }
    public void checkApple(){
        if((x[0] == apple_x) && (y[0]==apple_y)){
            dots++;
            locateApple();
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(inGame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int z = 0; z < dots; z++) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this);
                }
                else {
                    g.drawImage(dot, x[z], y[z], this);
                }

            }
            Toolkit.getDefaultToolkit().sync();
        }
        else{
            gameOver(g);

        }

    }

    public void gameOver(Graphics g){
        String msg = "Game Over";
        Font font = new Font("SAN_SERIF",Font.BOLD,14);
        FontMetrics metrics = getFontMetrics(font);
        g.setFont(font);
        g.setColor(Color.WHITE);
        g.drawString(msg,(300 - metrics.stringWidth(msg))/2, 300/2);
    }

    public void checkCollision(){
        //IF SNAKE TOUCHES ITSELF
        for(int z = dots; z>0; z--){
            if((z >4) && (x[0] == x[z]) && (y[0] == y[z])){
                inGame = false;
            }
        }
        if(x[0]>=290){
            inGame = false;
        }
        if(x[0]<=3){
            inGame = false;
        }
        if(y[0]>290){
            inGame = false;
        }
        if(y[0]<=3){
            inGame = false;
        }
        if(!inGame){
        timer.stop();
        }
    }
    public void move(){
        for(int z = dots ; z>0; z--){
            x[z] = x[z-1];
            y[z] = y[z-1];
        }
        if(rightDirection){
            x[0] += dot_size;

        }
        if(leftDirection){
                x[0]-=dot_size;

        }
        if(upDirection){
            y[0] -= dot_size;

        }
        if(downDirection){
            y[0]+=dot_size;

        }
    }

    public void actionPerformed(ActionEvent ae){
        if(inGame){
            checkApple();
            checkCollision();
            move();

        }
        repaint();

    }
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);

            key = e.getKeyCode();

            if(key == KeyEvent.VK_RIGHT && (!leftDirection)){
                rightDirection = true;
                upDirection = false;
                downDirection = false;

            }
            if(key == KeyEvent.VK_LEFT && (!rightDirection)){
                leftDirection = true;
                upDirection = false;
                downDirection = false;

            }
            if(key == KeyEvent.VK_UP && (!downDirection)){
                rightDirection = false;
                upDirection = true;
                leftDirection = false;

            }
            if(key == KeyEvent.VK_DOWN && (!upDirection)){
                rightDirection = false;
                leftDirection = false;
                downDirection = true;

            }

        }

    }


}