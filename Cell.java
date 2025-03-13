enum CellEntityType {
    PLAYER, VOID, ENEMY, SANCTUARY, PORTAL
}

public class Cell {
    private int x;
    private int y;
    private CellEntityType type;
    private boolean visited;

    public Cell (int x, int y, CellEntityType type){
        this.x = x;
        this.y = y;
        this.type = type;
        this.visited = false;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public CellEntityType getType() {
        return type;
    }

    public boolean getVisited(){
        return visited;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
       this.y = y;
    }

    public void setType(CellEntityType type) {
        this.type = type;
    }

    public void setVisited(boolean visited){
        this.visited = visited;
    }

    // method that checks if there is an enemy
    public boolean checkEnemy() {
        if(type.equals(CellEntityType.ENEMY))
            return true;
        else
            return false;
    }

    // method that checks if there is a sanctuary
    public boolean checkSanctuary(){
        if(type.equals(CellEntityType.SANCTUARY))
            return true;
        else
            return false;
    }

    // method that checks if there is a portal
    public boolean checkPortal(){
        if(type.equals(CellEntityType.PORTAL))
            return true;
        else
            return false;
    }

    // method that checks if there is a void cell
    public boolean checkVoid(){
        if(type.equals(CellEntityType.VOID))
            return true;
        else
            return false;
    }

    @Override
    public String toString() {
        return "Cell:" + "x = " + x + ", y = " + y + ", type = " + type + ", visited = " + visited;
    }
}
