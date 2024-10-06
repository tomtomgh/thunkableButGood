import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AddEventPage {

    private JPanel addEventPanel;
    private String selectedDay;  // The selected day (e.g., "Monday")
    private ArrayList<String> friendsList;  // Store the friends invited
    private JTextField eventTitleField;
    private JPanel mainPanelContainer;

    public AddEventPage(JPanel mainPanelContainer, String selectedDay) {
        this.selectedDay = selectedDay;  // Store the selected day
        this.mainPanelContainer = mainPanelContainer;
        this.friendsList = new ArrayList<>();  // Initialize the list of friends invited

        addEventPanel = new JPanel(new GridBagLayout());  // Use GridBagLayout for flexible positioning
        addEventPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Set padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(128, 158, 255));  // Blue background
        backButton.setForeground(Color.BLACK);  // Black text
        backButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Action Listener for back button
        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
            cl.show(mainPanelContainer, "DayViewPage-" + selectedDay);  // Go back to DayViewPage for the selected day
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Span across two columns
        addEventPanel.add(backButton, gbc);

        // Event Title Label
        JLabel titleLabel = new JLabel("NEW EVENT for " + selectedDay);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        addEventPanel.add(titleLabel, gbc);

        // Event Title Input Field
        JLabel eventTitleLabel = new JLabel("Title:");
        eventTitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        addEventPanel.add(eventTitleLabel, gbc);

        eventTitleField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        addEventPanel.add(eventTitleField, gbc);

        // Friend's Name Input Field
        JLabel friendNameLabel = new JLabel("Friend's Name:");
        friendNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        addEventPanel.add(friendNameLabel, gbc);

        JTextField friendNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        addEventPanel.add(friendNameField, gbc);

        // Friend's Email Input Field
        JLabel friendEmailLabel = new JLabel("Friend's Email:");
        friendEmailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        addEventPanel.add(friendEmailLabel, gbc);

        JTextField friendEmailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        addEventPanel.add(friendEmailField, gbc);

        // Add Friend Button
        JButton addFriendButton = new JButton("Add Friend");
        addFriendButton.setBackground(new Color(128, 158, 255));  // Blue background
        addFriendButton.setForeground(Color.BLACK);  // Black text
        addFriendButton.setFont(new Font("Arial", Font.BOLD, 16));

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        addEventPanel.add(addFriendButton, gbc);

        // Display list of friends added
        JTextArea friendsAddedArea = new JTextArea(5, 20);
        friendsAddedArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(friendsAddedArea);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        addEventPanel.add(scrollPane, gbc);

        // Action Listener for "Add Friend" button
        addFriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String friendName = friendNameField.getText();
                String friendEmail = friendEmailField.getText();
                String eventTitle = eventTitleField.getText();
                if (!friendName.isEmpty() && !friendEmail.isEmpty() && !eventTitle.isEmpty()) {
                    String friendInfo = friendName + " (" + friendEmail + ")";
                    friendsList.add(friendInfo);  // Add friend to the list
                    friendsAddedArea.append(friendInfo + "\n");
                    friendNameField.setText("");  // Clear input fields
                    friendEmailField.setText("");

                    // Save the data to the user_activities.txt file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_activities.txt", true))) {
                        writer.write(friendName + "," + friendEmail + "," + eventTitle);  // Save friend info and activity
                        writer.newLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                } else {
                    JOptionPane.showMessageDialog(addEventPanel, "Please fill all fields!");
                }
            }
        });

        // Suggestions Button (Purple with Fancy Style)
        JButton suggestionsButton = new JButton("Suggestions");
        suggestionsButton.setBackground(new Color(138, 43, 226));  // Purple background
        suggestionsButton.setForeground(Color.WHITE);  // White text
        suggestionsButton.setFont(new Font("Arial", Font.BOLD, 16));
        suggestionsButton.setPreferredSize(new Dimension(150, 50));  // Set button size

        // Action Listener for the Suggestions Button
        suggestionsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the current event title
                String eventTitle = getEventTitle();
                if (eventTitle == null || eventTitle.isEmpty()) {
                    JOptionPane.showMessageDialog(addEventPanel, "Please enter the event title first.");
                    return;
                }

                // Create the SuggestionsList page and pass the event title
                SuggestionsList suggestionsList = new SuggestionsList(mainPanelContainer, eventTitle);
                mainPanelContainer.add(suggestionsList.getSuggestionsListPanel(), "SuggestionsList");

                // Switch to the SuggestionsList page
                CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                cl.show(mainPanelContainer, "SuggestionsList");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        addEventPanel.add(suggestionsButton, gbc);

        // Done Button
        JButton doneButton = new JButton("DONE");
        doneButton.setBackground(new Color(128, 158, 255));  // Blue background
        doneButton.setForeground(Color.BLACK);  // Black text
        doneButton.setFont(new Font("Arial", Font.BOLD, 16));

        // Action Listener for done button
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String eventTitle = eventTitleField.getText();

                if (eventTitle.isEmpty()) {
                    JOptionPane.showMessageDialog(addEventPanel, "Please enter the event title.");
                    return;
                }

                // Store the event along with friends in the events.txt file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("events.txt", true))) {
                    writer.write(eventTitle + "," + selectedDay);
                    if (!friendsList.isEmpty()) {
                        writer.write("," + String.join(" | ", friendsList));  // Add invited friends to the same line
                    }
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(addEventPanel,
                    "Event Created: \nTitle: " + eventTitle + " on " + selectedDay);

                // Go back to DayViewPage when event is created
                CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                cl.show(mainPanelContainer, "DayViewPage-" + selectedDay);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        addEventPanel.add(doneButton, gbc);
    }

    public JPanel getAddEventPanel() {
        return addEventPanel;
    }

    public String getEventTitle() {
        return eventTitleField.getText();  // Return the current event title
    }
}
