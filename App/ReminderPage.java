import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ReminderPage {

    private static JPanel reminderPanel;

    public ReminderPage(JPanel mainPanelContainer) {
        reminderPanel = new JPanel();
        reminderPanel.setLayout(new BorderLayout());
        reminderPanel.setBackground(Color.WHITE); // Background color of the entire page

        // Title panel for PlanifAI and Reminder
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(2, 1, 0, 5)); // 2 rows, 1 column with space between rows
        titlePanel.setBackground(Color.WHITE);

        // PlanifAI title
        JLabel planifAILabel = new JLabel("PlanifAI", SwingConstants.CENTER);
        planifAILabel.setFont(new Font("Arial", Font.BOLD, 20));
        planifAILabel.setOpaque(true);
        planifAILabel.setBackground(new Color(104, 159, 255)); // Light blue background
        planifAILabel.setForeground(Color.BLACK);
        planifAILabel.setPreferredSize(new Dimension(300, 50));
        titlePanel.add(planifAILabel);

        // Reminder title
        JLabel reminderLabel = new JLabel("Reminder", SwingConstants.CENTER);
        reminderLabel.setFont(new Font("Arial", Font.BOLD, 20));
        reminderLabel.setOpaque(true);
        reminderLabel.setBackground(new Color(255, 178, 45)); // Yellow background
        reminderLabel.setForeground(Color.BLACK);
        reminderLabel.setPreferredSize(new Dimension(300, 50));
        titlePanel.add(reminderLabel);

        reminderPanel.add(titlePanel, BorderLayout.NORTH);

        // Center panel for reminder details
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        // Bell icon in the center
        JLabel bellIconLabel = new JLabel(new ImageIcon("bell_icon.png"), SwingConstants.CENTER);
        centerPanel.add(bellIconLabel, BorderLayout.NORTH);

        // Reminder time and activity
        JPanel reminderContentPanel = new JPanel();
        reminderContentPanel.setLayout(new GridLayout(2, 1));
        reminderContentPanel.setBackground(new Color(255, 178, 45)); // Orange background for reminder box

        JLabel timeLabel = new JLabel("In 30 mins", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timeLabel.setForeground(Color.BLACK);
        reminderContentPanel.add(timeLabel);

        JLabel activityLabel = new JLabel("Gym Training 17:00 - 18:30", SwingConstants.CENTER);
        activityLabel.setFont(new Font("Arial", Font.BOLD, 16));
        activityLabel.setForeground(Color.BLACK);
        reminderContentPanel.add(activityLabel);

        centerPanel.add(reminderContentPanel, BorderLayout.CENTER);
        reminderPanel.add(centerPanel, BorderLayout.CENTER);

        // Dismiss button at the bottom
        JButton dismissButton = new JButton("Dismiss");
        dismissButton.setFont(new Font("Arial", Font.BOLD, 16));
        dismissButton.setBackground(new Color(255, 178, 45)); // Orange background for button
        dismissButton.setForeground(Color.BLACK);
        dismissButton.setBorder(BorderFactory.createEmptyBorder());
        dismissButton.setPreferredSize(new Dimension(100, 50));

        dismissButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                cl.show(mainPanelContainer, "MainPage"); // Switch back to the main page
            }
        });

        reminderPanel.add(dismissButton, BorderLayout.SOUTH);
    }

    public JPanel getReminderPanel() {
        return reminderPanel;
    }

    public static void updateReminderLabels(String title, String time) {
        JPanel centerPanel = (JPanel) reminderPanel.getComponent(1);
        JPanel reminderContentPanel = (JPanel) centerPanel.getComponent(1);
    
        JLabel timeLabel = (JLabel) reminderContentPanel.getComponent(0);
        JLabel activityLabel = (JLabel) reminderContentPanel.getComponent(1);
    
        timeLabel.setText("Reminder: " + time);
        activityLabel.setText(title);
    }
}
