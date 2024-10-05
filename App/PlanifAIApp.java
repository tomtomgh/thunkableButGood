import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlanifAIApp {

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("PlanifAI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 700);  // Size to simulate a mobile app
        frame.setResizable(false);

        // Create a CardLayout to switch between the main and modification screens
        JPanel mainPanel = new JPanel(new CardLayout());

        // Create the main page and modification screen panels
        MainPage planifAIPage = new MainPage(mainPanel);
        DayViewPage dayViewPage = new DayViewPage(mainPanel);

        // Add both screens to the main panel
        mainPanel.add(planifAIPage.getMainPanel(), "MainPage");
        mainPanel.add(dayViewPage.getDayViewPanel(), "DayViewPage");

        // Show the frame
        frame.add(mainPanel);
        frame.setVisible(true);
    }
}
