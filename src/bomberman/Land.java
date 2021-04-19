package bomberman;

import static bomberman.enums.CellType.BLOCK;
import static bomberman.enums.CellType.BOMB;
import static bomberman.enums.CellType.EXPLOSIVE_BLOCK;
import static bomberman.enums.CellType.FREE_ZONE;
import static bomberman.enums.CellType.GHOST;
import static bomberman.enums.CellType.HERO;
import static bomberman.enums.CellType.HERO_AND_BOMB;
import static bomberman.enums.CellType.PORTAL;
import static bomberman.enums.CellType.PORTAL_AND_GHOST;
import static bomberman.enums.CellType.WALL_BOMB;
import static bomberman.enums.CellType.WALL_FIRE;

import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import bomberman.enums.CellType;
import bomberman.timer.listeners.BombTimerListener;

public class Land {

    private final CellType[][] land;
    private final GraphicLand graphicLand;
    private final Arena arena;
    private static int flame = 1;
    private static int maxBombs = 1;
    private static int bomb = 0;
    private int ghostNumber;
    private Hero hero;

    public Land(Arena arena) {
        land = new CellType[25][35];
        graphicLand = new GraphicLand();
        ghostNumber = 10;
        this.arena = arena;

        fillMatrixWithBasics();

        createRandomWall();

        createGhosts();

        clearStartingSpace();

        placeBonuses();

        graphicLand.setData(land);
    }

    public GraphicLand getGraphicLand() {
        return graphicLand;
    }

    private void fillMatrixWithBasics() {
        //azzero la matrice
        for (CellType[] row : land) {
            for (int j = 0; j < row.length; j++) {
                row[j] = FREE_ZONE;
            }
        }

        //salvo i bordi
        for (int i = 0; i < land[0].length; i++) {
            land[0][i] = BLOCK;
            land[land.length - 1][i] = BLOCK;
        }
        for (int i = 0; i < land.length; i++) {
            land[i][0] = BLOCK;
            land[i][land[i].length - 1] = BLOCK;
        }

        //salvo i blocchi indistruttibili
        for (int i = 0; i < land.length; i++) {
            if ((i % 2) == 0) {
                for (int j = 0; j < land[i].length; j++) {
                    if (((j % 2) == 0)) {
                        land[i][j] = BLOCK;
                    }
                }
            }
        }
    }

    private void createRandomWall() {
        for (CellType[] row : land) {
            for (int j = 0; j < row.length; j++) {
                if (row[j] == FREE_ZONE) {
                    row[j] = (Math.random() <= 0.4 ? EXPLOSIVE_BLOCK : FREE_ZONE);
                }
            }
        }
    }

    public Hero getHero() {
        return hero;
    }

    public CellType[][] getLand() {
        return land;
    }

    public void move(int currentRow, int currentColumn, int destinationRow, int destinationColumn, CellType type) {
        if ((land[currentRow][currentColumn] == HERO) || (land[currentRow][currentColumn] == GHOST)) {
            land[currentRow][currentColumn] = FREE_ZONE;
        }
        if (land[currentRow][currentColumn] == HERO_AND_BOMB) {
            land[currentRow][currentColumn] = BOMB;
        }
        if (land[currentRow][currentColumn] == PORTAL_AND_GHOST) {
            land[currentRow][currentColumn] = PORTAL;
        }
        land[destinationRow][destinationColumn] = type;
        graphicLand.setData(land);
    }

    private void clearStartingSpace() {
        //mi assicuro che rimanga lo spazio per muoversi all'inizio, e creo il personaggio
        land[1][1] = HERO;
        hero = new Hero(this);
        land[1][2] = FREE_ZONE;
        land[2][1] = FREE_ZONE;
        //libero anche lo spazio per portale e fantasmi
        land[land.length - 2][land[1].length - 2] = PORTAL;
        land[land.length - 2][land[1].length - 3] = FREE_ZONE;
        land[land.length - 2][land[1].length - 4] = FREE_ZONE;
        land[land.length - 3][land[1].length - 4] = FREE_ZONE;
        land[land.length - 4][land[1].length - 4] = FREE_ZONE;
        land[land.length - 4][land[1].length - 3] = FREE_ZONE;
        land[land.length - 4][land[1].length - 2] = FREE_ZONE;
        land[land.length - 3][land[1].length - 2] = FREE_ZONE;
    }

    private void placeBonuses() {
        int bonus = 0;
        while (bonus < 50) {
            Random r = new Random();
            int x = r.nextInt(35);
            int y = r.nextInt(25);
            if (land[y][x] == EXPLOSIVE_BLOCK) {
                if (bonus < 25) {
                    land[y][x] = WALL_BOMB;
                } else {
                    land[y][x] = WALL_FIRE;
                }
                bonus++;
            }
        }
    }

    private void createGhosts() {
        int createdGhosts = 0;
        Random generator = new Random();
        while (createdGhosts != ghostNumber) {

            int i = generator.nextInt(25);
            int j = generator.nextInt(35);
            //mi assicuro di lasciare lo spazio vuoto per poter iniziare
            if ((land[i][j] == FREE_ZONE) && ((i + j) > 4)) {
                land[i][j] = GHOST;
                new Ghost(i, j, this);
                createdGhosts++;
            }
        }
    }

    public void generateGhost() {
        land[land.length - 2][land[1].length - 2] = PORTAL_AND_GHOST;
        (new Ghost(land.length - 2, land[1].length - 2, this)).begin();
        ghostNumber++;

    }

    public void setBomb(int currentRow, int currentColumn) {
        if (bomb < maxBombs) {
            bomb++;
            land[currentRow][currentColumn] = HERO_AND_BOMB;
            graphicLand.setData(land);
            BombHolder.getBombHolder().addBombLocation(new int[]{currentRow, currentColumn, flame});
            Timer t = new Timer(3000, new BombTimerListener(land, graphicLand, hero));
            t.setRepeats(false);
            t.start();
        }
    }

    public void refresh() {
        graphicLand.setData(land);
    }

    public Arena getArena() {
        return arena;
    }

    public void interruptGame() {
        Ghost.stopGhosts();
    }

    public void startGame() {
        Ghost.startGhosts();
        arena.requestFocus();
    }

    public void decreaseGhostNumber() {
        ghostNumber--;
        if (ghostNumber == 0) {
            interruptGame();
            String message = "Hai vinto!";
            message += "\nIniziare una nuova partita?";
            int choice = JOptionPane.showConfirmDialog(graphicLand, message, "Vittoria", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                arena.restart();
            } else {
                System.exit(0);
            }
        }
    }

    public void increaseGhostNumber() {
        ghostNumber++;
    }

    void catchBonus(CellType type) {
        switch (type) {
            case BOMB_UP:
                maxBombs++;
                break;
            case FIRE_UP:
                flame++;
                break;
            default:
                break;
        }
    }
    
    public static void decreaseBombs(){
        bomb--;
    }

}
