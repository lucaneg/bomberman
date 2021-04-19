package bomberman;

import java.util.ArrayList;

public class BombHolder {

    private final ArrayList<int[]> fireLocation;
    private final ArrayList<int[]> explosionLocation;
    private static BombHolder holder;

    private BombHolder() {
        this.fireLocation = new ArrayList<>();
        this.explosionLocation = new ArrayList<>();
    }

    public static BombHolder getBombHolder() {
        if (holder == null) {
            holder = new BombHolder();
        }
        return holder;
    }

    public void addBombLocation(int[] fireLocation) {
        this.fireLocation.add(fireLocation);
    }

    public int[] removeBombLocation() {
        int[] a = fireLocation.remove(0);
        explosionLocation.add(a);
        return a;
    }

    public int[] getExplosionLocation() {
        return explosionLocation.remove(0);
    }
}
