import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main{
    //Constants for sizes
    public static final int FRAME_WIDTH = 1280;
    public static final int FRAME_HEIGHT = 720;
    public static final int SIZE = 650;

    //JPanels
    public static SimulatorPanel simulator;

    //ArrayLists of walls and particles
    public static ArrayList<Particle> particles = new ArrayList<>();
    //TODO: Add walls later

    public static void main(String[] args){
        
        //Create Window with JFrame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Group 5 Particle Simulator");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);

        //ADD PANELS
        simulator = new SimulatorPanel();
        frame.add(simulator);
    }

    public static class SimulatorPanel extends JPanel{ //Class for the Particle simulator JPanel

        public SimulatorPanel(){
            this.setBackground(Color.BLACK);
            this.setBounds(50, FRAME_HEIGHT/2-SIZE/2-25, SIZE, SIZE);
        }

        public void paintComponent(Graphics g){
            //Call paintComponent from JPanel
            super.paintComponent(g);

            //Cast grahics to g2D
            Graphics2D g2D = (Graphics2D) g;

            //Set Draw Color to Green
            g2D.setColor(Color.GREEN);

            //Draw stuff. Remove Later
            g2D.drawLine(0, 0, 200, 200); //Wall Example
            g2D.drawOval(100, 10, 3, 3); //Particle Example
        }
    }
}