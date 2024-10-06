import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        for (String day : days) {
            JPanel dayPanel = new JPanel(new BorderLayout());

            // "+" Button to add an event
            JButton addButton = new JButton("+");
            addButton.setPreferredSize(new Dimension(50, 50));
            addButton.setBackground(new Color(255, 178, 45));  // Yellowish color
            addButton.setForeground(Color.WHITE);
            addButton.setBorder(BorderFactory.createEmptyBorder());

            // Action Listener for the "+" button
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                    DayViewPage dayViewPage = new DayViewPage(mainPanelContainer, day);  // Pass the selected day
                    mainPanelContainer.add(dayViewPage.getDayViewPanel(), "DayViewPage-" + day);
                    cl.show(mainPanelContainer, "DayViewPage-" + day);  // Switch to the DayViewPage
                }
            });

            // Center label for the day
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 16));
            dayLabel.setForeground(Color.WHITE);
            dayLabel.setOpaque(true);
            dayLabel.setBackground(new Color(128, 158, 255));  // Set color for day labels

            // Notification Button (Bell icon)
            JButton reminderButton = new JButton(new ImageIcon("bell_icon.png"));
            reminderButton.setPreferredSize(new Dimension(50, 50));
            reminderButton.setBorder(BorderFactory.createEmptyBorder());

            dayPanel.add(addButton, BorderLayout.WEST);  // "+" button on the left
            dayPanel.add(dayLabel, BorderLayout.CENTER);  // Day of the week label in the center
            dayPanel.add(reminderButton, BorderLayout.EAST);  // Notification button on the right
            dayPanel.setBackground(new Color(255, 255, 255));  // Panel color for each row
            // Action Listener for the "+" button
           
            
             reminderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                    cl.show(mainPanelContainer, "reminderPage");  // Switch to the reminderPage
                }
            });


            daysPanel.add(dayPanel);
        }

        mainPanel.add(daysPanel, BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
