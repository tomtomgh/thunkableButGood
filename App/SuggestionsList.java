import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SuggestionsList {

    private JPanel suggestionsListPanel;

    public SuggestionsList(JPanel mainPanelContainer, String eventTitle) {
        suggestionsListPanel = new JPanel(new BorderLayout());
        suggestionsListPanel.setBackground(Color.WHITE);
        
        // Title label
        JLabel titleLabel = new JLabel("Friend Suggestions for: " + eventTitle, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        suggestionsListPanel.add(titleLabel, BorderLayout.NORTH);

        // Area to display the suggested friends
        JTextArea suggestionsArea = new JTextArea(10, 30);
        suggestionsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(suggestionsArea);
        suggestionsListPanel.add(scrollPane, BorderLayout.CENTER);

        // Back Button to go back to AddEventPage
        JButton backButton = new JButton("Back to Event");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(128, 158, 255));  // Blue background
        backButton.setForeground(Color.BLACK);  // Black text

        // Action listener to return to AddEventPage
        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
            cl.show(mainPanelContainer, "AddEventPage");  // Reuse the same AddEventPage
        });

        suggestionsListPanel.add(backButton, BorderLayout.SOUTH);

        // Load suggestions based on event title
        loadSuggestions(eventTitle, suggestionsArea);
    }

    // Method to load and filter suggestions
    private void loadSuggestions(String eventTitle, JTextArea suggestionsArea) {
        try (BufferedReader reader = new BufferedReader(new FileReader("user_activities.txt"))) {
            String line;
            ArrayList<String> suggestions = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3 && data[2].equalsIgnoreCase(eventTitle)) {
                    // Match found: add name and email to suggestions
                    suggestions.add(data[0] + " (" + data[1] + ")");
                }
            }

            // Display the suggestions in the text area
            if (!suggestions.isEmpty()) {
                for (String suggestion : suggestions) {
                    suggestionsArea.append(suggestion + "\n");
                }
            } else {
                suggestionsArea.append("No suggestions found for this event.\n");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            suggestionsArea.append("Error loading suggestions.\n");
        }
    }

    public JPanel getSuggestionsListPanel() {
        return suggestionsListPanel;
    }
}
