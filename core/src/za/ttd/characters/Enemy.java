package za.ttd.characters;

import za.ttd.characters.objects.Position;

public abstract class Enemy extends Actor {
    //"Ghost" states- fairly self-explanatory
    static final int AGGRESSIVE = 2;
    static final int PASSIVE = 3;
    static final int RETREAT = 4;
    static final int VULNERABLE = 5;

    protected float defaultSpeed;
    protected boolean vulnerable;

    public Enemy(Position position, float speed, String actorName) {
        super(position, speed, actorName);
        this.defaultSpeed = speed;
        vulnerable = false;
    }

    /*
    * Make object to vulnerable or no longer vulnerable,
    * make it move slower/or reset to original speed,
    * being allows tomas to kill it*/
    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;

        if (vulnerable)
            super.movementSpeed = defaultSpeed*.9f;
        else
            super.movementSpeed = defaultSpeed;
    }

    /*
    * @return the current state of enemies vulnerability*/
    public boolean getVulnerability() {
        return vulnerable;
    }

    public void slow() {
        super.movementSpeed = defaultSpeed*.9f;
    }

    public void normalSpeed() {
        super.movementSpeed = defaultSpeed;
    }
}
