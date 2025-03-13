import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameBoard extends JPanel {
    private Game game;
    private Grid grid;
    private JFrame frame;
    // panel for the grid layout
    private JPanel gridPanel;
    // panel for control buttons (north, south, east, west, exit)
    private JPanel controlPanel;
    // panel to display character and account information
    private JPanel infoPanel;
    private JLabel infoLabel;
    // player's icon that is displayed on the grid
    private ImageIcon playerIcon;

    public GameBoard(Game game) {
        frame = new JFrame("Game Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        // center the window on the screen
        frame.setLocationRelativeTo(null);

        this.game = game;

        // initialize player's icon
        ImageIcon originalIcon = new ImageIcon("src/pictures/player.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        playerIcon = new ImageIcon(scaledImage);

        // generate hardcoded grid for the game
        this.grid = Grid.generateGridHardcodat();

        setLayout(new BorderLayout());

        // create grid panel
        gridPanel = new JPanel();
        gridPanel.setLayout(null);

        // color the grid
        gridColoredHardcodat();

        // create control buttons panel
        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.setBackground(new Color(178, 230, 244));
        updateControlPanel();

        // create info panel
        infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBackground(new Color(178, 230, 244));
        // label to display character info
        infoLabel = new JLabel();
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        // update character's info
        updateCharacterInfo();
        infoPanel.add(infoLabel);

        // add all panels to the main game board
        add(gridPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.EAST);

        // add this game board to the window frame
        frame.add(this);
        // make the frame visible
        frame.setVisible(true);
    }

    // method that updates the interface for the grid panel based on current grid
    // state (each cell type has a different color)
    public void updateGridColored() {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(grid.getLength(), grid.getWidth(), 2, 2));

        ArrayList<ArrayList<Cell>> cells = grid.getCells();
        for (int i = 0; i < grid.getLength(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                JButton cellButton = new JButton();
                Cell cell = cells.get(i).get(j);

                // set a random color based on the cell type
                if (cell.getType() == CellEntityType.PLAYER) {
                    cellButton.setBackground(new Color(36, 64, 126));
                } else {
                    switch (cell.getType()) {
                        case SANCTUARY:
                            cellButton.setBackground(new Color(70, 161, 61));
                            break;
                        case ENEMY:
                            cellButton.setBackground(new Color(161, 61, 64));
                            break;
                        case PORTAL:
                            cellButton.setBackground(new Color(139, 61, 161));
                            break;
                        default:
                            cellButton.setBackground(Color.WHITE);
                            break;
                    }
                }
                cellButton.setEnabled(false);
                gridPanel.add(cellButton);
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    // method to update the grid and color the cells
    // gray - visited cell; blue - player
    public void gridColoredHardcodat() {
        // clear existing components
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(grid.getLength(), grid.getWidth(), 2, 2));

        ArrayList<ArrayList<Cell>> cells = grid.getCells();
        for (int i = 0; i < grid.getLength(); i++) {
            for (int j = 0; j < grid.getWidth(); j++) {
                // create a button
                JButton cellButton = new JButton();
                // get cell data
                Cell cell = cells.get(i).get(j);

                // set color based on cell type and visited status
                if (cell.getType() == CellEntityType.PLAYER) {
                    // set an icon for the player
                    cellButton.setIcon(playerIcon);
                    cellButton.setDisabledIcon(playerIcon);
                    cellButton.setEnabled(false);
                } else if (cell.getVisited()) {
                    cellButton.setBackground(new Color(168, 168, 168, 247));
                } else {
                    // white - unvisited cell
                    cellButton.setBackground(Color.WHITE);
                }
                // disable button
                cellButton.setEnabled(false);
                // add button to the panel
                gridPanel.add(cellButton);
            }
        }
        // refresh layout and panel
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    // update control panel buttons based on available directions
    public void updateControlPanel() {
        // clear existing buttons
        controlPanel.removeAll();

        // add available buttons
        if (grid.canGoNorth())
            addDirectionButton("NORTH");
        if (grid.canGoSouth())
            addDirectionButton("SOUTH");
        if (grid.canGoEast())
            addDirectionButton("EAST");
        if (grid.canGoWest())
            addDirectionButton("WEST");

        // add exit button
        JButton exitButton = new JButton("EXIT");
        exitButton.setBackground(new Color(110, 0, 15));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFocusPainted(false);
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        // add actionListener to handle EXIT button
        exitButton.addActionListener(e -> handleExit());
        controlPanel.add(exitButton);

        // refresh layout and panel
        controlPanel.revalidate();
        controlPanel.repaint();
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    // update character's information in the info panel
    public void updateCharacterInfo() {
        // get current character and account
        Character currentCharacter = game.getCurrentCharacter();
        Account currentAccount = game.getCurrentAccount();

        StringBuilder info = new StringBuilder();
        info.append("<html>");
        info.append("<b>Account Info:</b><br>");
        info.append("Account: ").append(currentAccount.getInformation().getName()).append("<br><br>");
        info.append("<b>Character Info:</b><br>");
        info.append("Name: ").append(currentCharacter.getName()).append("<br>");
        info.append("Experience: ").append(currentCharacter.getExperience()).append("<br>");
        info.append("Level: ").append(currentCharacter.getLevel()).append("<br>");
        info.append("Games Played: ").append(currentAccount.getGamesPlayed()).append("<br>");
        info.append("Killed enemies: ").append(currentCharacter.getKilledEnemies()).append("<br>");
        info.append("Life: ").append(currentCharacter.getCurrentLife()).append("<br>");
        info.append("Mana: ").append(currentCharacter.getCurrentMana()).append("<br>");
        info.append("</html>");

        // set label text
        infoLabel.setText(info.toString());
    }

    // method that adds direction button
    public void addDirectionButton(String direction) {
        JButton button = new JButton(direction);
        button.setBackground(new Color(156, 208, 241)); // Bleu
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.addActionListener((ActionEvent e) -> handleMove(direction));
        controlPanel.add(button);
    }

    //method that handle movement in a specified direction
    public void handleMove(String direction) {
        try {
            // save the previous cell
            Cell previousCell = grid.getCurrentCell();
            switch (direction.toUpperCase()) {
                case "NORTH":
                    // access cell to check if the character is going to meet an enemy,
                    // a sanctuary or a portal
                    Cell north = grid.accessNorthCell();
                    if(north.checkEnemy()) {
                        System.out.println("\nYOU'VE MET AN ENEMY. Current info: ");
                        handleBattle();
                        // move the player
                        Cell playerNorth = grid.goNorth();
                        playerNorth.setType(CellEntityType.PLAYER);
                    }
                    if(north.checkPortal()) {
                        handlePortal();
                    }
                    if(north.checkSanctuary()) {
                        handleSanctuary();
                        Cell playerNorth = grid.goNorth();
                        playerNorth.setType(CellEntityType.PLAYER);
                    }
                    // if the cell is void, just move the player
                    if(north.checkVoid()) {
                        Cell playerNorth = grid.goNorth();
                        playerNorth.setType(CellEntityType.PLAYER);
                    }
                    break;
                case "SOUTH":
                    // access cell to check if the character is going to meet an enemy,
                    // a sanctuary or a portal
                    Cell south = grid.accessSouthCell();
                    if(south.checkEnemy()) {
                        System.out.println("\n\tYOU'VE MET AN ENEMY. Current info: ");
                        handleBattle();
                        Cell playerSouth = grid.goSouth();
                        playerSouth.setType(CellEntityType.PLAYER);
                    }
                    if(south.checkPortal()){
                        handlePortal();
                    }
                    if(south.checkSanctuary()) {
                        handleSanctuary();
                        Cell playerSouth = grid.goSouth();
                        playerSouth.setType(CellEntityType.PLAYER);
                    }
                    // if the cell is void, just move the player
                    if(south.checkVoid()) {
                        Cell playerSouth = grid.goSouth();
                        playerSouth.setType(CellEntityType.PLAYER);
                    }
                    break;
                case "EAST":
                    // access cell to check if the character is going to meet an enemy,
                    // a sanctuary or a portal
                    Cell east = grid.accessEastCell();
                    if(east.checkEnemy()) {
                        System.out.println("\n\tYOU'VE MET AN ENEMY. Current info: ");
                        handleBattle();
                        Cell playerEast = grid.goEast();
                        playerEast.setType(CellEntityType.PLAYER);
                    }
                    if(east.checkPortal()) {
                        handlePortal();
                    }
                    if(east.checkSanctuary()) {
                        handleSanctuary();
                        Cell playerEast = grid.goEast();
                        playerEast.setType(CellEntityType.PLAYER);
                    }
                    // if the cell is void, just move the player
                    if(east.checkVoid()) {
                        Cell playerEast = grid.goEast();
                        playerEast.setType(CellEntityType.PLAYER);
                    }
                    break;
                case "WEST":
                    // access cell to check if the character is going to meet an enemy,
                    // a sanctuary or a portal
                    Cell west = grid.accessWestCell();
                    if(west.checkEnemy()) {
                        System.out.println("\n\tYOU'VE MET AN ENEMY. Current info: ");
                        handleBattle();
                        Cell playerWest = grid.goWest();
                        playerWest.setType(CellEntityType.PLAYER);
                        break;
                    }
                    if(west.checkPortal()) {
                        handlePortal();
                    }
                    if(west.checkSanctuary()){
                        handleSanctuary();
                        Cell playerWest = grid.goWest();
                        playerWest.setType(CellEntityType.PLAYER);
                        break;
                    }
                    // if the cell is void, just move the player
                    if(west.checkVoid()) {
                        Cell playerWest = grid.goWest();
                        playerWest.setType(CellEntityType.PLAYER);
                        break;
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid direction: " + direction);
            }
            // mark previous cell as void
            previousCell.setType(CellEntityType.VOID);

            // update grid interface and refresh layout and panel
            gridColoredHardcodat();
            updateControlPanel();
            updateCharacterInfo();
        } catch (ImpossibleMoveException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Impossible move", JOptionPane.WARNING_MESSAGE);
        }
    }

    // method that handles sanctuary encounter
    public void handleSanctuary() {
        Character currentCharacter = game.getCurrentCharacter();

        int lifeBefore = currentCharacter.getCurrentLife();
        int manaBefore = currentCharacter.getCurrentMana();

        // add random values to character's life and mana
        currentCharacter.addValuesSanctuar();

        int lifeAfter = currentCharacter.getCurrentLife();
        int manaAfter = currentCharacter.getCurrentMana();

        String message = String.format(
                "<html><b>Sanctuary Encountered!</b><br><br>" +
                        "Life and mana before sanctuary:<br>Life: %d, Mana: %d<br><br>" +
                        "Life and mana after sanctuary:<br>Life: %d, Mana: %d</html>",
                lifeBefore, manaBefore, lifeAfter, manaAfter
        );

        JOptionPane.showMessageDialog(
                this,
                message,
                "Sanctuary",
                JOptionPane.INFORMATION_MESSAGE
        );

        updateCharacterInfo();
    }

    // method that handles portal encounter
    public void handlePortal() {
        // increase character's level and number of games played
        Character currentCharacter = game.getCurrentCharacter();
        Account currentAccount = game.getCurrentAccount();

        currentCharacter.levelUpPortal(currentAccount.getGamesPlayed());
        currentAccount.setGamesPlayed(currentAccount.getGamesPlayed() + 1);

        JOptionPane.showMessageDialog(
                this,
                "<html><b>Portal Encountered!</b><br><br>" +
                        "Congratulations! You have leveled up to the next level.<br>" +
                        "Choose a character to continue.</html>",
                "Level Up!",
                JOptionPane.INFORMATION_MESSAGE
        );

        // choose a character to continue the game
        Character selectedCharacter = game.getCharacterPanel().showCharacterSelection(currentAccount);
        if (selectedCharacter != null) {
            game.setCurrentCharacter(selectedCharacter);
            JOptionPane.showMessageDialog(
                    this,
                    "Character selected: " + selectedCharacter.getName(),
                    "Character Selected",
                    JOptionPane.INFORMATION_MESSAGE
            );
            grid = Grid.generategrid();
            gridColoredHardcodat();
        }
    }

    // method that handles enemy encounter
    public void handleBattle() {
        Enemy enemy = new Enemy();
        Character currentCharacter = game.getCurrentCharacter();
        BattlePage battlePage = new BattlePage(
                currentCharacter, enemy, game.getGameBoard()
        );
        battlePage.setVisible(true);
    }

    // method that handles exit button click
    public void handleExit() {
        int confirm = JOptionPane.showConfirmDialog(
                frame,
                "Are you sure you want to exit the game?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            // choose a character to continue the game
            Character selectedCharacter = game.getCharacterPanel().showCharacterSelection(game.getCurrentAccount());
            if (selectedCharacter != null) {
                game.setCurrentCharacter(selectedCharacter);
                GameBoard currentGameBoard = game.getGameBoard();

                if (currentGameBoard != null) {
                    currentGameBoard.updateCharacterInfo();
                    currentGameBoard.gridColoredHardcodat();
                    currentGameBoard.getFrame().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "No active game found. Exiting.", "Error", JOptionPane.ERROR_MESSAGE);
                    game.startGameLoop();
                }
            } else {
                JOptionPane.showMessageDialog(null, "No characters selected. Exiting game.", "Exit Game", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }
}
