import javax.swing.*;
import java.util.Random;

class CharacterFactory {
    public static Character createCharacter(String type, String name, int experience, int level) {
        switch (type.toLowerCase()) {
            case "warrior":
                return new Warrior(name, experience, level);
            case "mage":
                return new Mage(name, experience, level);
            case "rogue":
                return new Rogue(name, experience, level);
            default:
                throw new IllegalArgumentException("Invalid character type: " + type);
        }
    }
}

public abstract class Character extends Entity {
    protected String name;
    protected int experience;
    protected int level;
    protected int strength;
    protected int charisma;
    protected int dexterity;
    // number of killed enemies
    protected int killedEnemies;

    public Character(String name, int maxLife, int maxMana, int experience, int level, int strength, int charisma,
                     int dexterity) {
        // initial, the character has maximum life and mana
        super(maxLife, maxMana);
        this.name = name;
        this.experience = experience;
        this.level = level;
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;
        this.killedEnemies = 0;
    }

    public Character(String name, int experience, int level){
        this.name = name;
        this.experience = experience;
        this.level = level;
        this.killedEnemies = 0;
    }

    public String getName() {
        return name;
    }

    public int getExperience() {
        return experience;
    }

    public int getLevel() {
        return level;
    }

    public int getStrength() {
        return strength;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getKilledEnemies() {
        return killedEnemies;
    }

    public void addKilledEnemy() {
        this.killedEnemies++;
    }

    public void setKilledEnemies(int killedEnemies) {
        this.killedEnemies = killedEnemies;
    }

    // method that regenerates life and mana when entering a sanctuary
    public void addValuesSanctuar() {
        Random random = new Random();
        int addLife = random.nextInt(20,51);
        int addMana = random.nextInt(20, 51);
        this.regenerateLife(addLife);
        this.regenerateMana(addMana);
    }

    // method to handles experience and intern level up when winning
    public void addWin() {
        // random experience points added
        int pointsExperience  = new Random().nextInt(50, 81);
        setExperience(getExperience() + pointsExperience);
        addKilledEnemy();

        // check if the character can go to the next intern level
        int neededExperience = 300; // experience needed to go to the next level
        if (getExperience() >= neededExperience) {
            setLevel(getLevel() + 1);
            // recalculate experience when the character gets to a new intern level
            setExperience(getExperience() - neededExperience);

            // improve attributes when the character gets to a new intern level
            int pointsStrength = new Random().nextInt(15, 31);
            int pointsDexterity = new Random().nextInt(10, 21);
            int pointsCharisma = new Random().nextInt(5, 16);
            setStrength(getStrength() + pointsStrength);
            setDexterity(getDexterity() + pointsDexterity);
            setCharisma(getCharisma() + pointsCharisma);

            // show final window
            new FinalPage(getName(), this.getClass().getName(), getLevel(), getExperience(), getKilledEnemies());

            System.out.println(getName() + " advanced to intern level " + getLevel());
        }
    }

    // method for leveling up when the character enters a portal
    public void levelUpPortal(int levelCompleted){
        int newExperience = experience + (levelCompleted * 5);
        this.experience = newExperience;
        System.out.println(getName() + " entered a portal and experience was adjusted to: " + experience);
        // if he got to neededExperience, he also moves to the next intern level
        int neededExperience = 300;
        if (experience >= neededExperience) {
            setLevel(getLevel() + 1);
            setExperience(getExperience() - neededExperience);

            // show final window
            new FinalPage(getName(), this.getClass().getName(), getLevel(), getExperience(), getKilledEnemies());

            System.out.println(getName() + " advanced to game's level " + getLevel());
        }
    }

    public abstract int getDamage(BattlePage battlePage);

    public abstract void receiveDamage(int damage, BattlePage battlePage);

    @Override
    public String toString() {
        return "Character: name = " + name + ", life  = " + getCurrentLife() + ", mana = "  +
                getCurrentMana() + ", level = " + level + ", experience = " + experience +
                ", strength = " + strength + ", charisma = " + charisma + ", dexterity = " + dexterity +
                "\n           Character's abilities:" + getAbilities();
    }
}
