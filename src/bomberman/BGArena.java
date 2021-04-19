package bomberman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BGArena extends JPanel {

    private final Image wall_img = new ImageIcon(getClass().getResource("/resources/plain_wall.jpg")).getImage();

    public BGArena() {
        setPreferredSize(new Dimension(700, 500));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //cornice
        int k = 100;
        g.setColor(new Color(k, k, k));
        for (int i = 0; i < 35; i++) {
            g.drawImage(wall_img, i * 20, 0, this);
            g.drawImage(wall_img, i * 20, 480, this);
            if (i < 25) {
                g.drawImage(wall_img, 0, i * 20, this);
                g.drawImage(wall_img, 680, i * 20, this);
            }
            
            //blocchi fissi
            if ((i % 2) == 0) {
                int a = i * 20;
                for (int j = 0; j < 25; j++) {
                    if (((j % 2) == 0)) {
                        int b = j * 20;
                        g.drawImage(wall_img, a, b, this);
                    }
                }
            }
        }
    }
}
