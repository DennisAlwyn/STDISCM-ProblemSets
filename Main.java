import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    public static JPanel optionsPanel;

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
    //NOTE: Any wall or particle that is added to the above lists will automatically be rendered

    public static void main(String[] args){
        
        //Create Window with JFrame
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setLayout(null);
        frame.setTitle("Group 5 Particle Simulator | FPS: 0"); //Title displays fps
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
       

        //Add Simulator Panel
        simulator = new SimulatorPanel();
        frame.add(simulator);
        rendererThread = new rendererObject();
        rendererThread.start();//Start rendering thread

        optionsPanel = new JPanel();
        optionsPanel.setLayout(null);
        optionsPanel.setBounds(1300, 40, 400, 900);
        frame.add(optionsPanel);
     
        //Adding buttons
        JButton addLine = new JButton("Add Particles (Line)");
        JButton addArc = new JButton("Add Particles (Arc)");
        JButton addStagger = new JButton("Add Particles (Stagger)");
        JButton addWall = new JButton("Add Wall");

        ArrayList<JButton> pButtons = new ArrayList<>();
        pButtons.add(addLine);
        pButtons.add(addArc);
        pButtons.add(addStagger);
        pButtons.add(addWall);
        for(int i = 0; i < pButtons.size(); i++){
            pButtons.get(i).setBounds(0, 0+(190*i), 175, 20);
            optionsPanel.add(pButtons.get(i));
        }

         //Adding Line inputs
         ArrayList<JTextField> lineFields = new ArrayList<>();
         ArrayList<JLabel> lineLabels = new ArrayList<>();
         ArrayList<JTextField> lineFields2 = new ArrayList<>();
         ArrayList<JLabel> lineLabels2 = new ArrayList<>();
         JTextField lineX = new JTextField();
         JTextField lineY = new JTextField();
         JTextField lineSpeed = new JTextField();
         JTextField lineNum = new JTextField();
         JTextField lineStart = new JTextField();
         JTextField lineEnd = new JTextField();
         JTextField lineTheta = new JTextField();
         lineFields.add(lineX); lineFields.add(lineY); lineFields.add(lineSpeed); lineFields.add(lineNum); lineFields.add(lineTheta);
         lineFields2.add(lineStart); lineFields2.add(lineEnd);
         JLabel lineXText = new JLabel("Xi: ");
         JLabel lineYText = new JLabel("Yi: ");
         JLabel lineSText = new JLabel("V: ");
         JLabel lineNumText = new JLabel("N: ");
         JLabel lineEndXText = new JLabel("Xf: ");
         JLabel lineEndYText = new JLabel("Yf: ");
         JLabel lineThetaText = new JLabel("θ: ");
         lineLabels.add(lineXText); lineLabels.add(lineYText); lineLabels.add(lineSText); lineLabels.add(lineNumText); lineLabels.add(lineThetaText);
         lineLabels2.add(lineEndXText); lineLabels2.add(lineEndYText);
         //Column 1
         for(int i = 0; i < lineFields.size(); i++){
             //X, Y and Speed and number
             lineLabels.get(i).setBounds(0, 40+(25*i), 25, 20);
             lineFields.get(i).setBounds(25, 40+(25*i), 50, 20);
             optionsPanel.add(lineLabels.get(i));
             optionsPanel.add(lineFields.get(i));
         }
         //Column 2
         for(int i = 0; i < lineFields2.size(); i++){
             //Arc start and end
             lineLabels2.get(i).setBounds(85, 40+(25*i), 25, 20);
             lineFields2.get(i).setBounds(110, 40+(25*i), 50, 20);
             optionsPanel.add(lineLabels2.get(i));
             optionsPanel.add(lineFields2.get(i));
         }

        //Adding Arc inputs
        ArrayList<JTextField> arcFields = new ArrayList<>();
        ArrayList<JLabel> arcLabels = new ArrayList<>();
        ArrayList<JTextField> arcFields2 = new ArrayList<>();
        ArrayList<JLabel> arcLabels2 = new ArrayList<>();
        JTextField arcX = new JTextField();
        JTextField arcY = new JTextField();
        JTextField arcSpeed = new JTextField();
        JTextField arcNum = new JTextField();
        JTextField arcStart = new JTextField();
        JTextField arcEnd = new JTextField();
        arcFields.add(arcX); arcFields.add(arcY); arcFields.add(arcSpeed); arcFields.add(arcNum);
        arcFields2.add(arcStart); arcFields2.add(arcEnd);
        JLabel arcXText = new JLabel("X: ");
        JLabel arcYText = new JLabel("Y: ");
        JLabel arcSText = new JLabel("V: ");
        JLabel arcNumText = new JLabel("N: ");
        JLabel arcStartText = new JLabel("θi: ");
        JLabel arcEndText = new JLabel("θf: ");
        arcLabels.add(arcXText); arcLabels.add(arcYText); arcLabels.add(arcSText); arcLabels.add(arcNumText);
        arcLabels2.add(arcStartText); arcLabels2.add(arcEndText);
        //Column 1
        for(int i = 0; i < arcFields.size(); i++){
            //X, Y and Speed and number
            arcLabels.get(i).setBounds(0, 220+(25*i), 25, 20);
            arcFields.get(i).setBounds(25, 220+(25*i), 50, 20);
            optionsPanel.add(arcLabels.get(i));
            optionsPanel.add(arcFields.get(i));
        }
        //Column 2
        for(int i = 0; i < arcFields2.size(); i++){
            //Arc start and end
            arcLabels2.get(i).setBounds(85, 220+(25*i), 25, 20);
            arcFields2.get(i).setBounds(110, 220+(25*i), 50, 20);
            optionsPanel.add(arcLabels2.get(i));
            optionsPanel.add(arcFields2.get(i));
        }

        //Adding Stagger inputs
        ArrayList<JTextField> stagFields = new ArrayList<>();
        ArrayList<JLabel> stagLabels = new ArrayList<>();
        ArrayList<JTextField> stagFields2 = new ArrayList<>();
        ArrayList<JLabel> stagLabels2 = new ArrayList<>();
        JTextField stagX = new JTextField();
        JTextField stagY = new JTextField();
        JTextField stagTheta = new JTextField();
        JTextField stagNum = new JTextField();
        JTextField stagStart = new JTextField();
        JTextField stagEnd = new JTextField();
        stagFields.add(stagX); stagFields.add(stagY); stagFields.add(stagTheta); stagFields.add(stagNum);
        stagFields2.add(stagStart); stagFields2.add(stagEnd);
        JLabel stagXText = new JLabel("X: ");
        JLabel stagYText = new JLabel("Y: ");
        JLabel stagAText = new JLabel("θ: ");
        JLabel stagNumText = new JLabel("N: ");
        JLabel stagStartText = new JLabel("Vi: ");
        JLabel stagEndText = new JLabel("Vf: ");
        stagLabels.add(stagXText); stagLabels.add(stagYText); stagLabels.add(stagAText); stagLabels.add(stagNumText);
        stagLabels2.add(stagStartText); stagLabels2.add(stagEndText);
        //Column 1
        for(int i = 0; i < stagFields.size(); i++){
            //X, Y and Speed and number
            stagLabels.get(i).setBounds(0, 410+(25*i), 25, 20);
            stagFields.get(i).setBounds(25, 410+(25*i), 50, 20);
            optionsPanel.add(stagLabels.get(i));
            optionsPanel.add(stagFields.get(i));
        }
        //Column 2
        for(int i = 0; i < stagFields2.size(); i++){
            //Arc start and end
            stagLabels2.get(i).setBounds(85, 410+(25*i), 25, 20);
            stagFields2.get(i).setBounds(110, 410+(25*i), 50, 20);
            optionsPanel.add(stagLabels2.get(i));
            optionsPanel.add(stagFields2.get(i));
        }
        
        //Adding Wall inputs
        ArrayList<JLabel> wallLabels = new ArrayList<>();
        ArrayList<JTextField> wallFields = new ArrayList<>();
        JTextField wallX1 = new JTextField();
        JTextField wallX2 = new JTextField();
        JTextField wallY1 = new JTextField();
        JTextField wallY2 = new JTextField();
        wallFields.add(wallX1); wallFields.add(wallY1); wallFields.add(wallX2); wallFields.add(wallY2);
        JLabel wX1 = new JLabel("X1: ");
        JLabel wX2 = new JLabel("X2: ");
        JLabel wY1 = new JLabel("Y1: ");
        JLabel wY2 = new JLabel("Y2: ");
        wallLabels.add(wX1); wallLabels.add(wY1); wallLabels.add(wX2); wallLabels.add(wY2);
        for(int i = 0; i < wallFields.size(); i++){
            wallLabels.get(i).setBounds(0, 600+(25*i), 25, 20);
            wallFields.get(i).setBounds(25, 600+(25*i), 50, 20);
            optionsPanel.add(wallLabels.get(i));
            optionsPanel.add(wallFields.get(i));
        }

        //Make frame visible
        frame.setVisible(true);

        //Action Listeners
        addLine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numParticles;
                int startXPoint;
                int endXPoint;
                int startYPoint;
                int endYPoint;
                int speed;
                int theta;
                double uniDistance;

                //Crash due to invalid input prevention
                try {
                    numParticles = Integer.parseInt(lineNum.getText());
                    startXPoint = Integer.parseInt(lineX.getText());
                    endXPoint = Integer.parseInt(lineStart.getText());
                    startYPoint = Integer.parseInt(lineY.getText());
                    endYPoint = Integer.parseInt(lineEnd.getText());
                    uniDistance = Math.sqrt((endYPoint - startYPoint) * (endYPoint - startYPoint) + (endXPoint - startXPoint) * (endXPoint - startXPoint));
                    speed = Integer.parseInt(lineSpeed.getText());
                    theta = Integer.parseInt(lineTheta.getText());
                } catch (Exception ex) {
                    return;
                }

                double distance = uniDistance / ((double)numParticles);

                for(int i = 0; i < numParticles; i++) {
                    //Start from start angle and spiral
                    particles.add(new Particle(startXPoint + (distance * i), startYPoint+ (distance * i) , speed, theta));
                }
            }
        });

        //Arc action listener
        addArc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numParticles;
                int startAngle;
                int endAngle;
                int arcWidth;
                int x;
                int y;
                int v;

                //Crash due to invalid input prevention
                try {
                    numParticles = Integer.parseInt(arcNum.getText());
                    startAngle = Integer.parseInt(arcStart.getText());
                    endAngle = Integer.parseInt(arcEnd.getText());
                    arcWidth = endAngle - startAngle;
                    x = Integer.parseInt(arcX.getText());
                    y = Integer.parseInt(arcY.getText());
                    v = Integer.parseInt(arcSpeed.getText());
                } catch (Exception ex) {
                    return;
                }

                double anglePerIteration = arcWidth / ((double)numParticles);

                for(int i = 0; i < numParticles; i++) {
                    //Start from start angle and spiral
                    particles.add(new Particle(x, y, v, startAngle+(anglePerIteration*i)));
                }
            }
        });

        //Stagger Action Listener
        addStagger.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int numParticles;
                int startV;
                int endV;
                int x;
                int y;
                int t;

                //No crash due to invalid input please!!!
                try {
                    numParticles = Integer.parseInt(stagNum.getText());
                    startV = Integer.parseInt(stagStart.getText());
                    endV = Integer.parseInt(stagEnd.getText());
                    x = Integer.parseInt(stagX.getText());
                    y = Integer.parseInt(stagY.getText());
                    t = Integer.parseInt(stagTheta.getText());
                } catch (Exception ex) {
                    return;
                }

                int vDelta = endV - startV;
                double vDeltaPerIteration = vDelta / ((double)numParticles);

                for(int i = 0; i < numParticles; i++) {
                    particles.add(new Particle(x, y, startV+(vDeltaPerIteration*i), t));
                }
            }
        });

        addWall.addActionListener(new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                walls.add(new Wall(Integer.parseInt(wallX1.getText()), Integer.parseInt(wallY1.getText()), Integer.parseInt(wallX2.getText()), Integer.parseInt(wallY2.getText())));
            }
        });
        
        //Make Particle Threads
        for(int i = 0; i < 128; i++){
            particleThreads.add(new ParticleObject());
            particleThreads.get(i).start();
        }

        int threadIndex = 0;
        int numParticles;
        while(true){ //Assigning loop
            numParticles = particles.size(); //Re-check if new particles have been added
            System.out.println("Program is running..."); //NOTICE: LOAD BEARING PRINT STATEMENT. DO NOT REMOVE!!!

            if(numParticles <= 0)
                continue; //Do not start if there are no particles

            for(int i = 0; i < numParticles; i++){ //Loop through particles
                while(true){
                    if(threadIndex >= particleThreads.size()) //Reset at limit
                        threadIndex = 0;
                    if(particleThreads.get(threadIndex).isAvailable){ //Assign particle if thread available
                        particleThreads.get(threadIndex).particleIndex = i; //Assign particle
                        particleThreads.get(threadIndex).assignTask(); //Assign task to thread
                        threadIndex++; //Increment index, thread is now occupied
                        break; //Move to next particle
                    }
                    threadIndex++; //Increment index, thread is already busy
                }
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

            //Cast graphics to g2D
            Graphics2D g2D = (Graphics2D) g;

            //Clear screen
            g2D.setColor(Color.BLACK);
            g2D.fillRect(0, 0, SIM_WIDTH, SIM_HEIGHT);

            //Draw Particles
            g2D.setColor(Color.GREEN); //Set Particle color
            int tempX, tempY;
            int numParticles = particles.size();
            for(int i = 0; i < numParticles; i++){
                tempX = (int)Math.round(particles.get(i).x);
                tempY = (int)Math.round(particles.get(i).y);
                g2D.fillOval(tempX, tempY, 3, 3);
            }
            
            //Draw Walls
            g2D.setColor(Color.BLUE); //Set Wall color
            int numWalls = walls.size();
            for(int i = 0; i < numWalls; i++){
                g2D.drawLine(walls.get(i).x1, walls.get(i).y1, walls.get(i).x2, walls.get(i).y2);
            }

            totalFrames++; //Add to total frames since by this point, everything has been drawn

            //FPS limit: ~60fps
            try {
                Thread.sleep(1000/50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void fpsCounter(){
        //Measure FPS
        if(System.nanoTime() > lastFPSCheck + 500000000){
            lastFPSCheck = System.nanoTime();
            currentFPS = totalFrames * 4; //This is the closest match to the fps on the measurement tools
            totalFrames = 0;
            frame.setTitle("Group 5 Particle Simulator | FPS: " + String.valueOf(currentFPS)); //Update fps display
        }
    }

    public static class rendererObject extends Thread{

        public void run(){
            while(true){
                //Repaint simulator
                if(simulator != null)
                    simulator.repaint();
                fpsCounter();
            }
        }
    }

    public static class ParticleObject extends Thread{
        public boolean isAvailable = true; //Determines if thread is available
        public int particleIndex = 0;
        private Object waitLock = new Object();

        public void assignTask(){
            synchronized (waitLock){
                waitLock.notify();
            }
        }

        public void run(){
            while(true){
                isAvailable = true;
                synchronized (waitLock) {
                    try {
                        waitLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        continue;
                    }

                    isAvailable = false; //Thread is processing something

                    //Particle Moves
                    if(particles.size() > 0){ //Error prevention
                        boolean hasMoved = particles.get(particleIndex).move();
                        
                        if(hasMoved){ //Only do reflection checks if particle has moved
                            //Process Particle border reflections
                            if(particles.get(particleIndex).x >= SIM_WIDTH
                            || particles.get(particleIndex).x <= 0){
                                particles.get(particleIndex).reflect(90); //Flat vertical wall
                            }
                            else if(particles.get(particleIndex).y >= SIM_HEIGHT
                            || particles.get(particleIndex).y <= 0){
                                particles.get(particleIndex).reflect(180); //Flat horizontal wall
                            }

                            //Process Particle wall reflections
                            for(int i = 0; i < walls.size(); i++){
                                if(walls.get(i).hasCollided(particles.get(particleIndex))){ //If p hits wall
                                    particles.get(particleIndex).reflect(walls.get(i).angle);
                                    break; //Then stop checking
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}