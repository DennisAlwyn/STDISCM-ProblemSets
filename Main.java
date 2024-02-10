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
        rendererThread = new rendererObject();
        rendererThread.start();//Start rendering thread

        //Test Particle Movement and Reflection
        particles.add(new Particle(100, 100, 1000, 1));
        while(true){
            particles.get(0).move();
            if(particles.get(0).x >= SIM_WIDTH || particles.get(0).x <= 0 || particles.get(0).y >= SIM_HEIGHT
            || particles.get(0).y <= 0)
                {
                    particles.get(0).reflect();
                    System.out.println(String.valueOf("Angle: " + particles.get(0).angle));
                }
        }
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

            //Draw Particles
            int tempX, tempY;
            int numParticles = particles.size();
            for(int i = 0; i < numParticles; i++){
                tempX = (int)Math.round(particles.get(i).x);
                tempY = (int)Math.round(particles.get(i).y);
                g2D.fillOval(tempX, tempY, 3, 3);
            }
        }
    }

    public static class rendererObject extends Thread{

        public void run(){
            while(true){
                //FPS Counter
                totalFrames++;
                if(System.nanoTime() > lastFPSCheck + 500000000){ //Checks every half second
                    lastFPSCheck = System.nanoTime();
                    currentFPS = totalFrames*2; //Since the check is in every 0.5s
                    totalFrames = 0;

                    //Display FPS
                    System.out.println("FPS: " + String.valueOf(currentFPS)); //DEBUG REMOVE LATER
                    //TODO: Display FPS in a counter somewhere later
                }

                //Repaint simulator
                simulator.repaint();

                //Cap FPS
                try {
                    Thread.sleep(1000/70);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
                
            }
        }
    }
}