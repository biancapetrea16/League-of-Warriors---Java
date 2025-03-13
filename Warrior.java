import java.util.Random;

public class Warrior extends Character {
    public Warrior(String name, int experience, int level) {
        super(name, 300, 300, experience, level,
                ((new Random()).nextInt(15, 31)), // random value - strength: between [15, 30]
                (new Random()).nextInt(10, 31),   // charisma: [10, 15]
                (new Random()).nextInt(5, 26));   // dexterity: [5, 25]
        setImmuneFire(true);
    }

    @Override
    public int getDamage(BattlePage battlePage) {
        int damage = getStrength() * 3;
        // 50% chance to deal double damage
        if (Math.random() < 0.5) {
            damage *= 2;
            battlePage.getBattleInfo().append("The character (warrior) deals double damage.\n");
        }
        return damage;
    }

    @Override
    public void receiveDamage(int damage, BattlePage battlePage) {
        // 50% chance to avoid damage
        if (Math.random() < 0.5) {
            battlePage.getBattleInfo().append("\tThe character " + getName() + " avoided the damage.\n");
            setLastDamage(0);
            return;
        }
        damage = damage - (getCharisma() / 3 + getDexterity() / 3);
        if (damage > 0) {
            setLastDamage(damage);
            battlePage.getBattleInfo().append("\tThe character " + getName() + " received " + damage + " damage.\n");
        } else {
            setLastDamage(0);
            battlePage.getBattleInfo().append("\tThe character " + getName() + " avoided the damage.\n");
        }
    }
}

class Mage extends Character {
    public Mage(String name, int experience, int level) {
        super(name, 300, 300, experience, level,
                ((new Random()).nextInt(3, 16)), //strength [3, 15]
                (new Random()).nextInt(20, 31),  //charisma [20, 30]
                (new Random()).nextInt(10, 26)); //dexterity [10, 25]
        setImmuneIce(true);
    }

    @Override
    public int getDamage(BattlePage battlePage) {
        int damage = (int) (getDexterity() * 2.5);
        // 50% chance to deal double damage
        if (Math.random() < 0.5) {
            damage *= 2;
            battlePage.getBattleInfo().append("The character (mage) deals double damage.\n");
        }
        return damage;
    }

    @Override
    public void receiveDamage(int damage, BattlePage battlePage) {
        // 50% chance to avoid damage
        if (Math.random() < 0.5) {
            battlePage.getBattleInfo().append("\tThe character " + getName() + " avoided the damage.\n");
            setLastDamage(0);
            return;
        }
        damage = damage - (getStrength() / 2 + getCharisma() / 3);
        if (damage > 0) {
            setLastDamage(damage);
            battlePage.getBattleInfo().append("\tThe character " + getName() + " received " + damage + " damage.\n");
        } else {
            setLastDamage(0);
            battlePage.getBattleInfo().append("\tThe character " + getName() + " avoided the damage.\n");
        }
    }
}

class Rogue extends Character {
    public Rogue(String name, int experience, int level) {
        super(name, 300, 300, experience, level,
                ((new Random()).nextInt(5, 16)),  //strength [5, 25]
                (new Random()).nextInt(10, 26),   //charisma [10, 25]
                (new Random()).nextInt(15, 31));  //dexterity [30, 50]
        setImmuneEarth(true);
    }

    //@Override
    public int getDamage(BattlePage battlePage) {
        int damage = (int) (getCharisma() * 2.7);
        // 50% chance to deal double damage
        if (Math.random() < 0.5) {
            damage *= 2;
            battlePage.getBattleInfo().append("The character (rogue) deals double damage.\n");
        }
        return damage;
    }

    @Override
    public void receiveDamage(int damage, BattlePage battlePage) {
        // 50% chance to avoid damage
        if (Math.random() < 0.5) {
            battlePage.getBattleInfo().append("\tThe character " + getName() + " avoided the damage.\n");
            setLastDamage(0);
            return;
        }
        damage = damage - (getStrength() / 3 + getDexterity() / 2);
        if (damage > 0) {
            setLastDamage(damage);
            battlePage.getBattleInfo().append("\tThe character " + getName() + " received " + damage + " damage.\n");
        } else {
            setLastDamage(0);
            battlePage.getBattleInfo().append("\tThe character " + getName() + " avoided the damage.\n");
        }
    }
}

