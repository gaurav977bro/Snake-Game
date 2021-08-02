package basics.com;
import javax.swing.*;


public class S extends JFrame{

    S(){
        add(new B());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(310,300);
        setResizable(false);
        setLocationRelativeTo(null);


    }
    public static void main(String[] args){
        new S().setVisible(true);
    }
}