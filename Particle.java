public class Particle {
    public double x, y; //Coordinates of particle
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
    }

    public boolean move(){ //Method for moving the particle
        if((System.nanoTime() - lastUpdate) >= waitTime){ //If it is time for the particle to move
            //Set lastUpdate to nanotime
            lastUpdate = System.nanoTime();

            x += Math.cos(Math.toRadians(angle)); //Add cosine of angle to get x movement
            y += Math.sin(Math.toRadians(angle))*-1; //Add sine of angle to get y movement. Multiple by -1 since x axis is flipped

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

    public void reflect(){ //Determines new angle on reflection
        if(!hasReflected){ //Particle has not yet reflected
            hasReflected = true; //Particle is now reflecting
            
            //Record reflection coordinates
            xRef = Math.round(x);
            yRef = Math.round(y);
            
            if(angle == 0 || angle == 180 || angle == 90 || angle == 270)
                angle += 180;
            else
                angle += 270;
            if(angle >= 360) //Reduce by 1 full circle if angle >= 360
                angle -= 360;
            if(angle < 0)
                angle += 360;
        }
    }
}
