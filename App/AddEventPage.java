import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;

public class AddEventPage {

    private JPanel addEventPanel;
    private String selectedDay;  // The selected day (e.g., "Monday")
    private ArrayList<String> friendsList;  // Store the friends invited
    private JTextField eventTitleField;
    private JTextArea friendsAddedArea;  // Make this an instance variable
    private JPanel mainPanelContainer;
    private AddEventPage addEventPageInstance;  // Reference to self for passing to SuggestionsList

    // New instance variables for time spinners
    private JSpinner fromTimeSpinner;
    private JSpinner toTimeSpinner;

    public AddEventPage(JPanel mainPanelContainer, String selectedDay) {
        this.selectedDay = selectedDay;  // Store the selected day
        this.mainPanelContainer = mainPanelContainer;
        this.friendsList = new ArrayList<>();  // Initialize the list of friends invited
        this.addEventPageInstance = this;  // Reference to self

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

        // From Time Spinner
        JLabel fromLabel = new JLabel("From:");
        fromLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 3;
        addEventPanel.add(fromLabel, gbc);

        SpinnerDateModel fromTimeModel = new SpinnerDateModel();
        fromTimeSpinner = new JSpinner(fromTimeModel);
        JSpinner.DateEditor timeEditorFrom = new JSpinner.DateEditor(fromTimeSpinner, "hh:mm a");
        fromTimeSpinner.setEditor(timeEditorFrom);

        gbc.gridx = 1;
        gbc.gridy = 3;
        addEventPanel.add(fromTimeSpinner, gbc);

        // To Time Spinner
        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 4;
        addEventPanel.add(toLabel, gbc);

        SpinnerDateModel toTimeModel = new SpinnerDateModel();
        toTimeSpinner = new JSpinner(toTimeModel);
        JSpinner.DateEditor timeEditorTo = new JSpinner.DateEditor(toTimeSpinner, "hh:mm a");
        toTimeSpinner.setEditor(timeEditorTo);

        gbc.gridx = 1;
        gbc.gridy = 4;
        addEventPanel.add(toTimeSpinner, gbc);

        // Friend's Name Input Field
        JLabel friendNameLabel = new JLabel("Friend's Name:");
        friendNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 5;
        addEventPanel.add(friendNameLabel, gbc);

        JTextField friendNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        addEventPanel.add(friendNameField, gbc);

        // Friend's Email Input Field
        JLabel friendEmailLabel = new JLabel("Friend's Email:");
        friendEmailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 6;
        addEventPanel.add(friendEmailLabel, gbc);

        JTextField friendEmailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 6;
        addEventPanel.add(friendEmailField, gbc);

        // Add Friend Button
        JButton addFriendButton = new JButton("Add Friend");
        addFriendButton.setBackground(new Color(128, 158, 255));  // Blue background
        addFriendButton.setForeground(Color.BLACK);  // Black text
        addFriendButton.setFont(new Font("Arial", Font.BOLD, 16));

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        addEventPanel.add(addFriendButton, gbc);

        // Display list of friends added
        friendsAddedArea = new JTextArea(5, 20);
        friendsAddedArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(friendsAddedArea);
        gbc.gridx = 0;
        gbc.gridy = 8;
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
        suggestionsButton.setForeground(new Color(138, 43, 226));  // White text
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

                // Create the SuggestionsList page and pass the event title and selectedDay
                SuggestionsList suggestionsList = new SuggestionsList(mainPanelContainer, eventTitle, selectedDay, addEventPageInstance);
                mainPanelContainer.add(suggestionsList.getSuggestionsListPanel(), "SuggestionsList-" + selectedDay);

                // Switch to the SuggestionsList page
                CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                cl.show(mainPanelContainer, "SuggestionsList-" + selectedDay);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 9;
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

                // Get the selected times
                Date fromTime = (Date) fromTimeSpinner.getValue();
                Date toTime = (Date) toTimeSpinner.getValue();
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
                String fromTimeStr = timeFormat.format(fromTime);
                String toTimeStr = timeFormat.format(toTime);

                // // Calculate reminder time
                // Calendar cal = Calendar.getInstance();
                // cal.setTime(fromTime);
                // cal.add(Calendar.MINUTE, -30); // -30 should be 
                // Date reminderTime = cal.getTime();
                // // Save event with reminder time
                // SimpleDateFormat reminderFormat = new SimpleDateFormat("hh:mm a");
                // //String reminderTimeStr = reminderFormat.format(reminderTime);

                // // Calculate reminder time with default offset
                // int reminderOffset = -30; // Default 30 minutes before
                // // Check for keywords and adjust reminder time
                // if (eventTitle.contains("gym")) {
                //     reminderOffset = -60; // 1 hour before for gym
                // } else if (eventTitle.contains("airport") || eventTitle.contains("airplane") || eventTitle.contains("flight")) {
                //     reminderOffset = -240; // 4 hours before for airport/flight
                // } else if (eventTitle.contains("groceries")) {
                //     reminderOffset = -5; // 5 minutes before for groceries
                // }
                // int hourbefore = reminderOffset/60;
                // int minutebefore = reminderOffset%60;
                // cal.add(Calendar.HOUR, hourbefore);
                // cal.add(Calendar.MINUTE, minutebefore);
                // String reminderTimeStr = reminderFormat.format(reminderTime); 
                String from = timeFormat.format(fromTime);
                String to = timeFormat.format(toTime);

                // // Calculate reminder time
                Calendar cal = Calendar.getInstance();
                cal.setTime(fromTime);
                // cal.add(Calendar.MINUTE, -30); // -30 here means 30 mins before
                // Date reminderTime = cal.getTime();
                // // Save event with reminder time
                // SimpleDateFormat reminderFormat = new SimpleDateFormat("hh:mm a");
                // String reminderTimeStr = reminderFormat.format(reminderTime);
                
                // Calculate reminder time with default offset
                int reminderOffset = -30; // Default 30 minutes before
                // Check for keywords and adjust reminder time
                if (eventTitle.contains("gym")) {
                    reminderOffset = -60; // 1 hour before for gym
                } else if (eventTitle.contains("airport") || eventTitle.contains("airplane") || eventTitle.contains("flight")) {
                    reminderOffset = -240; // 4 hours before for airport/flight
                } else if (eventTitle.contains("groceries")) {
                    reminderOffset = -5; // 5 minutes before for groceries
                }
                int hourbefore = reminderOffset/60;
                int minutebefore = reminderOffset%60;
                cal.add(Calendar.HOUR, +hourbefore);
                cal.add(Calendar.MINUTE, +minutebefore);
                Date reminderTime = cal.getTime();
                SimpleDateFormat reminderFormat = new SimpleDateFormat("hh:mm a");
                String reminderTimeStr = reminderFormat.format(reminderTime); 

                // Optional: Validate that the 'From' time is before the 'To' time
                if (fromTime.after(toTime)) {
                    JOptionPane.showMessageDialog(addEventPanel, "The 'From' time must be before the 'To' time.");
                    return;
                }

                // Store the event along with friends in the events.txt file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("events.txt", true))) {
                    writer.write(eventTitle + "," + fromTimeStr + "," + toTimeStr + "," + selectedDay + "," + reminderTimeStr);
                    if (!friendsList.isEmpty()) {
                        writer.write("," + String.join(" | ", friendsList));  // Add invited friends to the same line
                    }
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(addEventPanel,
                    "Event Created: \nTitle: " + eventTitle +
                    "\nFrom: " + fromTimeStr + " to " + toTimeStr +
                    " on " + selectedDay);

                // Go back to DayViewPage when event is created
                CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                cl.show(mainPanelContainer, "DayViewPage-" + selectedDay);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        addEventPanel.add(doneButton, gbc);
    }

    public JPanel getAddEventPanel() {
        return addEventPanel;
    }

    public String getEventTitle() {
        return eventTitleField.getText();  // Return the current event title
    }

    // Method to add friends from SuggestionsList
    public void addFriendsFromSuggestions(ArrayList<String> selectedFriends) {
        for (String friendInfo : selectedFriends) {
            if (!friendsList.contains(friendInfo)) {
                friendsList.add(friendInfo);
                friendsAddedArea.append(friendInfo + "\n");
            }
        }
    }
}
