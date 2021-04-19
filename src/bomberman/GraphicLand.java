/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bomberman;

import static bomberman.enums.CellType.BOMB;
import static bomberman.enums.CellType.BOMB_UP;
import static bomberman.enums.CellType.EXPLOSIVE_BLOCK;
import static bomberman.enums.CellType.FIRE;
import static bomberman.enums.CellType.FIRE_UP;
import static bomberman.enums.CellType.GHOST;
import static bomberman.enums.CellType.GHOST_AND_FIRE;
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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import bomberman.enums.CellType;

/**
 *
 * @author Luca
 */
public class GraphicLand extends JComponent {

    private CellType[][] land;
    private final Image bomb_img;
    private final Image ghost_img;
    private final Image wall_img;
    private final Image fire_img;
    private final Image hero_img;
    private final Image portal_img;
    private final Image bomb_up_img;
    private final Image fire_up_img;
    private final Image speed_up_img;

    public GraphicLand() {
        setPreferredSize(new Dimension(700, 500));
        setOpaque(false);
        bomb_img = new ImageIcon(getClass().getResource("/resources/bomb.png")).getImage();
        ghost_img = new ImageIcon(getClass().getResource("/resources/ghost.gif")).getImage();
        wall_img = new ImageIcon(getClass().getResource("/resources/wall.jpg")).getImage();
        fire_img = new ImageIcon(getClass().getResource("/resources/fire.png")).getImage();
        hero_img = new ImageIcon(getClass().getResource("/resources/hero.png")).getImage();
        portal_img = new ImageIcon(getClass().getResource("/resources/portal.png")).getImage();
        bomb_up_img = new ImageIcon(getClass().getResource("/resources/bomb_up.png")).getImage();
        fire_up_img = new ImageIcon(getClass().getResource("/resources/flame_up.png")).getImage();
        speed_up_img = new ImageIcon(getClass().getResource("/resources/speed_up.png")).getImage();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < land.length; i++) {
            for (int j = 0; j < land[i].length; j++) {
                if ((land[i][j] == EXPLOSIVE_BLOCK) || (land[i][j] == WALL_BOMB) || (land[i][j] == WALL_FIRE) || (land[i][j] == WALL_SPEED)) {
                    g.drawImage(wall_img, j * 20, i * 20, this);
                }
                if (land[i][j] == HERO) {
                    g.drawImage(hero_img, j * 20, i * 20, this);
                }
                if (land[i][j] == GHOST) {
                    g.drawImage(ghost_img, j * 20 + 2, i * 20 + 2, this);
                }
                if (land[i][j] == BOMB) {
                    g.drawImage(bomb_img, j * 20 + 2, i * 20 + 2, this);
                }
                if (land[i][j] == FIRE) {
                    g.drawImage(fire_img, j * 20, i * 20, this);
                }
                if (land[i][j] == HERO_AND_BOMB) {
                    g.drawImage(hero_img, j * 20, i * 20, this);
                    g.drawImage(bomb_img, j * 20 + 2, i * 20 + 2, this);
                }
                if (land[i][j] == HERO_AND_GHOST) {
                    g.drawImage(hero_img, j * 20, i * 20, this);
                    g.drawImage(ghost_img, j * 20 + 2, i * 20 + 2, this);
                }
                if (land[i][j] == HERO_AND_FIRE) {
                    g.drawImage(fire_img, j * 20, i * 20, this);
                    g.drawImage(hero_img, j * 20, i * 20, this);
                }
                if (land[i][j] == GHOST_AND_FIRE) {
                    g.drawImage(fire_img, j * 20, i * 20, this);
                    g.drawImage(ghost_img, j * 20 + 2, i * 20 + 2, this);
                }
                if (land[i][j] == PORTAL) {
                    g.drawImage(portal_img, j * 20, i * 20, this);
                }
                if (land[i][j] == PORTAL_AND_GHOST) {
                    g.drawImage(portal_img, j * 20, i * 20, this);
                    g.drawImage(ghost_img, j * 20 + 2, i * 20 + 2, this);
                }
                if (land[i][j] == BOMB_UP) {
                    g.drawImage(bomb_up_img, j * 20 + 2, i * 20 + 2, this);
                }
                if (land[i][j] == FIRE_UP) {
                    g.drawImage(fire_up_img, j * 20 + 2, i * 20 + 2, this);
                }
                if (land[i][j] == SPEED_UP) {
                    g.drawImage(speed_up_img, j * 20 + 2, i * 20 + 2, this);
                }

            }
        }


    }

    public void setData(CellType[][] land) {
        this.land = land;
        repaint();
    }
}
