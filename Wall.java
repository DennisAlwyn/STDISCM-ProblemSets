public class Wall {
    public int x1, y1; //Start point
    public int x2, y2; //End point
    public int angle; //Angle of line (deg)

    public Wall(int sX, int sY, int eX, int eY){
        x1 = sX;
        y1 = sY;
        x2 = eX;
        y2 = eY;
        angle = (int)Math.toDegrees(Math.atan((y2-y1)/(x2-x1)));
    }

    public boolean hasCollided(Particle p){
        //Get rounded particle coords
        int pX = (int)Math.round(p.x);
        int pY = (int)Math.round(p.y);

        //Difference between particle and start point
        int pxs = pX - x1;
        int pys = pY - y1;

        //Difference between start and end
        int exs = x2 - x1;
        int eys = y2 - y1;

        //Determine if point is in line
        int crossProduct = (pxs * eys) - (pys * exs);

        if(Math.abs(crossProduct) < 0.1){ //Point lies on line
            //Check if point is in wall coords
            if(Math.abs(exs) >= Math.abs(eys)){
                return exs > 0 ? 
                    x1 <= pX && pX <= x2 :
                    x2 <= pX && pX <= x1;
            }
            else{
                return eys > 0 ? 
                    y1 <= pY && pY <= y2 :
                    y2 <= pY && pY <= y1;
            }
        }

        return false;
    }
}
