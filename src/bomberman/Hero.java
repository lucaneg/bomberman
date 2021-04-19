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
import static bomberman.enums.CellType.HERO;
import static bomberman.enums.CellType.HERO_AND_BOMB;
import static bomberman.enums.CellType.HERO_AND_FIRE;
import static bomberman.enums.CellType.HERO_AND_GHOST;
import static bomberman.enums.CellType.PORTAL;
import static bomberman.enums.CellType.PORTAL_AND_GHOST;
import static bomberman.enums.CellType.SPEED_UP;
import static bomberman.enums.CellType.WALL_BOMB;
import static bomberman.enums.CellType.WALL_FIRE;
import static bomberman.enums.CellType.WALL_SPEED;

import javax.swing.JOptionPane;

import bomberman.enums.CellType;
import bomberman.enums.Direction;

/**
 *
 * @author Luca
 */
public class Hero extends Character {

    private int currentRow;
    private int currentColumn;
    private Land land;
    
    public Hero(Land land) {
        this.currentRow = 1;
        this.currentColumn = 1;
        this.land = land;
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
                || (currentLand[b][a] == WALL_BOMB) || (currentLand[b][a] == WALL_FIRE)
                || (currentLand[b][a] == WALL_SPEED)) {
            return;
        }
        if (currentLand[b][a] == FIRE) {
            land.move(currentRow, currentColumn, b, a, HERO_AND_FIRE);
            currentColumn = a;
            currentRow = b;
            land.refresh();
            die();
            return;
        }
        if (currentLand[b][a] == GHOST) {
            land.move(currentRow, currentColumn, b, a, HERO_AND_GHOST);
            currentColumn = a;
            currentRow = b;
            land.refresh();
            die();
            return;
        }
        if (currentLand[b][a] == FREE_ZONE) {
            land.move(currentRow, currentColumn, b, a, HERO);
            currentColumn = a;
            currentRow = b;
            return;
        }
        if((currentLand[b][a] == FIRE_UP) || (currentLand[b][a] == BOMB_UP) || (currentLand[b][a] == SPEED_UP)) {
            land.catchBonus(currentLand[b][a]);
            land.move(currentRow, currentColumn, b, a, HERO);
            currentColumn = a;
            currentRow = b;  
            return;
        }
    }

    @Override
    public void die() {
        land.interruptGame();
        String message = "Sei morto!";
        message += "\nIniziare una nuova partita?";
        int choice = JOptionPane.showConfirmDialog(land.getGraphicLand(), message, "Game over", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            land.getArena().restart();
        } else {
            System.exit(0);
        }
    }

    public void dropBomb() {
        land.setBomb(currentRow, currentColumn);
    }
}
