public class Fire extends Spell{
    public Fire(String name, int damage, int manaCost){
        super(name, damage, manaCost);
    }

    @Override
    public void visit(Entity e) {
        if (!e.isImmune("Fire")) {
            int damage = getDamage();
            if (e instanceof Enemy)
                System.out.println("The character used " + "Fire" + " and dealt " + damage + " damage!");
            else
                System.out.println("The enemy used " + "Fire" + " and dealt " + damage + " damage!");
        } else {
            if (e instanceof Enemy)
                System.out.println("The enemy is immune to " + "Fire" + "!");
            else
                System.out.println("The character is immune to " + "Fire" + "!");
        }
    }
}

class Ice extends Spell{
    public Ice(String name, int damage, int manaCost){
        super(name, damage, manaCost);
    }

    @Override
    public void visit(Entity e) {
        if (!e.isImmune("Ice")) {
            int damage = getDamage();
            if (e instanceof Enemy)
                System.out.println("The character used " + "Ice" + " and dealt " + damage + " damage!");
            else
                System.out.println("The enemy used " + "Ice" + " and dealt " + damage + " damage!");
        } else {
            if (e instanceof Enemy)
                System.out.println("The enemy is immune to " + "Ice" + "!");
            else
                System.out.println("The character is immune to " + "Ice" + "!");
        }
    }
}

class Earth extends Spell{
    public Earth(String name, int damage, int manaCost){
        super(name, damage, manaCost);
    }

    @Override
    public void visit(Entity e) {
        if (!e.isImmune("Earth")) {
            int damage = getDamage();
            if (e instanceof Enemy)
                System.out.println("The character used " + "Earth" + " and dealt " + damage + " damage!");
            else
                System.out.println("The enemy used " + "Earth" + " and dealt " + damage + " damage!");
        } else {
            if (e instanceof Enemy)
                System.out.println("The enemy is immune to " + "Earth" + "!");
            else
                System.out.println("The character is immune to " + "Earth" + "!");
        }
    }
}
