public class Wall {
    public int x1, y1; //Start point
    public int x2, y2; //End point

    public Wall(int sX, int sY, int eX, int eY){
        x1 = sX;
        y1 = sY;
        x2 = eX;
        y2 = eY;
    }

    public boolean hasCollided(Particle p){
        //Difference between particle and start point
        int pxs = (int)Math.round(p.x) - x1;
        int pys = (int)Math.round(p.y) - y1;

        //Difference between start and end
        int exs = x2 - x1;
        int eys = y2 - y1;

        //Determine if point is in line
        int crossProduct = (pxs * eys) - (pys * exs);

        if(Math.abs(crossProduct) < 0.1){ //Point lies on line
            //Check if point is in wall coords
            if(Math.abs(exs) >= Math.abs(eys)){
                return exs > 0 ? 
                    x1 <= p.x && p.x <= x2 :
                    x2 <= p.x && p.x <= x1;
            }
            else{
                return eys > 0 ? 
                    y1 <= p.y && p.y <= y2 :
                    y2 <= p.y && p.y <= y1;
            }
        }

        return false;
    }
}
