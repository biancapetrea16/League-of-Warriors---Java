import java.util.ArrayList;
import java.util.Random;

class ImpossibleMoveException extends Exception{
    public ImpossibleMoveException(){
        super();
    }
    public ImpossibleMoveException(String msj) {
        super(msj);
    }
}

public class Grid {
    private ArrayList<ArrayList<Cell>> cells;
    private int length;
    private int width;
    private Cell currentCell;

    private Grid(int l, int w) {
        this.cells = new ArrayList<>();
        this.length = l;
        this.width = w;
    }

    public ArrayList<ArrayList<Cell>> getCells() {
        return cells;
    }

    public void setCells(ArrayList<ArrayList<Cell>> cells) {
        this.cells = cells;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    // method that generates a random grid between 3x3 - 10x10
    public static Grid generategrid(){
        Random random = new Random();

        int length = random.nextInt(8) + 3;
        int width = random.nextInt(8) + 3;

        Grid g = new Grid(length, width);
        g.addCell();
        return g;
    }

    // method that adds needed cells to the grid
    private void addCell() {
        Random random = new Random();

        // initialize the grid as void
        for (int i = 0; i < length; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(new Cell(i, j, CellEntityType.VOID));
            }
            cells.add(row);
        }

        // one player
        int playerX = random.nextInt(0, length);
        int playerY = random.nextInt(0, width);
        // access player's grid and mark its type
        currentCell = cells.get(playerX).get(playerY);
        currentCell.setType(CellEntityType.PLAYER);

        // minimum 2 sanctuaries
        Random rEntity = new Random();
        // i add maximum 4
        int iEntity = 2 + random.nextInt(3);
        for (int i = 0; i < iEntity; i++) {
            while (true) {
                int xEntity = random.nextInt(length);
                int yEntity = random.nextInt(width);
                Cell cellEntity = cells.get(xEntity).get(yEntity);
                if (cellEntity.getType() == CellEntityType.VOID) {
                    cellEntity.setType(CellEntityType.SANCTUARY);
                    break;
                }
            }
        }

        // minimum 4 enemies
        Random rEnemy = new Random();
        // i add maximum 8 enemies
        int iEnemy = 4 + random.nextInt(5);
        for (int i = 0; i < iEnemy; i++) {
            while (true) {
                int xEnemy = random.nextInt(length);
                int yEnemy = random.nextInt(width);
                Cell cellEntity = cells.get(xEnemy).get(yEnemy);
                if (cellEntity.getType() == CellEntityType.VOID) {
                    cellEntity.setType(CellEntityType.ENEMY);
                    break;
                }
            }
        }

        // one portal
        boolean portalExists = false;
        while (!portalExists) {
            int xPortal = random.nextInt(length);
            int yPortal = random.nextInt(width);
            Cell cellEntity = cells.get(xPortal).get(yPortal);
            // check if the cell is free, so i can put a portal there
            if (cellEntity.getType() == CellEntityType.VOID) {
                cellEntity.setType(CellEntityType.PORTAL);
                portalExists = true;
            }
        }
    }

    // move the player to the north cell if he can go there
    public Cell goNorth() throws ImpossibleMoveException {
        if(currentCell.getX() == 0)
            throw new ImpossibleMoveException("the character can't go NORTH");
        // set the cell where he was as void and as visited
        currentCell.setType(CellEntityType.VOID);
        currentCell.setVisited(true);
        // move the current cell wit tha player
        currentCell = cells.get(currentCell.getX() - 1).get(currentCell.getY());
        return currentCell;
}

    public Cell goSouth() throws ImpossibleMoveException {
        if(currentCell.getX() == length - 1)
            throw new ImpossibleMoveException("the character can't go SOUTH");
        currentCell.setType(CellEntityType.VOID);
        currentCell.setVisited(true);
        currentCell = cells.get(currentCell.getX() + 1).get(currentCell.getY());
        return currentCell;
    }

    public Cell goWest() throws ImpossibleMoveException {
        if(currentCell.getY() == 0)
            throw new ImpossibleMoveException("the character can't go WEST");
        currentCell.setType(CellEntityType.VOID);
        currentCell.setVisited(true);
        currentCell = cells.get(currentCell.getX()).get(currentCell.getY() - 1);
        return currentCell;
    }

    public Cell goEast() throws ImpossibleMoveException {
        if(currentCell.getY() == width - 1)
            throw new ImpossibleMoveException("the character can't go EAST");
        currentCell.setType(CellEntityType.VOID);
        currentCell.setVisited(true);
        currentCell = cells.get(currentCell.getX()).get(currentCell.getY() + 1);
        return currentCell;
    }

    // method that accesses current cell
    public Cell accessPlayerCell(){
        return cells.get(currentCell.getX()).get(currentCell.getY());
    }

    // methods that return north/south/west/east cell (method that i use when i
    // check if there is an enemy/portal/sanctuary
    public Cell accessNorthCell() throws ImpossibleMoveException {
        if(currentCell.getX() == 0)
            throw new ImpossibleMoveException("the character can't go NORTH");
        return cells.get(currentCell.getX() - 1).get(currentCell.getY());
    }

    public Cell accessSouthCell() throws ImpossibleMoveException {
        if(currentCell.getX() == length - 1)
            throw new ImpossibleMoveException("the character can't go SOUTH");
        return cells.get(currentCell.getX() + 1).get(currentCell.getY());
    }

    public Cell accessWestCell() throws ImpossibleMoveException {
        if(currentCell.getY() == 0)
            throw new ImpossibleMoveException("the character can't go WEST");
        return cells.get(currentCell.getX()).get(currentCell.getY() - 1);
    }

    public Cell accessEastCell() throws ImpossibleMoveException {
        if(currentCell.getY() == width - 1)
            throw new ImpossibleMoveException("the character can't go EAST");
        return cells.get(currentCell.getX()).get(currentCell.getY() + 1);
    }

    // methods that return true/false (check if i can go in that direction)
    public boolean canGoNorth(){
        if(currentCell.getX() == 0)
            return false;
        return true;
    }

    public boolean canGoSouth(){
        if(currentCell.getX() == length - 1)
            return false;
        return true;
    }

    public boolean canGoEast(){
        if(currentCell.getY() == width - 1)
            return false;
        return true;
    }

    public boolean canGoWest(){
        if(currentCell.getY() == 0)
            return false;
        return true;
    }

    // method that generates the test table
    public static Grid generateGridHardcodat(){
        int length = 5;
        int width = 5;

        Grid g = new Grid(length, width);
        g.addCellHardcodat();
        return g;
    }

    // method that add the cells from the test table
    public void addCellHardcodat(){
        for (int i = 0; i < length; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(new Cell(i, j, CellEntityType.VOID));
            }
            cells.add(row);
        }

        // player
        int playerX = 0;
        int playerY = 0;
        currentCell = cells.get(playerX).get(playerY);
        currentCell.setType(CellEntityType.PLAYER);

        // sanctuaries
        int scanctuar1X = 0;
        int scanctuar1Y = 3;
        Cell cellS1 = cells.get(scanctuar1X).get(scanctuar1Y);
        cellS1.setType(CellEntityType.SANCTUARY);
        int scanctuar2X = 1;
        int scanctuar2Y = 3;
        Cell cellS2 = cells.get(scanctuar2X).get(scanctuar2Y);
        cellS2.setType(CellEntityType.SANCTUARY);
        int scanctuar3X = 2;
        int scanctuar3Y = 0;
        Cell cellS3 = cells.get(scanctuar3X).get(scanctuar3Y);
        cellS3.setType(CellEntityType.SANCTUARY);
        int scanctuar4X = 4;
        int scanctuar4Y = 3;
        Cell cellS4 = cells.get(scanctuar4X).get(scanctuar4Y);
        cellS4.setType(CellEntityType.SANCTUARY);

        // portal
        int portalX = 4;
        int portalY = 4;
        Cell cellPortal = cells.get(portalX).get(portalY);
        cellPortal.setType(CellEntityType.PORTAL);

        // enemy
        int enemyX = 3;
        int enemyY = 4;
        Cell cellEnemy = cells.get(enemyX).get(enemyY);
        cellEnemy.setType(CellEntityType.ENEMY);
    }
}
