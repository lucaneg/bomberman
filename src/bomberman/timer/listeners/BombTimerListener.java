package bomberman.timer.listeners;

import static bomberman.enums.CellType.BOMB_UP;
import static bomberman.enums.CellType.FIRE;
import static bomberman.enums.CellType.FIRE_UP;
import static bomberman.enums.CellType.GHOST_AND_FIRE;
import static bomberman.enums.CellType.HERO_AND_FIRE;
import static bomberman.enums.CellType.SPEED_UP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import bomberman.BombHolder;
import bomberman.Ghost;
import bomberman.GraphicLand;
import bomberman.Hero;
import bomberman.enums.CellType;

public class BombTimerListener implements ActionListener {

    private final CellType[][] land;
    private final GraphicLand graphicLand;
    private final Hero hero;

    public BombTimerListener(CellType[][] land, GraphicLand graphicLand, Hero hero) {
        this.land = land;
        this.graphicLand = graphicLand;
        this.hero = hero;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] fire = BombHolder.getBombHolder().removeBombLocation();
        boolean up = true, down = true, left = true, right = true;
        
        inPlaceExplosion(fire[0], fire[1]);
        
        for (int i = 1; i <= fire[2]; i++) {
            if (up && insideLand(fire[0] + i, fire[1])) {
                up = propagateFire(fire[0] + i, fire[1]);
            }
            if (down && insideLand(fire[0] - i, fire[1])) {
                down = propagateFire(fire[0] - i, fire[1]);
            }
            if (left && insideLand(fire[0], fire[1] + i)) {
                left = propagateFire(fire[0], fire[1] + i);
            }
            if (right && insideLand(fire[0], fire[1] - i)) {
                right = propagateFire(fire[0], fire[1] - i);
            }
        }
        
        Timer t = new Timer(1000, new EstinguishFireListener(land, graphicLand));
        t.setRepeats(false);
        t.start();
    }

    private boolean insideLand(int i, int j) {
        return i > 0 && i < land.length && j > 0 && j < land[0].length;
    }

    private void inPlaceExplosion(int i, int j) {
        switch (land[i][j]) {
            case HERO:
            case HERO_AND_BOMB:
                land[i][j] = HERO_AND_FIRE;
                hero.die();
                break;
            case GHOST:
                land[i][j] = GHOST_AND_FIRE;
                Ghost.getGhostsAt(i, j).forEach((g) -> {
                    g.die();
                });
                break;
            case FREE_ZONE:
            case EXPLOSIVE_BLOCK:
            case BOMB:
                land[i][j] = FIRE;
                break;
            case WALL_BOMB:
                land[i][j] = BOMB_UP;
                break;
            case WALL_FIRE:
                land[i][j] = FIRE_UP;
                break;
            case WALL_SPEED:
                land[i][j] = SPEED_UP;
                break;
            default:
                break;
        }
        graphicLand.setData(land);
    }

    private boolean propagateFire(int i, int j) {
        boolean ret = true;
        switch (land[i][j]) {
            case HERO:
            case HERO_AND_BOMB:
                land[i][j] = HERO_AND_FIRE;
                hero.die();
                break;
            case GHOST:
                land[i][j] = GHOST_AND_FIRE;
                Ghost.getGhostsAt(i, j).forEach((g) -> {
                    g.die();
                });
                break;
            case FREE_ZONE:
                land[i][j] = FIRE;
                break;
            case EXPLOSIVE_BLOCK:
                land[i][j] = FIRE;
                ret = false;
                break;
            case WALL_BOMB:
                land[i][j] = BOMB_UP;
                ret = false;
                break;
            case WALL_FIRE:
                land[i][j] = FIRE_UP;
                ret = false;
                break;
            case WALL_SPEED:
                land[i][j] = SPEED_UP;
                ret = false;
                break;
            case BOMB:
            case BLOCK:
                ret = false;
                break;
            default:
                break;
        }
        graphicLand.setData(land);
        return ret;
    }
}
