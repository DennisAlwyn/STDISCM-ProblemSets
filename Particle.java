public class Particle {
    public double x, y; //Coordinates of particle
    private double vX, vY; //Velocity components
    private double xRef, yRef; //Coordinates of reflection
    private int waitTime; //How long should this particle wait before updating in nanoseconds
    private long lastUpdate; //System.nanotime of last time the particle was updated
    public int angle; //Angle that the particle is moving
    private boolean hasReflected; //Checks if particle has already reflected to prevent multiple relfections

    //Constructor
    public Particle(int xpos, int ypos, int speed, int theta){
        x = xpos;
        y = ypos;
        angle = theta;
        lastUpdate = 0;
        waitTime = 1000000000 / speed; //Divides 1 second in ns by speed to get movement speed
        hasReflected = false;

        //Set these to impossible coords so that it doesn't cause errors maybe...
        xRef = -420;
        yRef = -420;

        //Set velocity components
        double radAngle = Math.toRadians(angle);
        vX = Math.cos(radAngle); //Add cosine of angle to get x movement
        vY = Math.sin(radAngle)*-1; //Add sine of angle to get y movement. Multiple by -1 since x axis is flipped
    }

    public boolean move(){ //Method for moving the particle
        if((System.nanoTime() - lastUpdate) >= waitTime){ //If it is time for the particle to move
            //Set lastUpdate to nanotime
            lastUpdate = System.nanoTime();

            //Move particle to new pos
            x += vX;
            y += vY;

            if(Math.abs(xRef-Math.round(x)) >= 5 || Math.abs(yRef-Math.round(y)) >= 5){ //Check if the particle has moved away from the wall
                hasReflected = false; //Particle has moved sufficiently, no longer reflecting
                //Reset values
                xRef = -420;
                yRef = -420;
            }
            return true; //Particle moves
        }
        return false; //Particle does not move yet.
    }

    public void reflect(int wallAngle){ //Determines new angle on reflection
        if(!hasReflected){ //Particle has not yet reflected
            hasReflected = true; //Particle is now reflecting
            
            //Record reflection coordinates
            xRef = Math.round(x);
            yRef = Math.round(y);
            
            //Calc new vX and vY
            double radAngle = Math.toRadians(wallAngle);
            double nX = -Math.sin(radAngle);
            double nY = Math.cos(radAngle);
            double dotProduct = (vX*nX) + (vY*nY);
            vX = vX - 2 * dotProduct * nX;
            vY = vY -2 * dotProduct * nY;
        }
    }
}
