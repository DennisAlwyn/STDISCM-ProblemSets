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
    public static FPSPanel fpsCounter;

    //Threads
    public static Thread rendererThread;
    public static ArrayList<ParticleObject> particleThreads = new ArrayList<>();

    //FPS
    public static long lastFPSCheck = 0;
    public static int currentFPS = 0;
    public static int totalFrames = 0;

    //ArrayLists of walls and particles
    public static ArrayList<Particle> particles = new ArrayList<>();
    public static ArrayList<Wall> walls = new ArrayList<>();

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

        //Add FPS Panel
        fpsCounter = new FPSPanel();
        frame.add(fpsCounter);

        //TODO: Add Particle and wall creation Panels

        //Make Particle Threads
        for(int i = 0; i < 10; i++){
            particleThreads.add(new ParticleObject());
            particleThreads.get(i).start();
        }

        //Test particleObject Threads DEBUG
        for(int i = 0; i < 20000; i++){
            particles.add(new Particle(SIM_WIDTH/2, SIM_HEIGHT/2, 100, 2*i));
        }

        //TEST WALL DEBUG
        walls.add(new Wall(10, 10, 600, 600));

        int threadIndex = 0;
        int numParticles;
        while(true){ //Assigning loop
            if(particles.size() <= 0)
                continue; //Do not start if there are no particles

            numParticles = particles.size(); //Re-check if new particles have been added

            for(int i = 0; i < numParticles; i++){ //Loop through particles
                while(true){
                    if(threadIndex >= particleThreads.size()) //Reset at limit
                        threadIndex = 0;
                    if(particleThreads.get(threadIndex).isAvailable){ //Assign particle if thread available
                        particleThreads.get(threadIndex).particleIndex = i; //Assign particle
                        particleThreads.get(threadIndex).interrupt(); //Wake up thread
                        threadIndex++; //Increment index, thread is now occupied
                        break; //Move to next particle
                    }
                    threadIndex++; //Increment index, thread is already busy
                }
            }
        }
    }

    public static class FPSPanel extends JPanel{ //Displays FPS
        private final int W = 51, H = 25;

        public FPSPanel(){
            this.setBounds(10, 10, W, H);
        }

        public void paintComponent(Graphics g){
            //Call paintComponent from JPanel
            super.paintComponent(g);

            //Cast graphics to g2D
            Graphics2D g2D = (Graphics2D) g;

            //Clear screen
            g2D.setColor(Color.BLACK);
            g2D.fillRect(0, 0, W, H);

            //Draw FPS
            g2D.setColor(Color.RED);
            g2D.drawString("FPS: " + String.valueOf(currentFPS), 1, H-9);
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

            //Cast graphics to g2D
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

            int numWalls = walls.size();
            for(int i = 0; i < numWalls; i++){
                g2D.drawLine(walls.get(i).x1, walls.get(i).y1, walls.get(i).x2, walls.get(i).y2);
            }
        }
    }

    public static void fpsCounter(){
        //Display FPS
        if(System.nanoTime() > lastFPSCheck + 500000000){
            fpsCounter.repaint(); //Refresh fps
        }
        //Measure FPS
        if(System.nanoTime() > lastFPSCheck + 1000000000){
            lastFPSCheck = System.nanoTime();
            if(particles.size() > 0)
                currentFPS = (totalFrames / particles.size());
            totalFrames = 0;
        }
    }

    public static class rendererObject extends Thread{

        public void run(){
            while(true){
                //Repaint simulator
                simulator.repaint();
                fpsCounter();
            }
        }
    }

    public static class ParticleObject extends Thread{
        public boolean isAvailable = true; //Determines if thread is available
        public int particleIndex = 0;

        public void run(){
            while(true){
                isAvailable = true;
                try {
                    Thread.sleep(500000000*500000000*500000000);
                } catch (Exception e) {
                    isAvailable = false; //Thread is processing something

                    //Particle Moves
                    if(particles.size() > 0){ //Error prevention
                        boolean hasMoved = particles.get(particleIndex).move();
                        
                        if(hasMoved){ //Only do reflection checks if particle has moved
                            //Process Particle border reflections
                            if(particles.get(particleIndex).x >= SIM_WIDTH
                            || particles.get(particleIndex).x <= 0
                            || particles.get(particleIndex).y >= SIM_HEIGHT
                            || particles.get(particleIndex).y <= 0){
                                particles.get(particleIndex).reflect();
                            }

                            //TODO: Process Particle wall reflections
                            for(int i = 0; i < walls.size(); i++){
                                if(walls.get(i).hasCollided(particles.get(particleIndex))){ //If p hits wall
                                    particles.get(particleIndex).reflect();
                                    break; //Then stop checking
                                }
                            }
                            totalFrames++;
                        }
                    }
                }
            }
        }
    }
}