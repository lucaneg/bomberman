package bomberman;

import static bomberman.enums.Direction.DOWN;
import static bomberman.enums.Direction.LEFT;
import static bomberman.enums.Direction.RIGHT;
import static bomberman.enums.Direction.UP;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class Arena extends JFrame implements KeyListener {

    private BGArena arena;
    private GraphicLand graphicLand;
    private JLayeredPane layeredPane;
    private Land land;
    private Welcome welcome;
    private boolean started;

    public Arena() {
        super("Bomberman");
        started = false;
        layeredPane = getLayeredPane();
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(100, 100);
        setSize(706, 525);
        addKeyListener(this);

        //sfondo
        arena = new BGArena();
        layeredPane.add(arena, new Integer(1));
        arena.setBounds(0, 0, 700, 500);

        //pannello personaggi
        land = new Land(this);
        graphicLand = land.getGraphicLand();
        layeredPane.add(graphicLand, new Integer(10));
        graphicLand.setBounds(0, 0, 700, 500);

        //schermata di benvenuto
        welcome = new Welcome();
        layeredPane.add(welcome, new Integer(20));
        welcome.setBounds(0, 0, 700, 500);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (started) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_DOWN:
                    land.getHero().move(DOWN);
                    break;
                case KeyEvent.VK_UP:
                    land.getHero().move(UP);
                    break;
                case KeyEvent.VK_LEFT:
                    land.getHero().move(LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    land.getHero().move(RIGHT);
                    break;
                case KeyEvent.VK_SPACE:
                    land.getHero().dropBomb();
                    break;
            }
        } else {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                started = true;
                layeredPane.remove(welcome);
                land.startGame();
                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public void restart() {
        layeredPane.remove(graphicLand);
        land = new Land(this);
        graphicLand = land.getGraphicLand();
        layeredPane.add(graphicLand, new Integer(10));
        graphicLand.setBounds(0, 0, 700, 500);
        repaint();
        land.startGame();
    }
}
