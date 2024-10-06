import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainPage {

    private JPanel mainPanel;

    public MainPage(JPanel mainPanelContainer) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(169, 169, 255));  // Set background color

        // Title label
        JLabel titleLabel = new JLabel("PlanifAI", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(104, 159, 255));  // Light blue
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setPreferredSize(new Dimension(400, 60));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Panel for the 7 day buttons
        JPanel daysPanel = new JPanel();
        daysPanel.setLayout(new GridLayout(7, 1, 10, 10));  // Grid with 7 rows, 1 column

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (int i = 0; i < days.length; i++) {
            // Create a layered pane to allow stacking of components (buttons over labels)
            JLayeredPane layeredPane = new JLayeredPane();
            layeredPane.setPreferredSize(new Dimension(400, 60));  // Ensure consistent sizing

            // Day Label
            JLabel dayLabel = new JLabel(days[i], SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 20));
            dayLabel.setOpaque(true);
            dayLabel.setBounds(0, 0, 400, 60);  // Absolute positioning
            dayLabel.setBackground(new Color(128, 158, 255));
            dayLabel.setForeground(Color.WHITE);

            // "+" Button to add an event
            JButton addButton = new JButton("+");
            addButton.setBounds(10, 10, 40, 40);  // Absolute positioning on top of label
            addButton.setBackground(Color.YELLOW);  // Yellow background for add button
            addButton.setForeground(Color.BLACK);
            addButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Action Listener for the "+" button
            String day = days[i];  // Final or effectively final variable to use inside ActionListener
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Add button for " + day + " clicked.");
                    CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                    DayViewPage dayViewPage = new DayViewPage(mainPanelContainer, day);
                    mainPanelContainer.add(dayViewPage.getDayViewPanel(), "DayViewPage-" + day);
                    cl.show(mainPanelContainer, "DayViewPage-" + day);
                }
            });

            // Notification Button (Reminder Button)
            JButton reminderButton = new JButton("⏰");
            reminderButton.setBounds(350, 10, 40, 40);  // Absolute positioning on top of label
            reminderButton.setBackground(Color.GREEN);  // Green background for reminder button
            reminderButton.setForeground(Color.BLACK);
            reminderButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Action Listener for the reminder button
            reminderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Reminder button for " + day + " clicked.");
                    CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                    cl.show(mainPanelContainer, "ReminderPage");
                }
            });

            // Add components to the layered pane
            layeredPane.add(dayLabel, JLayeredPane.DEFAULT_LAYER);  // Background label at the base
            layeredPane.add(addButton, JLayeredPane.PALETTE_LAYER);  // Add button overlaid on top
            layeredPane.add(reminderButton, JLayeredPane.PALETTE_LAYER);  // Reminder button overlaid on top

            // Add the layered pane to the main panel
            daysPanel.add(layeredPane);
        }

        mainPanel.add(daysPanel, BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
