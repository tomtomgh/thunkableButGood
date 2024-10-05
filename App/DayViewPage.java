import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DayViewPage {

    private JPanel dayViewPanel;

    public DayViewPage(JPanel mainPanelContainer) {
        dayViewPanel = new JPanel(new BorderLayout());
        dayViewPanel.setBackground(Color.WHITE);  // Set background color

        // Title label (Add Event Button)
        JButton addEventButton = new JButton("Add Event");
        addEventButton.setFont(new Font("Arial", Font.BOLD, 16));
        addEventButton.setPreferredSize(new Dimension(400, 50));

        // Action Listener for adding an event
        addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(dayViewPanel, "Event added for the day!");
            }
        });
        dayViewPanel.add(addEventButton, BorderLayout.NORTH);

        // Placeholder for displaying planned events
        JTextArea eventsTextArea = new JTextArea("Today's Events:\n\nNo events planned yet.");
        eventsTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        eventsTextArea.setEditable(false);
        dayViewPanel.add(new JScrollPane(eventsTextArea), BorderLayout.CENTER);

        // Exit button to return to the main page
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(400, 50));
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Action Listener for the exit button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                cl.show(mainPanelContainer, "MainPage");  // Switch back to the MainPage
            }
        });

        dayViewPanel.add(exitButton, BorderLayout.SOUTH);
    }

    public JPanel getDayViewPanel() {
        return dayViewPanel;
    }
}
