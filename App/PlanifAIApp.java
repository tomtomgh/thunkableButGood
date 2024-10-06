import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class PlanifAIApp {

    private static Timer timer; // Declare timer as a static member
    private static CardLayout cardLayout;
    private static JPanel mainPanelContainer;

    private static Instant startTime;  // Declare start time
    private static Instant endTime;    // Declare end time

    public static void main(String[] args) {
        // Record the start time when the app opens
        startTime = Instant.now();

        JFrame frame = new JFrame("PlanifAI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 700);  
        frame.setResizable(false);

        mainPanelContainer = new JPanel(cardLayout = new CardLayout());

        MainPage mainPage = new MainPage(mainPanelContainer);
        DayViewPage dayViewPage = new DayViewPage(mainPanelContainer, null);
        AddEventPage addEventPage = new AddEventPage(mainPanelContainer, null);
        ReminderPage reminderPage = new ReminderPage(mainPanelContainer);

        mainPanelContainer.add(mainPage.getMainPanel(), "MainPage");
        mainPanelContainer.add(dayViewPage.getDayViewPanel(), "DayViewPage");
        mainPanelContainer.add(addEventPage.getAddEventPanel(), "AddEventPage");
        mainPanelContainer.add(reminderPage.getReminderPanel(), "ReminderPage");

        cardLayout.show(mainPanelContainer, "MainPage");

        frame.add(mainPanelContainer);
        frame.setVisible(true);

        // Start the reminder checker
        startReminderChecker();

        // Add a WindowListener to capture when the application closes
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                // Record the end time when the app closes
                endTime = Instant.now();
                
                // Calculate the elapsed time
                Duration elapsedTime = Duration.between(startTime, endTime);
                long elapsedSeconds = elapsedTime.getSeconds();
                long minutes = elapsedSeconds / 60;
                long seconds = elapsedSeconds % 60;

                // Format the elapsed time as a string
                String elapsedTimeStr = "Elapsed time: " + minutes + " minutes and " + seconds + " seconds.";

                // Display or log the elapsed time
                System.out.println(elapsedTimeStr);
                
                // Save the elapsed time to timer.txt
                saveElapsedTimeToFile(elapsedTimeStr);

                // Exit the application
                System.exit(0);
            }
        });
    }

    private static void saveElapsedTimeToFile(String elapsedTime) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("timer.txt", true))) {
            // Append the elapsed time to timer.txt
            writer.write(elapsedTime);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startReminderChecker() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkForReminders();
            }
        }, 0, 60000); // Check every minute
    }

    private static void checkForReminders() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        Date currentTime = new Date();
        CardLayout cl = cardLayout; // Assuming cardLayout is accessible here

        try (BufferedReader reader = new BufferedReader(new FileReader("events.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] eventDetails = line.split(",");
                if (eventDetails.length < 6) continue;

                String reminderTimeStr = eventDetails[4];
                String eventTitle = eventDetails[0];
                String eventTime = eventDetails[1] + " - " + eventDetails[2];

                Date reminderTime = timeFormat.parse(reminderTimeStr);

                if (currentTime.after(reminderTime) || currentTime.getTime() - reminderTime.getTime() < 60000) {
                    updateReminderPage(eventTitle, eventTime);
                    cl.show(mainPanelContainer, "ReminderPage");

                    // Optionally, here you could update the file or data structure to mark this event as reminded
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static void updateReminderPage(final String title, final String time) {
        // Ensure this runs on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            ReminderPage.updateReminderLabels(title, time);
        });
    }
}
