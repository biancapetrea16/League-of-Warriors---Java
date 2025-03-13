import javax.swing.*;
import java.awt.*;

public class FinalPage extends JFrame {
    // constructor that creates and displays the final page window
    public FinalPage(String name, String role, int level, int experience, int killedEnemies) {
        // set the title of the JFrame
        setTitle("Info character");
        setSize(350, 700);
        // make sure that only this window is closed when exited
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // main panel for all components: image and character information
        JPanel mainPanel = new JPanel();
        // arrange components vertically
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(234, 251, 207));

        // load and display the character's image
        ImageIcon originalImage = new ImageIcon("src/pictures/eu.jpg");
        Image scaledImage = originalImage.getImage().getScaledInstance(280, 400, Image.SCALE_SMOOTH);
        // add the resized image to a JLabel
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // add the image to the main panel
        mainPanel.add(imageLabel);

        // panel for displaying character's information
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(4, 2, 5, 5));
        infoPanel.setBackground(new Color(234, 251, 207));
        infoPanel.setOpaque(true);

        // font style for text
        Font labelFont = new Font("Arial", Font.BOLD, 14);
        // add labels with character information to the info panel
        infoPanel.add(createInfoLabel("NAME: ", name, labelFont));
        infoPanel.add(createInfoLabel("ROLE: ", role, labelFont));
        infoPanel.add(createInfoLabel("LEVEL: ", String.valueOf(level), labelFont));
        infoPanel.add(createInfoLabel("EXPERIENCE: ", String.valueOf(experience), labelFont));
        infoPanel.add(createInfoLabel("KILLED ENEMIES: ", String.valueOf(killedEnemies), labelFont));

        // add the info panel to the main panel
        mainPanel.add(infoPanel);

        add(mainPanel, BorderLayout.CENTER);

        // panel for the EXIT button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(234, 251, 207));

        JButton exitButton = new JButton("EXIT");
        exitButton.setBackground(new Color(129, 0, 30));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.addActionListener(e -> dispose());

        // add the EXIT button to the button panel
        buttonPanel.add(exitButton);
        add(buttonPanel, BorderLayout.SOUTH);
        // make the JFrame visible
        setVisible(true);
    }

    // method that helps to create a stylized text
    private JPanel createInfoLabel(String labelText, String valueText, Font font) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(234, 251, 207));

        JLabel label = new JLabel(labelText);
        label.setFont(font);
        label.setForeground(Color.BLACK);

        JLabel value = new JLabel(valueText);
        value.setFont(new Font("Arial", Font.PLAIN, 14));
        value.setForeground(Color.BLACK);

        panel.add(label);
        panel.add(value);

        return panel;
    }
}
