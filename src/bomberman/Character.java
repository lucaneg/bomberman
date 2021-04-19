package bomberman;

import bomberman.enums.Direction;

public abstract class Character {
    
    public abstract void move(Direction direction);
    
    public abstract void die();
    
}
