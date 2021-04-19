package bomberman.enums;

public enum Direction {
    NONE(0),
    UP(1),
    DOWN(2),
    LEFT(3),
    RIGHT(4);

    private final int value;

    private Direction(int value) {
        this.value = value;
    }

    public static Direction fromInt(int n) {
        for (Direction d : Direction.values()) {
            if (d.value == n) {
                return d;
            }
        }
        return NONE;
    }
}
