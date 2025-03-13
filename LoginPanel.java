import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel {
    private JDialog dialog;
    // input fields for email and password
    private JTextField emailField;
    private JPasswordField passwordField;
    private Game game;
    // authenticated account after login
    private Account authenticatedAccount;

    public LoginPanel(Game game) {
        this.game = game;
    }

    // creates and displays the login panel
    public Account createLoginPanel() {
        if (dialog == null) {
            dialog = new JDialog((Frame) null, "League of Warriors - Authentication", true);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setSize(450, 350);
            dialog.setLayout(new BorderLayout());
        }

        // login panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 1, 10, 10));
        loginPanel.setBackground(new Color(237, 190, 228, 255));

        // creates labels for email and password
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));

        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        emailField.setBackground(new Color(246, 221, 241, 255));
        passwordField.setBackground(new Color(246, 221, 241, 255));

        // create a login button
        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(120, 40));
        loginButton.setFont(new Font("Arial", Font.PLAIN, 18));
        loginButton.setBackground(new Color(129, 0, 69));
        loginButton.setForeground(Color.WHITE);
        loginButton.setHorizontalAlignment(SwingConstants.CENTER);

        // add an auxiliary panel for centering the button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(237, 190, 228, 255));
        buttonPanel.add(loginButton);

        // add an action listener to handle login button clicks
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get entered email and password
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                // use the game instance to authenticate the user
                Account account = game.authenticateUserGUI(email, password);
                // if authentication is successful, save the account
                if (account != null) {
                    authenticatedAccount = account;
                    // check if the account has alive characters
                    if (game.hasAliveCharacters()) {
                        JOptionPane.showMessageDialog(dialog, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        // close the login dialog
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "No alive characters in this account.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    // if authentication fails
                    JOptionPane.showMessageDialog(dialog, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // add components to the login panel
        loginPanel.add(emailLabel);
        loginPanel.add(emailField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(loginButton);
        // add the login panel to the dialog
        dialog.add(loginPanel, BorderLayout.CENTER);
        // refresh the dialog to reflect changes
        dialog.revalidate();
        dialog.repaint();
        // center the dialog on the screen
        dialog.setLocationRelativeTo(null);
        // show the dialog
        dialog.setVisible(true);

        // return the authenticated account
        return authenticatedAccount;
    }
}
