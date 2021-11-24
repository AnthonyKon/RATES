import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.sql.Connection;
import java.sql.DriverManager;

import static javax.swing.JOptionPane.showConfirmDialog;


public class EditProfilePanel extends JPanel implements ActionListener {


    JPanel editProfilePanel, welcomePanel;
    JLabel dobLabel, usernameLabel, fNameLabel, welcomeLabel, lNameLabel, passwordLabel, streetLabel,
            suburbLabel, postCodeLabel, stateLabel, countryLabel, emailLabel;
    JTextField usernameStr, fNameStr, lNameStr, passwordStr, dobStr, streetStr, suburbStr, postCodeStr, stateStr, countryStr, emailStr;
    JButton save, cancel;

    //Sql goodies
    PreparedStatement pst = null;
    Connection c = null;

    private JPanel currentPanel;

    EditProfilePanel(JPanel currentPanel) {
        this.currentPanel = currentPanel;

        //titled border
        TitledBorder title = BorderFactory.createTitledBorder("RATES");
        title.setTitleJustification(TitledBorder.CENTER);
        setBorder(title);

        //editable items
        dobLabel = new JLabel();
        dobLabel.setText("Date of Birth:");
        dobStr = new JTextField();

        usernameLabel = new JLabel();
        usernameLabel.setText("Username:");
        usernameStr = new JTextField();

        fNameLabel = new JLabel();
        fNameLabel.setText("First Name:");
        fNameStr = new JTextField();

        lNameLabel = new JLabel();
        lNameLabel.setText("Last Name:");
        lNameStr = new JTextField();

        passwordLabel = new JLabel();
        passwordLabel.setText("Password:");
        passwordStr = new JTextField();

        streetLabel = new JLabel();
        streetLabel.setText("Street:");
        streetStr = new JTextField();

        suburbLabel = new JLabel();
        suburbLabel.setText("Suburb");
        suburbStr = new JTextField();

        postCodeLabel = new JLabel();
        postCodeLabel.setText("Post Code:");
        postCodeStr = new JTextField();

        stateLabel = new JLabel();
        stateLabel.setText("State");
        stateStr = new JTextField();

        countryLabel = new JLabel();
        countryLabel.setText("Country");
        countryStr = new JTextField();

        emailLabel = new JLabel();
        emailLabel.setText("Email:");
        emailStr = new JTextField();


        //Buttons
        save = new JButton("Save");
        cancel = new JButton("Cancel");


        //Welcome Panel
        welcomePanel = new JPanel(new BorderLayout());
        welcomeLabel = new JLabel();
        welcomeLabel.setText("Welcome to RATES!");
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 75)));
        welcomePanel.add(welcomeLabel);

        //Login Panel
        editProfilePanel = new JPanel(new GridLayout(15, 1));
        editProfilePanel.add(Box.createRigidArea(new Dimension(0, 25)));
        editProfilePanel.add(Box.createRigidArea(new Dimension(0, 25)));
        editProfilePanel.add(dobLabel);
        editProfilePanel.add(dobStr);
        editProfilePanel.add(usernameLabel);
        editProfilePanel.add(usernameStr);
        editProfilePanel.add(fNameLabel);
        editProfilePanel.add(fNameStr);
        editProfilePanel.add(lNameLabel);
        editProfilePanel.add(lNameStr);
        editProfilePanel.add(emailLabel);
        editProfilePanel.add(emailStr);
        editProfilePanel.add(passwordLabel);
        editProfilePanel.add(passwordStr);
        editProfilePanel.add(streetLabel);
        editProfilePanel.add(streetStr);
        editProfilePanel.add(suburbLabel);
        editProfilePanel.add(suburbStr);
        editProfilePanel.add(postCodeLabel);
        editProfilePanel.add(postCodeStr);
        editProfilePanel.add(stateLabel);
        editProfilePanel.add(stateStr);
        editProfilePanel.add(countryLabel);
        editProfilePanel.add(countryStr);

        editProfilePanel.add(save);
        editProfilePanel.add(cancel);


        // Adding the listeners to components..
        save.addActionListener(this);
        cancel.addActionListener(this);
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

                System.out.println("Component shown!");
                try {
                    c = DriverManager.getConnection("jdbc:sqlite:RATES-AnthonyKonivets//src//bloba.db");
                    String sql = "SELECT * FROM User WHERE userNo = ?";
                    pst = c.prepareStatement(sql);
                    pst.setString(1, LoginPanel.loggedUser);
                    ResultSet rs = pst.executeQuery();

                    while (rs.next()) {

                        dobStr.setText(rs.getString("dob"));
                        usernameStr.setText(rs.getString("userName"));
                        fNameStr.setText(rs.getString("firstName"));
                        lNameStr.setText(rs.getString("lastName"));
                        emailStr.setText(rs.getString("email"));
                        passwordStr.setText(rs.getString("password"));
                        streetStr.setText(rs.getString("street"));
                        suburbStr.setText(rs.getString("suburb"));
                        postCodeStr.setText(rs.getString("postCode"));
                        stateStr.setText(rs.getString("state"));
                        countryStr.setText(rs.getString("country"));

                    }
                    pst.close();

                } catch (Exception b) {
                    JOptionPane.showMessageDialog(null, b);
                }
            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
        add(editProfilePanel, BorderLayout.CENTER);
        editProfilePanel.setPreferredSize(new Dimension(300, 450));
    }

    @Override
    public void actionPerformed(ActionEvent ae) {

        JButton source = (JButton) ae.getSource();
        if (source.getText().equals("Save")) {
            try {
                c = DriverManager.getConnection("jdbc:sqlite:RATES-AnthonyKonivets//src//bloba.db");
                String sql = "UPDATE User SET dob = ?, userName = ?, firstName = ?, lastName = ?, email = ?, password = ?, street = ?, " +
                        "suburb = ?, postCode = ?, state = ?, country = ? WHERE userNo = ?";
                pst = c.prepareStatement(sql);

                pst.setString(1, dobStr.getText());
                pst.setString(2, usernameStr.getText());
                pst.setString(3, fNameStr.getText());
                pst.setString(4, lNameStr.getText());
                pst.setString(5, emailStr.getText());
                pst.setString(6, passwordStr.getText());
                pst.setString(7, streetStr.getText());
                pst.setString(8, suburbStr.getText());
                pst.setString(9, postCodeStr.getText());
                pst.setString(10, stateStr.getText());
                pst.setString(11, countryStr.getText());
                pst.setString(12, LoginPanel.loggedUser);

                pst.executeUpdate();
                pst.close();

                JOptionPane.showMessageDialog(null, "Profile has been updated!");

            } catch (Exception b) {
                JOptionPane.showMessageDialog(null, b);
            }
        }
        if (source.getText().equals("Cancel")) {
            int dialogResult = showConfirmDialog(this, "Are you sure you want to cancel?\n" +
                    "Any unsaved changes will be lost.");
            if (dialogResult == JOptionPane.YES_OPTION) {
                CardLayoutManager main = new CardLayoutManager();
                main.switchPanel(currentPanel, "Home Page");
            }
        }


    }

}



























    /*JLabel displayLabel;
    JPanel keyPanel;
    JPanel clearPanel;


    public LoginPanel(){
        // Define the layout
        setLayout(new BorderLayout());
        // Define the label to display the numbers
        displayLabel = new JLabel("This is the first page");
        displayLabel.setBackground(Color.WHITE);
        displayLabel.setBorder(BorderFactory.createBevelBorder(1));

        add(displayLabel, BorderLayout.NORTH);


        // Create a panel to contain the keys
        keyPanel = new JPanel();
        keyPanel.setLayout(new GridLayout(4,3));
        keyPanel.setBorder(BorderFactory.createLineBorder(Color.black, 4));

        // Define the individual buttons on the keypad and add them to the keys panel
        JButton button;

        // Add the key panel to the PhonePanel
        add(keyPanel, BorderLayout.CENTER);
        keyPanel.setFocusable(true);
        button = new JButton("1");
        button.setMnemonic('1');
        button.addKeyListener(phoneBtnListener);
        button.addActionListener(new ActListener());
        keyPanel.add(button);

        // Create the clear button and add to the PhonePanel
        clearPanel = new JPanel();
        //clearPanel.setLayout(new GridLayout(1,1));


        //Need to change the layout so that the clear button is dynamic
        add(clearPanel, BorderLayout.SOUTH);
        JButton clearBtn = new JButton("Clear");
        clearBtn.setMnemonic('C');
        clearBtn.setToolTipText("Clear the display");
        clearPanel.add(clearBtn);
        clearBtn.addKeyListener(phoneBtnListener);
        clearBtn.addActionListener(new ActListener());


        // Set the size of the PhonePanel to 250x250
        setPreferredSize(new Dimension(250,250));
    }

    // Define a listener for the keys

    KeyListener phoneBtnListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Key: " + e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) { }

    };
    private class ActListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton source = (JButton) e.getSource();
            if (source.getText().equals("Clear")) {
                //displayLabel.setText(" ");
                HomePanel homePage = new HomePanel();
                homePage.setVisible(true);

            } else {
                displayLabel.setText(displayLabel.getText() + source.getText());
            }
        }
    }*/



