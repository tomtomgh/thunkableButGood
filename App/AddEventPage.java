import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEventPage {

    private JPanel addEventPanel;

    public AddEventPage(JPanel mainPanelContainer) {
        addEventPanel = new JPanel();
        addEventPanel.setLayout(new BoxLayout(addEventPanel, BoxLayout.Y_AXIS));
        addEventPanel.setBackground(Color.WHITE);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.setBackground(new Color(128, 158, 255));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(80, 40));

        // Action Listener for back button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to DayViewPage when Back button is clicked
                CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                cl.show(mainPanelContainer, "DayViewPage");
            }
        });

        // Event Title
        JLabel titleLabel = new JLabel("NEW EVENT");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Event Input Fields
        JTextField titleField = new JTextField("Title");
        JTextField dateField = new JTextField("When?");
        JTextField fromField = new JTextField("From");
        JTextField toField = new JTextField("To");
        JTextField friendField = new JTextField("abc.xyz@example.com");
        JTextField nameField = new JTextField("Name");

        // Add Event Button
        JButton doneButton = new JButton("DONE");
        doneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        doneButton.setBackground(new Color(128, 158, 255));
        doneButton.setForeground(Color.WHITE);
        doneButton.setFont(new Font("Arial", Font.BOLD, 16));
        doneButton.setPreferredSize(new Dimension(80, 40));

        // Action Listener for done button (Save event)
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(addEventPanel, "Event Created!");
                // Go back to DayViewPage when event is created
                CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                cl.show(mainPanelContainer, "DayViewPage");
            }
        });

        // Add components to the panel
        addEventPanel.add(backButton);
        addEventPanel.add(Box.createRigidArea(new Dimension(0, 10)));  // Spacer
        addEventPanel.add(titleLabel);
        addEventPanel.add(titleField);
        addEventPanel.add(dateField);
        addEventPanel.add(fromField);
        addEventPanel.add(toField);
        addEventPanel.add(friendField);
        addEventPanel.add(nameField);
        addEventPanel.add(doneButton);
    }

    public JPanel getAddEventPanel() {
        return addEventPanel;
    }
}
