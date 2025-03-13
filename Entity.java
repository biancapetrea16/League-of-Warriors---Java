import java.util.ArrayList;
import java.util.Random;

interface Battle {
    void receiveDamage(int damage, BattlePage battlePage);
    int getDamage(BattlePage battlePage);
}

abstract class Spell implements Visitor<Entity>{
    private String name;
    private int damage;
    private int manaCost;

    public Spell(String name, int damage, int manaCost) {
        this.name = name;
        this.damage = damage;
        this.manaCost = manaCost;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return damage;
    }

    public int getManaCost() {
        return manaCost;
    }

    @Override
    public String toString() {
        return "Spell (abilities): name = " + name + ", damage = " + damage + ", manaCost = " + manaCost;
    }
}

public abstract class Entity implements Battle, Element<Entity>{
    private ArrayList<Spell> abilities;
    private int currentLife;
    private int maxLife;
    private int currentMana;
    private int maxMana;
    private boolean fire;
    private boolean ice;
    private boolean earth;

    // final damage after the receiveDamage method
    private int lastDamage;

    public Entity() {}

    public Entity(int currentLife, int currentMana){
        this.currentLife = currentLife;
        this.maxLife = 300;
        this.currentMana = currentMana;
        this.maxMana = 300;
        this.fire = false;
        this.ice = false;
        this.earth = false;

        // set random abilities set
        setAbilities(randomAbilities());

        this.lastDamage = 0;
    }

    public int getLastDamage() {
        return lastDamage;
    }

    public void setLastDamage(int lastDamage) {
        this.lastDamage = lastDamage;
    }

    public int getCurrentLife() {
        return currentLife;
    }

    public int getMaxLife() {
        return maxLife;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public boolean getFire() {
        return fire;
    }

    public boolean getIce() {
        return ice;
    }

    public boolean getEarth() {
        return earth;
    }

    public ArrayList<Spell> getAbilities() {
        return abilities;
    }

    public void setCurrentLife(int currentLife) {
        this.currentLife = currentLife;
    }

    public void setCurrentMana(int currentMana) {
        this.currentMana = currentMana;
    }

    public void setAbilities(ArrayList<Spell> abilities) {
        this.abilities = abilities;
    }

    public void setImmuneFire(boolean fire) {
        this.fire = fire;
    }

    public void setImmuneIce(boolean ice) {
        this.ice = ice;
    }

    public void setImmuneEarth(boolean earth) {
        this.earth = earth;
    }

    public void regenerateLife(int life) {
        if(getCurrentLife() + life >= maxLife)
            setCurrentLife(maxLife);
        else
            setCurrentLife(getCurrentLife() + life);
    }

    public void regenerateMana(int mana) {
        if((getCurrentMana() + mana) >= maxMana)
            setCurrentMana(maxMana);
        else
            setCurrentMana(currentMana + mana);
    }

    // method used to check if the entity is immune to ability
    public boolean isImmune(String ability) {
        // check is he's immune to fire
        if (ability.equals("Fire")) {
            return getFire();
        }

        // check is he's immune to ice
        if (ability.equals("Ice")) {
            return getIce();
        }

        // check is he's immune to earth
        if (ability.equals("Earth")) {
            return getEarth();
        }

        return false;
    }

    // method to generates a random list of abilities
    public ArrayList<Spell> randomAbilities(){
        Random random = new Random();

        // list of abilities
        ArrayList<Spell> abilities = new ArrayList<>();
        abilities.add(new Fire("Fire", random.nextInt(20, 41), random.nextInt(35, 51)));
        abilities.add(new Ice("Ice", random.nextInt(15, 31), random.nextInt(35, 46)));
        abilities.add(new Earth("Earth", random.nextInt(25, 46), random.nextInt(40, 56)));

        // add maximum 3 more abilities
        int nrAbilities = random.nextInt(1, 4);
        for(int i = 0; i < nrAbilities; i++){
            // 0 = Fire, 1 = Ice, 2 = Earth
            int type = random.nextInt(3);
            switch (type) {
                case 0:
                    abilities.add(new Fire("Fire", random.nextInt(30, 51), random.nextInt(35, 51)));
                    break;
                case 1:
                    abilities.add(new Ice("Ice", random.nextInt(20, 41), random.nextInt(35, 46)));
                    break;
                case 2:
                    abilities.add(new Earth("Earth", random.nextInt(35, 56), random.nextInt(40, 56)));
                    break;
            }
        }
        return abilities;
    }

    // method for the usage of an ability
    public int useAbility(Spell spell, Entity e) {
        if (this.getCurrentMana() >= spell.getManaCost()) {
            this.setCurrentMana(this.getCurrentMana() - spell.getManaCost());
            // if the entity is not immune to the ability
            e.accept(spell);
            if (!e.isImmune(spell.getName())) {
                return spell.getDamage();
            } else {
                // is immune
                return 0;
            }
        } else {
            if(e instanceof Enemy)
                System.out.println("The character doesn't have enough mana to use " + spell.getName() + "!");
            else
                System.out.println("The enemy doesn't have enough mana to use " + spell.getName() + "!");
            // return -1 if the entity doesn't have enough mana
            return -1;
        }
    }

    // enemy gets a random ability from the list
    public Spell chooseAbility(){
        if(this.abilities == null || this.abilities.isEmpty()) {
            System.out.println("The enemy doesn't have abilities any more.");
            return null;
        }
        Spell chosen = this.abilities.get(new Random().nextInt(this.abilities.size()));
        return chosen;
    }

    // remove used abilities from entity's list
    public void removeAbility(Spell spell) {
        if (this.abilities != null && this.abilities.contains(spell)) {
            this.abilities.remove(spell);
            System.out.print(spell.getName() + " ability was deleted from ");
        }
    }

    @Override
    public int getDamage(BattlePage battlePage) {
        return 0;
    }

    @Override
    public void receiveDamage(int damage, BattlePage battlePage) {
        return;
    }

    @Override
    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "Entity: health = " + currentLife + "/" + maxLife+ ", mana = " + currentMana + "/" + maxMana;
    }
}
