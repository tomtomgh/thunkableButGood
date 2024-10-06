import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SuggestionsList {

    private JPanel suggestionsListPanel;
    private AddEventPage addEventPage;  // Reference to AddEventPage

    public SuggestionsList(JPanel mainPanelContainer, String eventTitle, String selectedDay, AddEventPage addEventPage) {
        this.addEventPage = addEventPage;  // Store the reference

        suggestionsListPanel = new JPanel(new BorderLayout());
        suggestionsListPanel.setBackground(Color.WHITE);

        // Title label
        JLabel titleLabel = new JLabel("Friend Suggestions for: " + eventTitle, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLACK);
        suggestionsListPanel.add(titleLabel, BorderLayout.NORTH);

        // List to display the suggested friends
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> suggestionsJList = new JList<>(listModel);
        suggestionsJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollPane = new JScrollPane(suggestionsJList);
        suggestionsListPanel.add(scrollPane, BorderLayout.CENTER);

        // Load suggestions based on event title
        loadSuggestions(eventTitle, listModel);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // "Add Selected Friends" Button
        JButton addSelectedButton = new JButton("Add Selected Friends");
        addSelectedButton.setFont(new Font("Arial", Font.BOLD, 16));
        addSelectedButton.setBackground(new Color(50, 205, 50));  // Green background
        addSelectedButton.setForeground(Color.BLACK);  // White text

        addSelectedButton.addActionListener(e -> {
            // Get selected friends
            ArrayList<String> selectedFriends = new ArrayList<>(suggestionsJList.getSelectedValuesList());
            if (!selectedFriends.isEmpty()) {
                // Add selected friends to AddEventPage
                addEventPage.addFriendsFromSuggestions(selectedFriends);
                JOptionPane.showMessageDialog(suggestionsListPanel, "Selected friends added to event.");
            } else {
                JOptionPane.showMessageDialog(suggestionsListPanel, "Please select at least one friend.");
            }
        });

        buttonPanel.add(addSelectedButton);

        // Back Button to go back to AddEventPage
        JButton backButton = new JButton("Back to Event");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(128, 158, 255));  // Blue background
        backButton.setForeground(Color.BLACK);  // Black text

        // Action listener to return to AddEventPage
        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
            // Use the unique card name for AddEventPage
            cl.show(mainPanelContainer, "AddEventPage-" + selectedDay);  // Reuse the same AddEventPage
        });

        buttonPanel.add(backButton);

        suggestionsListPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to load and filter suggestions
    private void loadSuggestions(String eventTitle, DefaultListModel<String> listModel) {
        Map<String, Set<String>> relatedKeywords = getRelatedKeywords();

        // Get related keywords for the event title
        Set<String> keywords = relatedKeywords.getOrDefault(eventTitle.toLowerCase(), new HashSet<>());
        if (keywords.isEmpty()) {
            // If no related keywords, include the event title itself
            keywords.add(eventTitle.toLowerCase());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("user_activities.txt"))) {
            String line;
            Set<String> suggestions = new HashSet<>();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String activity = data[2].toLowerCase();
                    // Check if the activity matches any of the keywords
                    if (keywords.contains(activity)) {
                        String friendInfo = data[0] + " (" + data[1] + ")";
                        suggestions.add(friendInfo);
                    }
                }
            }

            // Populate the list model
            if (!suggestions.isEmpty()) {
                for (String suggestion : suggestions) {
                    listModel.addElement(suggestion);
                }
            } else {
                listModel.addElement("No suggestions found for this event.");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            listModel.addElement("Error loading suggestions.");
        }
    }

    // Method to get related keywords
    private Map<String, Set<String>> getRelatedKeywords() {
        Map<String, Set<String>> relatedKeywords = new HashMap<>();

        // Map for "pool"
        Set<String> poolRelated = new HashSet<>(Arrays.asList("pool", "swimming", "swimming pool", "swim"));
        relatedKeywords.put("pool", poolRelated);

        // Map for "hike"
        Set<String> hikeRelated = new HashSet<>(Arrays.asList("hike", "walk", "run", "camping"));
        relatedKeywords.put("hike", hikeRelated);

        // Add more mappings as needed

        return relatedKeywords;
    }

    public JPanel getSuggestionsListPanel() {
        return suggestionsListPanel;
    }
}
