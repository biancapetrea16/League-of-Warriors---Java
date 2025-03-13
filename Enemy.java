import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity {
    // normal attack damage
    private int normalDamage;

    public Enemy() {
        super(getRandomValue(120, 301), getRandomValue(160, 301));
        this.normalDamage = getRandomValue(50, 101);

        // random abilities
        // enemy gets random immunity to abilities
        setImmuneFire(new Random().nextBoolean());
        setImmuneIce(new Random().nextBoolean());
        setImmuneEarth(new Random().nextBoolean());
    }

    // generate random number between min and max
    private static int getRandomValue(int min, int max) {
        return new Random().nextInt(max - min + 1) + min;
    }

    @Override
    public int getDamage(BattlePage battlePage) {
        // normal attack damage
        int damage = this.normalDamage;
        // 50% chance to deal double damage
        if (Math.random() < 0.5) {
            damage *= 2;
            battlePage.getBattleInfo().append("The enemy deals double damage.\n");
        }
        return damage;
    }

    @Override
    public void receiveDamage(int damage, BattlePage battlePage) {
        // 50% chance to avoid damage
        if (Math.random() < 0.5) {
            battlePage.getBattleInfo().append("\tThe enemy avoided the damage.\n");
            setLastDamage(0);
            return;
        }
        battlePage.getBattleInfo().append("\tThe enemy received " + damage + " damage!\n");
        // save the value of the damage received so that i can use it in battle method
        setLastDamage(damage);
    }

    @Override
    public String toString() {
        return "Enemy: life = " + getCurrentLife() + "/" + getMaxLife() +
                ", mana = " + getCurrentMana() + "/" + getMaxMana() +
                ", normalAttackDamage = " + normalDamage +
                ", immuneToFire = " + getFire() +
                ", immuneToIce = " + getIce() +
                ", immuneToEarth = " + getEarth() +
                "\n       Enemy's abilities = " + getAbilities();
    }
}
