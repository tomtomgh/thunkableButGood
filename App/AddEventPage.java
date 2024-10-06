import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEventPage {

    private JPanel addEventPanel;

    public AddEventPage(JPanel mainPanelContainer, String selectedDay) {
        addEventPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Add padding around elements
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title label
        JLabel titleLabel = new JLabel("NEW EVENT for " + selectedDay);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        addEventPanel.add(titleLabel, gbc);

        // Event title input
        JLabel eventTitleLabel = new JLabel("Title:");
        eventTitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        addEventPanel.add(eventTitleLabel, gbc);

        JTextField eventTitleField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        addEventPanel.add(eventTitleField, gbc);

        // Time input (from/to)
        JLabel fromLabel = new JLabel("From:");
        fromLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        addEventPanel.add(fromLabel, gbc);

        JSpinner fromTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditorFrom = new JSpinner.DateEditor(fromTimeSpinner, "hh:mm a");
        fromTimeSpinner.setEditor(timeEditorFrom);
        gbc.gridx = 1;
        gbc.gridy = 2;
        addEventPanel.add(fromTimeSpinner, gbc);

        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        addEventPanel.add(toLabel, gbc);

        JSpinner toTimeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditorTo = new JSpinner.DateEditor(toTimeSpinner, "hh:mm a");
        toTimeSpinner.setEditor(timeEditorTo);
        gbc.gridx = 1;
        gbc.gridy = 3;
        addEventPanel.add(toTimeSpinner, gbc);

        // Friend's Name and Email input
        JLabel friendNameLabel = new JLabel("Friend's Name:");
        friendNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        addEventPanel.add(friendNameLabel, gbc);

        JTextField friendNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        addEventPanel.add(friendNameField, gbc);

        JLabel friendEmailLabel = new JLabel("Friend's Email:");
        friendEmailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 5;
        addEventPanel.add(friendEmailLabel, gbc);

        JTextField friendEmailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        addEventPanel.add(friendEmailField, gbc);

        // Suggestions Button (Fancy purple button)
        JButton suggestionsButton = new JButton("Suggestions 💡");  // Button label includes a lightbulb emoji
        suggestionsButton.setBackground(new Color(138, 43, 226));   // Purple color
        suggestionsButton.setForeground(Color.WHITE);               // White text for contrast
        suggestionsButton.setFont(new Font("Arial", Font.BOLD, 16));  // Bold font
        suggestionsButton.setFocusPainted(false);                    // Remove button focus border
        suggestionsButton.setBorder(BorderFactory.createLineBorder(new Color(85, 26, 139), 2)); // Add a border
        suggestionsButton.setPreferredSize(new Dimension(150, 50));  // Set button size
        suggestionsButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Set hand cursor

        // Action Listener for "Suggestions" button (Future AI functionality)
        suggestionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(addEventPanel, "AI Friend Suggestions Coming Soon!");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        addEventPanel.add(suggestionsButton, gbc);

        // Done Button
        JButton doneButton = new JButton("Done");
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        addEventPanel.add(doneButton, gbc);
    }

    public JPanel getAddEventPanel() {
        return addEventPanel;
    }
}
