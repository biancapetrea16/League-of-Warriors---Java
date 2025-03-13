import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CharacterPanel {
    private JDialog dialog;
    private Game game;
    // the character selected by the user
    private Character selectedCharacter;

    public CharacterPanel(Game game) {
        this.game = game;
        // initialize the dialog
        this.dialog = new JDialog();
        // set dialog title
        this.dialog.setTitle("Select a Character");
        // initially, no character is selected
        this.selectedCharacter = null;
    }

    // method that displays a character selection dialog and allows the user to select a character
    public Character showCharacterSelection(Account account) {
        // clear previous components from the dialog
        dialog.getContentPane().removeAll();
        selectedCharacter = null;
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setSize(450, 350);
        dialog.setLayout(new BorderLayout());
        // make the dialog modal to block other interactions
        dialog.setModal(true);

        // panel for character selection
        JPanel characterSelectionPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        characterSelectionPanel.setBackground(new Color(199, 190, 237, 255));

        // instruction label
        JLabel characterLabel = new JLabel("Select a Character:");
        characterLabel.setFont(new Font("Arial", Font.BOLD, 16));
        characterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        characterSelectionPanel.add(characterLabel);

        // get characters from the account
        ArrayList<Character> characters = account.getCharacters();
        ButtonGroup characterButtons = new ButtonGroup();
        // check is there are any alive characters
        boolean hasAliveCharacters = false;

        // loop through all the characters in the account
        for (Character character : characters) {
            if (character.getCurrentLife() > 0) {
                // at least one alive character exists
                hasAliveCharacters = true;

                // create a radio button for character
                JRadioButton characterButton = new JRadioButton(character.getName());
                characterButton.setFont(new Font("Arial", Font.PLAIN, 14));
                characterButton.setBackground(new Color(199, 190, 237, 255));
                // add radio button to the group of buttons
                characterButtons.add(characterButton);
                // add button to the panel
                characterSelectionPanel.add(characterButton);

                // add actionListener for selecting the character
                characterButton.addActionListener(e -> {
                    selectedCharacter = character;
                    JOptionPane.showMessageDialog(dialog, "Character selected: " + character.getName(), "Success", JOptionPane.INFORMATION_MESSAGE);
                    // close the dialog
                    dialog.dispose();
                });
            }
        }

        // no alive characters
        if (!hasAliveCharacters) {
            JOptionPane.showMessageDialog(dialog, "No alive characters available. Returning to login.", "No Characters", JOptionPane.WARNING_MESSAGE);
            dialog.dispose();
            return null;
        }

        // add the panel to the dialog
        dialog.add(characterSelectionPanel, BorderLayout.CENTER);
        // center the dialog on the screen
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        return selectedCharacter;
    }
}
