import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Main{
    //Constants for sizes
    public static final int FRAME_WIDTH = 1600;
    public static final int FRAME_HEIGHT = 900;
    public static final int SIM_WIDTH = 1280;
    public static final int SIM_HEIGHT = 720;

    //JFrame
    public static JFrame frame;

    //JPanels
    public static SimulatorPanel simulator;

    //Threads
    public static Thread rendererThread;

    //FPS
    public static long lastFPSCheck = 0;
    public static int currentFPS = 0;
    public static int totalFrames = 0;

    //ArrayLists of walls and particles
    public static ArrayList<Particle> particles = new ArrayList<>();
    //TODO: Add walls later

    public static void main(String[] args){
        
        //Create Window with JFrame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Group 5 Particle Simulator");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setVisible(true);

        //Add Simulator Panel
        simulator = new SimulatorPanel();
        frame.add(simulator);
    }

    public static class SimulatorPanel extends JPanel{ //Class for the Particle simulator JPanel

        public SimulatorPanel(){
            this.setBackground(Color.BLACK);
            this.setBounds(10, FRAME_HEIGHT/2-SIM_HEIGHT/2-50, SIM_WIDTH, SIM_HEIGHT);
        }

        public void paintComponent(Graphics g){
            //Call paintComponent from JPanel
            super.paintComponent(g);

            //Cast grahics to g2D
            Graphics2D g2D = (Graphics2D) g;

            //Clear screen
            g2D.setColor(Color.BLACK);
            g2D.fillRect(0, 0, SIM_WIDTH, SIM_HEIGHT);

            //Set Draw Color to Green
            g2D.setColor(Color.GREEN);

            //Draw stuff. Remove Later
            g2D.drawLine(0, 0, 200, 200); //Wall Example
            g2D.drawOval(100, 10, 3, 3); //Particle Example
        }
    }

    public static class rendererObject extends Thread{

        public void run(){
            while(true){

            }
        }
    }
}