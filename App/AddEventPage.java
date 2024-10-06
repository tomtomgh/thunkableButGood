import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEventPage {

    private JPanel addEventPanel;

    public AddEventPage(JPanel mainPanelContainer) {
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
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to DayViewPage when Back button is clicked
                CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                cl.show(mainPanelContainer, "DayViewPage");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Span across two columns
        addEventPanel.add(backButton, gbc);

        // Event Title Label
        JLabel titleLabel = new JLabel("NEW EVENT");
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

        // Event Description Input Field
        JLabel eventDescriptionLabel = new JLabel("Description:");
        eventDescriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 3;
        addEventPanel.add(eventDescriptionLabel, gbc);

        JTextArea eventDescriptionArea = new JTextArea(3, 20);
        JScrollPane descriptionScroll = new JScrollPane(eventDescriptionArea);
        gbc.gridx = 1;
        gbc.gridy = 3;
        addEventPanel.add(descriptionScroll, gbc);

        // Date Picker (JDateChooser)
        JLabel dateLabel = new JLabel("When?");
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 4;
        addEventPanel.add(dateLabel, gbc);

        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("MMM d, yyyy");

        gbc.gridx = 1;
        gbc.gridy = 4;
        addEventPanel.add(dateChooser, gbc);
        
        // From Time Spinner
        JLabel fromLabel = new JLabel("From:");
        fromLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 5;
        addEventPanel.add(fromLabel, gbc);

        SpinnerDateModel fromTimeModel = new SpinnerDateModel();
        JSpinner fromTimeSpinner = new JSpinner(fromTimeModel);
        JSpinner.DateEditor timeEditorFrom = new JSpinner.DateEditor(fromTimeSpinner, "hh:mm a");
        fromTimeSpinner.setEditor(timeEditorFrom);

        gbc.gridx = 1;
        gbc.gridy = 5;
        addEventPanel.add(fromTimeSpinner, gbc);

        // To Time Spinner
        JLabel toLabel = new JLabel("To:");
        toLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        gbc.gridx = 0;
        gbc.gridy = 6;
        addEventPanel.add(toLabel, gbc);

        SpinnerDateModel toTimeModel = new SpinnerDateModel();
        JSpinner toTimeSpinner = new JSpinner(toTimeModel);
        JSpinner.DateEditor timeEditorTo = new JSpinner.DateEditor(toTimeSpinner, "hh:mm a");
        toTimeSpinner.setEditor(timeEditorTo);

        gbc.gridx = 1;
        gbc.gridy = 6;
        addEventPanel.add(toTimeSpinner, gbc);

        // Done Button
        JButton doneButton = new JButton("DONE");
        doneButton.setBackground(new Color(128, 158, 255));
        doneButton.setForeground(Color.WHITE);
        doneButton.setFont(new Font("Arial", Font.BOLD, 16));
        
        // Action Listener for done button
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get selected date and time
                Date selectedDate = dateChooser.getDate();
                Date fromTime = (Date) fromTimeSpinner.getValue();
                Date toTime = (Date) toTimeSpinner.getValue();

                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");

                String eventTitle = eventTitleField.getText();
                String eventDescription = eventDescriptionArea.getText();

                if (selectedDate != null && !eventTitle.isEmpty()) {
                    String date = dateFormat.format(selectedDate);
                    String from = timeFormat.format(fromTime);
                    String to = timeFormat.format(toTime);

                    // Write the event details to a file
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("events.txt", true))) {
                        writer.write(eventTitle + "," + from + "," + to + "," + date);
                        writer.newLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(addEventPanel,
                        "Event Created: \nTitle: " + eventTitle +
                        "\nDate: " + date + " from " + from + " to " + to);
                } else {
                    JOptionPane.showMessageDialog(addEventPanel,
                        "Please fill in the required fields.");
                }

                // Go back to DayViewPage when event is created
                CardLayout cl = (CardLayout) mainPanelContainer.getLayout();
                cl.show(mainPanelContainer, "DayViewPage");
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
