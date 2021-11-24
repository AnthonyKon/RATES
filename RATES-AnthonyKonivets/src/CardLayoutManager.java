import java.awt.*;
import javax.swing.*;

public class CardLayoutManager extends JFrame {

    private JMenuBar menuBar;
    private JMenu file;
    private JMenuItem exit;
    private JMenuItem back;
    private JPanel currentPanel;

    public CardLayoutManager() {
        setTitle("Current Panel");
        setResizable(false);
        setSize(new Dimension(350, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setJMenuBar(createMainMenu());
        setLocationRelativeTo(null);

        currentPanel = new JPanel();
        currentPanel.setLayout(new CardLayout());
        currentPanel.add(new LoginPanel(currentPanel), "Login Page");
        currentPanel.add(new HomePanel(currentPanel), "Home Page");
        currentPanel.add(new NewReviewPanel(currentPanel), "New Review Page");
        currentPanel.add(new ContinueReviewPanel(currentPanel), "Continue Review Page");
        currentPanel.add(new PreviousReview(currentPanel), "View Previous Reviews Page");
        currentPanel.add(new EditProfilePanel(currentPanel), "Edit Profile Page");

        setContentPane(currentPanel);
    }

    public JMenuBar createMainMenu() {
        menuBar = new JMenuBar();
        file = new JMenu("File");
        exit = new JMenuItem("Exit");
        back = new JMenuItem("Back");
        exit.addActionListener(e -> System.exit(0));

        back.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) currentPanel.getLayout();
            cardLayout.previous(currentPanel);
        });


        file.add(exit);
        file.add(back);
        menuBar.add(file);

        return menuBar;
    }

    public void switchPanel(Container container, String panelName) {
        CardLayout card = (CardLayout) (container.getLayout());
        card.show(container, panelName);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new CardLayoutManager().setVisible(true));
    }
}