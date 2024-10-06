import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class DayViewPage {

    private JPanel dayViewPanel;
    private JTextArea eventsTextArea;
    private String selectedDay;  // The selected day (e.g., "Monday")

    public DayViewPage(JPanel mainPanelContainer, String selectedDay) {
        this.selectedDay = selectedDay;  // Store the selected day
        dayViewPanel = new JPanel(new BorderLayout());
        dayViewPanel.setBackground(Color.WHITE);  // Set background color

        // Title label (Add Event Button)
        JButton addEventButton = new JButton("Add Event for " + selectedDay);
        addEventButton.setFont(new Font("Arial", Font.BOLD, 16));
        addEventButton.setPreferredSize(new Dimension(400, 50));

        // Action Listener for adding an event (Leads to AddEventPage)
        addEventButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
            AddEventPage addEventPage = new AddEventPage(mainPanelContainer, selectedDay);  // Pass the selected day
            mainPanelContainer.add(addEventPage.getAddEventPanel(), "AddEventPage-" + selectedDay);
            cl.show(mainPanelContainer, "AddEventPage-" + selectedDay);  // Switch to AddEventPage
        });
        dayViewPanel.add(addEventButton, BorderLayout.NORTH);

        // Placeholder for displaying planned events
        eventsTextArea = new JTextArea();
        eventsTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        eventsTextArea.setEditable(false);
        dayViewPanel.add(new JScrollPane(eventsTextArea), BorderLayout.CENTER);

        // Exit button to return to the main page
        JButton exitButton = new JButton("Exit");
        exitButton.setPreferredSize(new Dimension(400, 50));
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Action Listener for the exit button
        exitButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
            cl.show(mainPanelContainer, "MainPage");  // Switch back to the MainPage
        });

        dayViewPanel.add(exitButton, BorderLayout.SOUTH);
        loadEventsForDay(selectedDay);  // Load events for the selected day
    }

    public JPanel getDayViewPanel() {
        return dayViewPanel;
    }

    // Method to load events for the selected day (e.g., Monday)
    public void loadEventsForDay(String day) {
        eventsTextArea.setText("");  // Clear the current display
        try (BufferedReader reader = new BufferedReader(new FileReader("events.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parse each line to get the event details
                String[] eventDetails = line.split(",");
                if (eventDetails.length >= 4) {
                    String title = eventDetails[0];
                    String fromTime = eventDetails[1];
                    String toTime = eventDetails[2];
                    String eventDay = eventDetails[3];
                    String friends = eventDetails.length > 4 ? eventDetails[4] : "";

                    // Only show events for the selected day
                    if (eventDay.equalsIgnoreCase(day)) {
                        eventsTextArea.append("Title: " + title + "\n");
                        eventsTextArea.append("Time: " + fromTime + " - " + toTime + "\n");
                        if (!friends.isEmpty()) {
                            eventsTextArea.append("Invited Friends: " + friends + "\n");
                        }
                        eventsTextArea.append("\n");
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
