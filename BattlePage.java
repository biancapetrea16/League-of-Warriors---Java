import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

public class BattlePage extends JFrame {
    private Character currentCharacter;
    private Enemy enemy;
    // labels to display player's and enemy's information
    private JLabel playerInfo, enemyInfo;
    // area to log the battle actions (who attacks who and what damage they receive)
    private JTextArea battleInfo;
    private JButton attackButton, abilityButton;
    int damageToEnemy = 0;
    int damageToPlayer = 0;

    public JTextArea getBattleInfo() {
        return battleInfo;
    }

    public void setBattleInfo(JTextArea battleInfo) {
        this.battleInfo = battleInfo;
    }

    // contructor that initializes the battle screen
    public BattlePage(Character currentCharacter, Enemy enemy, GameBoard gameBoard) {
        this.currentCharacter = currentCharacter;
        this.enemy = enemy;

        setTitle("Battle");
        setSize(800, 700);
        setLayout(new BorderLayout());

        // creates top panel that display player's and enemy's information
        // 2 columns: one for the player, one for the en enemy
        JPanel topPanel = new JPanel(new GridLayout(1, 2));
        topPanel.setBackground(new Color(223, 236, 246));

        // player information
        playerInfo = new JLabel(getCharacterInfo(currentCharacter));
        playerInfo.setHorizontalAlignment(SwingConstants.CENTER);
        playerInfo.setOpaque(true);
        playerInfo.setBackground(new Color(223, 236, 246));
        topPanel.add(playerInfo);

        // enemy information
        enemyInfo = new JLabel(getEnemyInfo(enemy));
        enemyInfo.setHorizontalAlignment(SwingConstants.CENTER);
        enemyInfo.setOpaque(true);
        enemyInfo.setBackground(new Color(223, 236, 246));
        topPanel.add(enemyInfo);

        // add information panel to the north of the window
        add(topPanel, BorderLayout.NORTH);

        // create a middle panel for images and battle information
        JPanel middlePanel = new JPanel(new BorderLayout());

        // panel for images
        JPanel imagesPanel = new JPanel(new GridLayout(1, 2));
        imagesPanel.setBackground(new Color(223, 236, 246));

        // player image
        JLabel playerImageLabel = new JLabel();
        playerImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerImageLabel.setIcon(resizeImageIcon("src/pictures/eu.jpg", 150, 220));
        imagesPanel.add(playerImageLabel);

        // enemy image
        JLabel enemyImageLabel = new JLabel();
        enemyImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        enemyImageLabel.setIcon(resizeImageIcon("src/pictures/enemy.jpg", 150, 220));
        imagesPanel.add(enemyImageLabel);

        // add images to middle panel
        middlePanel.add(imagesPanel, BorderLayout.NORTH);

        // panel for battle information area
        battleInfo = new JTextArea();
        // makes the battle info area not editable
        battleInfo.setEditable(false);
        battleInfo.setBackground(new Color(234, 233, 248));
        // add scroll pane
        JScrollPane logScrollPane = new JScrollPane(battleInfo);
        middlePanel.add(logScrollPane, BorderLayout.CENTER);

        // adds middle panel to the center part of the window
        add(middlePanel, BorderLayout.CENTER);

        // creates the bottom panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(234, 233, 248));
        attackButton = new JButton("Attack normally");
        attackButton.setBackground(new Color(26, 0, 41));
        attackButton.setForeground(new Color(234, 233, 248));
        abilityButton = new JButton("Use Ability");
        abilityButton.setBackground(new Color(26, 0, 41));
        abilityButton.setForeground(new Color(234, 233, 248));
        //add ability and attack button
        buttonPanel.add(attackButton);
        buttonPanel.add(abilityButton);

        // adds bottom panel to the south part of the window
        add(buttonPanel, BorderLayout.SOUTH);

        // add actionListeners to handle button clicks
        attackButton.addActionListener(e -> handleAttack(gameBoard));
        abilityButton.addActionListener(e -> handleAbility(gameBoard));

        // close window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // update information on the window
        updateInfo();
    }

    // update player and enemy info on the screen
    private void updateInfo() {
        playerInfo.setText(getCharacterInfo(currentCharacter));
        enemyInfo.setText(getEnemyInfo(enemy));
    }

    // method that creates a string displaying charcater information
    private String getCharacterInfo(Character character) {
        return "<html>" +
                "Name: " + character.getName() + "<br>" +
                "Life: " + character.getCurrentLife() + "/" + character.getMaxLife() + "<br>" +
                "Mana: " + character.getCurrentMana() + "/" + character.getMaxMana() + "<br>" +
                "Strength: " + character.getStrength() + "<br>" +
                "Dexterity: " + character.getDexterity() + "<br>" +
                "Charisma: " + character.getCharisma() + "<br>" +
                "Abilities: " + character.getAbilities() + "<br>" +
                "</html>";
    }

    // method that creates a string displaying enemy information
    private String getEnemyInfo(Enemy enemy) {
        return "<html>" +
                "Life: " + enemy.getCurrentLife() + "/" + enemy.getMaxLife() + "<br>" +
                "Mana: " + enemy.getCurrentMana() + "/" + enemy.getMaxMana() + "<br>" +
                "Abilities: " + enemy.getAbilities() + "<br>" +
                "Immune to Fire: " + enemy.isImmune("Fire") + "<br>" +
                "Immune to Ice: " + enemy.isImmune("Ice") + "<br>" +
                "Immune to Earth: " + enemy.isImmune("Earth") + "<br>" +
                "</html>";
    }

    // method that resizes an image to help displaying it
    private ImageIcon resizeImageIcon(String imagePath, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    // method that handles player's normal attack
    private void handleAttack(GameBoard gameBoard) {
        // player attacks enemy
        damageToEnemy = currentCharacter.getDamage(this);
        battleInfo.append("\nThe character attacked the enemy NORMALLY and dealt " + damageToEnemy + " damage.\n");
        enemy.receiveDamage(damageToEnemy, this);
        damageToEnemy = (enemy.getLastDamage());
        enemy.setCurrentLife(enemy.getCurrentLife() - damageToEnemy);
        updateInfo();

        // check if enemy is defeated
        if (enemy.getCurrentLife() <= 0) {
            battleInfo.append("CONGRATULATIONS! You defeated an enemy.\n");
            // if the character won, recharge character's life and mana
            currentCharacter.setCurrentLife(Math.min(currentCharacter.getMaxLife(), 2 * currentCharacter.getCurrentLife()));
            currentCharacter.setCurrentMana(currentCharacter.getMaxMana());
            currentCharacter.addWin();
            gameBoard.updateCharacterInfo();
            endBattle(true);
            return;
        }

        // enemy's turn to attack
        enemyTurn();
    }

    // method that handles the use of an ability by the player
    private void handleAbility(GameBoard gameBoard) {
        if (currentCharacter.getAbilities() == null || currentCharacter.getAbilities().isEmpty()) {
            battleInfo.append("\nThe character has no abilities available! Attack normally!\n");
            handleAttack(gameBoard);
            updateInfo();
            return;
        }

        // creates a new panel to show pictures of the available abilities
        JPanel abilitiesPanel = new JPanel();
        // layout with 3 columns
        abilitiesPanel.setLayout(new GridLayout(0,3,10,10));

        // add ability to the panel
        for (Spell ability : currentCharacter.getAbilities()) {
            // create a panel for an ability
            JPanel abilityPanel = new JPanel();
            abilityPanel.setLayout(new BorderLayout());
            // set border around ability panel
            abilityPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // set background color based on ability type
            switch (ability.getName().toLowerCase()) {
                case "fire":
                    abilityPanel.setBackground(new Color(255, 222, 209));
                    break;
                case "ice":
                    abilityPanel.setBackground(new Color(212, 231, 248));
                    break;
                case "earth":
                    abilityPanel.setBackground(new Color(232, 251, 220));
                    break;
                default:
                    // default background
                    abilityPanel.setBackground(Color.WHITE);
            }

            // set image icon for the ability
            String imagePath = getAbilityImagePath(ability);
            ImageIcon icon = resizeImageIcon(imagePath, 140, 200);
            JLabel abilityImage = new JLabel(icon);
            abilityImage.setHorizontalAlignment(SwingConstants.CENTER);
            abilityPanel.add(abilityImage, BorderLayout.NORTH);

            // show information about the ability
            String details = "<html><center>" + ability.getName() + "<br>" +
                    "Damage: " + ability.getDamage() + "<br>" +
                    "Mana Cost: " + ability.getManaCost() + "</center></html>";
            JLabel abilityInfo = new JLabel(details);
            abilityInfo.setHorizontalAlignment(SwingConstants.CENTER);
            abilityPanel.add(abilityInfo, BorderLayout.CENTER);

            // button to select the ability you want to use
            JButton abilityButton = new JButton("USE");
            abilityButton.setBackground(new Color(26, 0, 41));
            abilityButton.setForeground(new Color(234, 233, 248));

            // add button's functionality
            abilityButton.addActionListener(e -> {
                int d = currentCharacter.useAbility(ability, enemy);
                if (d != -1) {
                    damageToEnemy = d;
                    battleInfo.append("\nPlayer used ability " + ability.getName() + " and dealt " + damageToEnemy + " damage.\n");
                    enemy.receiveDamage(damageToEnemy, this);
                    damageToEnemy = (enemy.getLastDamage());
                    enemy.setCurrentLife(enemy.getCurrentLife() - damageToEnemy);
                    updateInfo();
                    currentCharacter.removeAbility(ability);
                } else {
                    // if there isn't enough mana, attack normally
                    battleInfo.append("\nNot enough mana! Attack NORMALLY instead! \n");
                    handleAttack(gameBoard);
                    updateInfo();
                }

                // close the window containing the abilities panel after selection
                SwingUtilities.getWindowAncestor(abilitiesPanel).dispose();

                // check if enemy was defeated
                if (enemy.getCurrentLife() <= 0) {
                    battleInfo.append("CONGRATULATIONS! You defeated an enemy.\n");
                    // if the character won, recharge character's life and mana
                    currentCharacter.setCurrentLife(Math.min(currentCharacter.getMaxLife(), 2 * currentCharacter.getCurrentLife()));
                    currentCharacter.setCurrentMana(currentCharacter.getMaxMana());
                    // increase win count
                    currentCharacter.addWin();
                    gameBoard.updateCharacterInfo();
                    endBattle(true);
                } else {
                    // if the enemy is not defeated, it's its turn
                    enemyTurn();
                }
            });
            // add "USE" button to the south of each of the abilities panel
            abilityPanel.add(abilityButton, BorderLayout.SOUTH);
            // add each panel to the main one with all the abilities
            abilitiesPanel.add(abilityPanel);
        }

        // create scroll pane in case there are many abilities
        JScrollPane scrollPane = new JScrollPane(abilitiesPanel);
        scrollPane.setPreferredSize(new Dimension(700, 600));

        JOptionPane.showMessageDialog(
                this,
                scrollPane,
                "Choose an Ability",
                JOptionPane.PLAIN_MESSAGE
        );

        // update information after choosing ability
        updateInfo();
    }

    // method that handles enemy's turn to attack
    private void enemyTurn() {
        Game game = Game.getInstance();
        // enemy's turn to attack (chooses attack type randomly)
        // 0. normal attack
        // 1. random ability
        int enemyChoice = new Random().nextInt(2);

        // normal attack
        if (enemyChoice == 0) {
            damageToPlayer = enemy.getDamage(this);
            battleInfo.append("The enemy attacked the player NORMALLY and dealt " + damageToPlayer + " damage.\n");
        } else {
            // enemy uses ability
            // choose ability randomly
            Spell enemyAbility = enemy.chooseAbility();
            if (enemyAbility != null) {
                int d = enemy.useAbility(enemyAbility, currentCharacter);
                if (d != -1) {
                    damageToPlayer = d;
                    battleInfo.append("Enemy used ability " + enemyAbility.getName() + " and dealt " + damageToPlayer + " damage.\n");
                    enemy.removeAbility(enemyAbility);
                } else {
                    // the enemy does not have enough mana, so he attacks normally
                    damageToPlayer = enemy.getDamage(this);
                    battleInfo.append("The enemy attacked the player NORMALLY and dealt " + damageToPlayer + " damage.\n");
                }
            } else {
                // the enemy doesn't have available abilities anymore, so he attacks normally
                damageToPlayer = enemy.getDamage(this);
                battleInfo.append("The enemy attacked the player NORMALLY and dealt " + damageToPlayer + " damage.\n");
            }
        }

        // player receive damage
        currentCharacter.receiveDamage(damageToPlayer, this);
        damageToPlayer = (currentCharacter.getLastDamage());
        // update life and mana scores after the battle
        currentCharacter.setCurrentLife(currentCharacter.getCurrentLife() - damageToPlayer);

        updateInfo();

        // if player is defeated, choose another character to continue the game
        // or login into another account if the current one doesn't have available characters
        if (currentCharacter.getCurrentLife() <= 0) {
            battleInfo.append("!!! GAME OVER !!!\n");

            endBattle(false);

            ArrayList<String> aliveCharacters = game.getAliveCharacters();

            // if there are available characters
            if (!aliveCharacters.isEmpty()) {
                // show characters selection panel
                Character selectedCharacter = game.getCharacterPanel().showCharacterSelection(game.getCurrentAccount());
                if (selectedCharacter != null) {
                    // the current character becomes the one selected
                    currentCharacter = selectedCharacter;
                    game.setCurrentCharacter(selectedCharacter);

                    GameBoard currentGameBoard = game.getGameBoard();
                    if (currentGameBoard != null) {
                        // continue the game on the same game board
                        currentGameBoard.updateCharacterInfo();
                        currentGameBoard.gridColoredHardcodat();
                        currentGameBoard.getFrame().setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "No active game found. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
                        game.startGameLoop();
                    }
                } else {
                    // if the user exited
                    JOptionPane.showMessageDialog(
                            null,
                            "No characters selected. Exiting game.",
                            "Exit Game",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    System.exit(0);
                }
            } else {
                // if there are no available characters, go to login page
                JOptionPane.showMessageDialog(
                        null,
                        "No alive characters available. Returning to login.",
                        "Return to Login",
                        JOptionPane.INFORMATION_MESSAGE
                );

                Account account = game.getLoginPanel().createLoginPanel();

                if (account == null) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Authentication failed. Exiting game.",
                            "Exit Game",
                            JOptionPane.ERROR_MESSAGE
                    );
                    System.exit(0);
                }

                game.setCurrentAccount(account);
                Character newCharacter = game.getCharacterPanel().showCharacterSelection(account);

                if (newCharacter != null) {
                    // if the user selects a new character, continue the game
                    currentCharacter = newCharacter;
                    game.setCurrentCharacter(newCharacter);
                    game.startGameLoop();
                } else {
                    // if the user doesn't select a character, close the game
                    JOptionPane.showMessageDialog(
                            null,
                            "No characters selected. Exiting game.",
                            "Exit Game",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    System.exit(0);
                }
            }
        }

        //update information
        playerInfo.setText(getCharacterInfo(currentCharacter));
        enemyInfo.setText(getEnemyInfo(enemy));
    }

    // end the battle and display a massage
    private void endBattle(boolean playerWon) {
        attackButton.setEnabled(false);
        abilityButton.setEnabled(false);

        if (playerWon) {
            JOptionPane.showMessageDialog(this, "CONGRATULATIONS! You won the battle!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "You lost the battle. GAME OVER!");
            dispose();
        }
    }

    // get the image path for an ability based on its name
    private String getAbilityImagePath(Spell ability) {
        switch (ability.getName().toLowerCase()) {
            case "fire":
                return "src/pictures/fire.jpg";
            case "ice":
                return "src/pictures/ice.jpg";
            case "earth":
                return "src/pictures/earth.jpg";
            default:
                return "C:\\Users\\bianca\\Downloads\\default.jpg";
        }
    }

}
