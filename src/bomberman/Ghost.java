/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import static bomberman.enums.CellType.BLOCK;
import static bomberman.enums.CellType.BOMB;
import static bomberman.enums.CellType.BOMB_UP;
import static bomberman.enums.CellType.EXPLOSIVE_BLOCK;
import static bomberman.enums.CellType.FIRE;
import static bomberman.enums.CellType.FIRE_UP;
import static bomberman.enums.CellType.FREE_ZONE;
import static bomberman.enums.CellType.GHOST;
import static bomberman.enums.CellType.GHOST_AND_FIRE;
import static bomberman.enums.CellType.HERO;
import static bomberman.enums.CellType.HERO_AND_BOMB;
import static bomberman.enums.CellType.HERO_AND_GHOST;
import static bomberman.enums.CellType.PORTAL;
import static bomberman.enums.CellType.PORTAL_AND_GHOST;
import static bomberman.enums.CellType.SPEED_UP;
import static bomberman.enums.CellType.WALL_BOMB;
import static bomberman.enums.CellType.WALL_FIRE;
import static bomberman.enums.CellType.WALL_SPEED;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

/**
 *
 * @author Luca
 */
import bomberman.enums.CellType;
import bomberman.enums.Direction;

public class Ghost extends Character {

    private int currentRow;
    private int currentColumn;
    private static Land land;
    private static ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
    private Ghost thisGhost;
    private Direction currentDirection;
    private Timer t;
    private static Timer t2;

    public static ArrayList<Ghost> getGhostsAt(int row, int column) {
        ArrayList<Ghost> arr = new ArrayList<Ghost>();
        for (Ghost ghost : ghosts) {
            if ((ghost.currentColumn == column) && (ghost.currentRow == row)) {
                arr.add(ghost);
            }
        }
        return arr;
    }

    public static void startGhosts() {
        for (Ghost ghost : ghosts) {
            ghost.begin();
        }
        t2 = new Timer(40000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (ghosts.size() != 10) {
                    land.generateGhost();
                }
            }
        });
        t2.setRepeats(true);
        t2.start();

    }

    public static void stopGhosts() {
        for (Ghost ghost : ghosts) {
            ghost.suspend();
        }
        t2.stop();
    }

    public Ghost(int startRow, int startColumn, Land pLand) {
        this.currentRow = startRow;
        this.currentColumn = startColumn;
        land = pLand;
        Random r = new Random();
        this.currentDirection = Direction.fromInt(r.nextInt(4) + 1);
        ghosts.add(this);
        this.thisGhost = this;
        t = new Timer(700, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CellType[][] currentLand = land.getLand();
                int a = currentColumn;
                int b = currentRow;
                //decido se continuare nella stessa direzione o cambiare(il cambio può portare nella stessa direzione)
                Random r = new Random();
                if (r.nextDouble() < 0.7) {
                    //mantengo la direzione
                    switch (currentDirection) {
                        case UP:
                            b--;
                            break;
                        case DOWN:
                            b++;
                            break;
                        case LEFT:
                            a--;
                            break;
                        case RIGHT:
                            a++;
                            break;
                        default:
                            break;
                    }
                    int repetitions = 0;
                    while ((currentLand[b][a] == BLOCK) || (currentLand[b][a] == EXPLOSIVE_BLOCK)
                            || (currentLand[b][a] == BOMB) || (currentLand[b][a] == HERO_AND_BOMB)
                            || (currentLand[b][a] == PORTAL) || (currentLand[b][a] == PORTAL_AND_GHOST)
                            || (currentLand[b][a] == FIRE_UP) || (currentLand[b][a] == BOMB_UP)
                            || (currentLand[b][a] == SPEED_UP) || (currentLand[b][a] == WALL_BOMB)
                            || (currentLand[b][a] == WALL_FIRE) || (currentLand[b][a] == WALL_SPEED)) {
                        currentDirection = Direction.fromInt(r.nextInt(4) + 1);
                        a = currentColumn;
                        b = currentRow;
                        switch (currentDirection) {
                            case UP:
                                b--;
                                break;
                            case DOWN:
                                b++;
                                break;
                            case LEFT:
                                a--;
                                break;
                            case RIGHT:
                                a++;
                                break;
                            default:
                                break;
                        }

                        repetitions++;
                        if (repetitions == 5) {
                            break;
                        }
                    }
                } else {
                    //cambio di direzione
                    int repetitions = 0;
                    do {
                        currentDirection = Direction.fromInt(r.nextInt(4) + 1);
                        a = currentColumn;
                        b = currentRow;
                        switch (currentDirection) {
                            case UP:
                                b--;
                                break;
                            case DOWN:
                                b++;
                                break;
                            case LEFT:
                                a--;
                                break;
                            case RIGHT:
                                a++;
                                break;
                            default:
                                break;
                        }

                        repetitions++;
                        if (repetitions == 5) {
                            break;
                        }
                    } while ((currentLand[b][a] == BLOCK) || (currentLand[b][a] == EXPLOSIVE_BLOCK)
                            || (currentLand[b][a] == BOMB) || (currentLand[b][a] == HERO_AND_BOMB)
                            || (currentLand[b][a] == PORTAL) || (currentLand[b][a] == PORTAL_AND_GHOST)
                            || (currentLand[b][a] == FIRE_UP) || (currentLand[b][a] == BOMB_UP)
                            || (currentLand[b][a] == SPEED_UP) || (currentLand[b][a] == WALL_BOMB)
                            || (currentLand[b][a] == WALL_FIRE) || (currentLand[b][a] == WALL_SPEED));
                }
                thisGhost.move(currentDirection);
            }
        });
        t.setRepeats(true);
    }

    @Override
    public void move(Direction direction) {
        CellType[][] currentLand = land.getLand();
        int a = currentColumn;
        int b = currentRow;
        switch (direction) {
            case UP:
                b--;
                break;
            case DOWN:
                b++;
                break;
            case LEFT:
                a--;
                break;
            case RIGHT:
                a++;
                break;
            default:
                break;
        }
        if ((currentLand[b][a] == BLOCK) || (currentLand[b][a] == EXPLOSIVE_BLOCK)
                || (currentLand[b][a] == BOMB) || (currentLand[b][a] == HERO_AND_BOMB)
                || (currentLand[b][a] == PORTAL) || (currentLand[b][a] == PORTAL_AND_GHOST)
                || (currentLand[b][a] == FIRE_UP) || (currentLand[b][a] == BOMB_UP)
                || (currentLand[b][a] == SPEED_UP) || (currentLand[b][a] == WALL_BOMB)
                || (currentLand[b][a] == WALL_FIRE) || (currentLand[b][a] == WALL_SPEED)) {
            return;
        }
        if ((currentLand[b][a] == FIRE) || (currentLand[b][a] == GHOST_AND_FIRE)) {
            land.move(currentRow, currentColumn, b, a, GHOST_AND_FIRE);
            currentColumn = a;
            currentRow = b;
            land.refresh();
            die();
            return;
        }
        if (currentLand[b][a] == HERO) {
            land.move(currentRow, currentColumn, b, a, HERO_AND_GHOST);
            currentColumn = a;
            currentRow = b;
            land.refresh();
            land.getHero().die();
            return;
        }
        if ((currentLand[b][a] == FREE_ZONE) || (currentLand[b][a] == GHOST)) {
            land.move(currentRow, currentColumn, b, a, GHOST);
            currentColumn = a;
            currentRow = b;
        }
    }

    @Override
    public void die() {
        CellType[][] currentLand = land.getLand();
        ghosts.remove(thisGhost);
        currentLand[currentRow][currentColumn] = GHOST_AND_FIRE;
        land.refresh();
        t.stop();
        land.decreaseGhostNumber();
    }

    public void suspend() {
        t.stop();
    }

    public void begin() {
        t.start();
    }
}
