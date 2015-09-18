package za.ttd.characters;

import za.ttd.characters.objects.Position;

public abstract class Enemy extends Actor {
    protected float defaultSpeed;
    protected boolean vulnerable, kill;

    public Enemy(Position position, float speed, String actorName) {
        super(position, speed, actorName);
        this.defaultSpeed = speed;
        vulnerable = false;
        kill = false;
    }

    /*
    * Make object to vulnerable or no longer vulnerable,
    * make it move slower/or reset to original speed,
    * being allows tomas to kill it*/
    public void setVulnerable(boolean vulnerable) {
        this.vulnerable = vulnerable;

        if (vulnerable) {
            slow();
            //Play different animations
        }
        else
            super.movementSpeed = defaultSpeed;
    }

    /*
    * @return the current state of enemies vulnerability*/
    public boolean getVulnerability() {
        return vulnerable;
    }

    public boolean getKill() {
        return kill;
    }

    public void setKill(boolean kill) {
        this.kill = kill;
    }

    public void slow() {
        super.movementSpeed = defaultSpeed*.8f;
    }

    public void normalSpeed() {

        if (vulnerable)
            slow();
        else
            super.movementSpeed = defaultSpeed;
    }

    public void speedUp() {
        if (vulnerable)
            super.movementSpeed = defaultSpeed;
        else
            super.movementSpeed = defaultSpeed*1.2f;
    }

    public boolean collided(Position checkPos) {
        return checkPos.getIntX() == position.getIntX() && checkPos.getIntY() == position.getIntY();
    }
}
