import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddEventPage {

    private JPanel addEventPanel;
    private String selectedDay;  // The selected day (e.g., "Monday")
    private ArrayList<String> friendsList;  // Store the friends invited

    public AddEventPage(JPanel mainPanelContainer, String selectedDay) {
        this.selectedDay = selectedDay;  // Store the selected day
        this.friendsList = new ArrayList<>();  // Initialize the list of friends invited

        addEventPanel = new JPanel(new GridBagLayout());  // Use GridBagLayout for flexible positioning
        addEventPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);  // Set padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(128, 158, 255));
        backButton.setForeground(Color.WHITE);
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

        JTextField eventTitleField = new JTextField(20);
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
        JSpinner fromTimeSpinner = new JSpinner(fromTimeModel);
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
        JSpinner toTimeSpinner = new JSpinner(toTimeModel);
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
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        addEventPanel.add(addFriendButton, gbc);

        // Display list of friends added
        JTextArea friendsAddedArea = new JTextArea(5, 20);
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
                if (!friendName.isEmpty() && !friendEmail.isEmpty()) {
                    String friendInfo = friendName + " (" + friendEmail + ")";
                    friendsList.add(friendInfo);  // Add friend to the list
                    friendsAddedArea.append(friendInfo + "\n");
                    friendNameField.setText("");  // Clear input fields
                    friendEmailField.setText("");
                } else {
                    JOptionPane.showMessageDialog(addEventPanel, "Please fill both fields!");
                }
            }
        });

        // Done Button
        JButton doneButton = new JButton("DONE");
        doneButton.setBackground(new Color(128, 158, 255));
        doneButton.setForeground(Color.WHITE);
        doneButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Action Listener for done button
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date fromTime = (Date) fromTimeSpinner.getValue();
                Date toTime = (Date) toTimeSpinner.getValue();
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

                String eventTitle = eventTitleField.getText();
                String from = timeFormat.format(fromTime);
                String to = timeFormat.format(toTime);

                // Store the event along with friends in the same file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("events.txt", true))) {
                    writer.write(eventTitle + "," + from + "," + to + "," + selectedDay);
                    if (!friendsList.isEmpty()) {
                        writer.write("," + String.join(" | ", friendsList));  // Add invited friends to the same line
                    }
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(addEventPanel,
                    "Event Created: \nTitle: " + eventTitle +
                    "\nFrom: " + from + " to " + to + " on " + selectedDay);

                // Go back to DayViewPage when event is created
                CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                cl.show(mainPanelContainer, "DayViewPage-" + selectedDay);
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        addEventPanel.add(doneButton, gbc);
    }

    public JPanel getAddEventPanel() {
        return addEventPanel;
    }
}
