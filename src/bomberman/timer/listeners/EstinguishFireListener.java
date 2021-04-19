package bomberman.timer.listeners;

import static bomberman.enums.CellType.FIRE;
import static bomberman.enums.CellType.FREE_ZONE;
import static bomberman.enums.CellType.GHOST_AND_FIRE;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bomberman.BombHolder;
import bomberman.GraphicLand;
import bomberman.Land;
import bomberman.enums.CellType;

public class EstinguishFireListener implements ActionListener {

    private final CellType[][] land;
    private final GraphicLand graphicLand;

    public EstinguishFireListener(CellType[][] land, GraphicLand graphicLand) {
        this.land = land;
        this.graphicLand = graphicLand;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int[] explosion = BombHolder.getBombHolder().getExplosionLocation();

        clearFire(explosion[0], explosion[1]);

        for (int i = 0; i <= explosion[2]; i++) {
            clearFire(explosion[0] + i, explosion[1]);
            clearFire(explosion[0] - i, explosion[1]);
            clearFire(explosion[0], explosion[1] + i);
            clearFire(explosion[0], explosion[1] - i);
        }

        graphicLand.setData(land);
        Land.decreaseBombs();
    }

    private void clearFire(int i, int j) {
        if (insideLand(i, j)) {
            CellType type = land[i][j];
            if (type == FIRE || type == GHOST_AND_FIRE) {
                land[i][j] = FREE_ZONE;
            }
        }
    }

    private boolean insideLand(int i, int j) {
        return i > 0 && i < land.length && j > 0 && j < land[0].length;
    }
}
