import java.awt.*;
import javax.swing.*;

public class PlanifAIApp {

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("PlanifAI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 700);  // Size to simulate a mobile app
        frame.setResizable(false);

        // Create a CardLayout to switch between the main, modification, and add event screens
        JPanel mainPanel = new JPanel(new CardLayout());

        // Create the main page, day view, and add event panels
        // Create the main page, day view, and add event panels
        MainPage planifAIPage = new MainPage(mainPanel);
        DayViewPage dayViewPage = new DayViewPage(mainPanel);
        AddEventPage addEventPage = new AddEventPage(mainPanel);
        ReminderPage reminderPage = new ReminderPage(mainPanel);

        // Add all screens to the main panel
        mainPanel.add(planifAIPage.getMainPanel(), "MainPage");
        mainPanel.add(dayViewPage.getDayViewPanel(), "DayViewPage");
        mainPanel.add(addEventPage.getAddEventPanel(), "AddEventPage");
        mainPanel.add(reminderPage.getReminderPanel(), "ReminderPage");

        // Display the MainPage initially
        CardLayout cl = (CardLayout) mainPanel.getLayout();
        cl.show(mainPanel, "MainPage");

        // Show the frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
