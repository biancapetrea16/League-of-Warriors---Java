import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Game {
    // private instance for singleton
    private static Game instance;
    private ArrayList<Account> accounts;
    private Grid grid;
    private Character currentCharacter;
    private Account currentAccount;
    private LoginPanel loginPanel;
    private CharacterPanel characterPanel;
    private GameBoard gameBoard;

    private Game() {
        // load accounts from JSON input file
        this.accounts = JsonInput.deserializeAccounts();
        // initialize login panel and character panel
        loginPanel = new LoginPanel(this);
        characterPanel = new CharacterPanel(this);
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public Character getCurrentCharacter() {
        return currentCharacter;
    }

    public CharacterPanel getCharacterPanel() {
        return characterPanel;
    }

    public Grid getGrid() {
        return grid;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public void setCharacterPanel(CharacterPanel characterPanel) {
        this.characterPanel = characterPanel;
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public void setLoginPanel(LoginPanel loginPanel) {
        this.loginPanel = loginPanel;
    }

    public void setCurrentCharacter(Character currentCharacter) {
        this.currentCharacter = currentCharacter;
    }

    public Account getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(Account currentAccount) {
        this.currentAccount = currentAccount;
    }

    // method that authenticates a user based on email and password
    public Account authenticateUserGUI(String email, String password) {
        for (Account account : accounts) {
            if (account.getInformation().getCredentials().getEmail().equals(email) &&
                    account.getInformation().getCredentials().getPassword().equals(password)) {
                // set the authenticated account as the current account
                currentAccount = account;
                return account;
            }
        }
        // authentication failed
        return null;
    }

    // method that checks if the current account has any characters with life remaining
    public boolean hasAliveCharacters() {
        if (currentAccount != null) {
            for (Character character : currentAccount.getCharacters()) {
                if (character.getCurrentLife() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    // method that returns a list of alive characters for the current account
    public ArrayList<String> getAliveCharacters() {
        ArrayList<String> aliveCharacters = new ArrayList<>();
        if (currentAccount != null) {
            for (Character character : currentAccount.getCharacters()) {
                if (character.getCurrentLife() > 0) {
                    aliveCharacters.add(character.getName());
                }
            }
        }
        return aliveCharacters;
    }

    // starts the game loop by initializing the game board
    public void startGameLoop() {
        gameBoard = new GameBoard(this);
    }

    // authenticates the user
    public void runRandom() {
        if (gameBoard != null) {
            gameBoard.getFrame().dispose();
        }

        // create login panel
        Account account = loginPanel.createLoginPanel();

        if (account == null) {
            JOptionPane.showMessageDialog(
                    null,
                    "Authentication failed. Exiting game.",
                    "Exit Game",
                    JOptionPane.ERROR_MESSAGE
            );
            System.exit(0);
        }
        // set current account
        currentAccount = account;

        // create and display character selection panel
        Character selectedCharacter = characterPanel.showCharacterSelection(account);

        if (selectedCharacter != null) {
            // set current character
            currentCharacter = selectedCharacter;
            startGameLoop();
        } else {
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

class Test {
    // main method to run the game
    public static void main(String[] args) {
        // retrieve the singleton instance
        Game game = Game.getInstance();
        // start the game
        game.runRandom();
    }
}