package bomberman;

import java.awt.EventQueue;

public class Bomberman {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new Arena().setVisible(true));
    }
}
