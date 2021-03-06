import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showConfirmDialog;


public class HomePanel extends JPanel {

    JButton newReviewBtn, contRevBtn, previousRevBtn, editProfileBtn, logoutBtn;
    private JPanel currentPanel;

    public HomePanel(JPanel currentPanel) {
        //navigation for card layout
        this.currentPanel = currentPanel;

        //layout + titled border
        setLayout(new BorderLayout());
        TitledBorder title = BorderFactory.createTitledBorder("RATES");
        title.setTitleJustification(TitledBorder.CENTER);
        setBorder(title);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        newReviewBtn = new JButton("New Review");
        contRevBtn = new JButton("Continue Review");
        previousRevBtn = new JButton("Submitted Reviews");
        editProfileBtn = new JButton("Edit Profile");
        logoutBtn = new JButton("Logout");

        newReviewBtn.setMargin(new Insets(5, 34, 5, 34));
        contRevBtn.setMargin(new Insets(5, 21, 5, 22));
        previousRevBtn.setMargin(new Insets(5, 14, 5, 14));

        editProfileBtn.setMargin(new Insets(5, 10, 5, 10));
        logoutBtn.setMargin(new Insets(5, 20, 5, 20));

        newReviewBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        contRevBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        previousRevBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        editProfileBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        buttonPanel.add(newReviewBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        buttonPanel.add(contRevBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        buttonPanel.add(previousRevBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 80)));
        buttonPanel.add(editProfileBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        buttonPanel.add(logoutBtn);

        add(buttonPanel, BorderLayout.CENTER);

        newReviewBtn.addActionListener(new ActListener());
        logoutBtn.addActionListener(new ActListener());
        contRevBtn.addActionListener(new ActListener());
        previousRevBtn.addActionListener(new ActListener());
        editProfileBtn.addActionListener(new ActListener());

        setPreferredSize(new Dimension(250, 250));
    }

    private class ActListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            CardLayoutManager main = new CardLayoutManager();

            if (source.getText().equals("New Review")) {
                main.switchPanel(currentPanel, "New Review Page");
            } else if (source.getText().equals("Logout")) {
                int dialogResult = showConfirmDialog(null, "Are you sure you want to logout?");
                if (dialogResult == JOptionPane.YES_OPTION) {
                    main.switchPanel(currentPanel, "Login Page");
                }
            } else if (source.getText().equals("Continue Review")) {
                main.switchPanel(currentPanel, "Continue Review Page");
            } else if (source.getText().equals("Submitted Reviews")) {
                main.switchPanel(currentPanel, "View Previous Reviews Page");
            } else if (source.getText().equals("Edit Profile")) {
                main.switchPanel(currentPanel, "Edit Profile Page");

            }
        }
    }


}

