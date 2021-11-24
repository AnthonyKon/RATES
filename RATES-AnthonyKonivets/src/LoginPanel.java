import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.sql.Connection;
import java.sql.DriverManager;


public class LoginPanel extends JPanel implements ActionListener {


    public static String loggedUser;
    public static String loggedUserName;

    JPanel loginPanel, welcomePanel;
    JLabel userLabel, pwdLabel, message, welcomeLabel;
    JTextField usernameStr;
    JPasswordField pwdStr;
    JButton submit;

    //Sql goodies
    PreparedStatement pst = null;
    Connection c = null;

    private JPanel currentPanel;

    LoginPanel(JPanel currentPanel) {
        this.currentPanel = currentPanel;


        //titled border
        TitledBorder title = BorderFactory.createTitledBorder("RATES");
        title.setTitleJustification(TitledBorder.CENTER);
        setBorder(title);

        //user + password
        userLabel = new JLabel();
        userLabel.setText("Username:");
        usernameStr = new JTextField("koni0028");
        pwdLabel = new JLabel();
        pwdLabel.setText("Password:");
        pwdStr = new JPasswordField("password");

        //Submit
        submit = new JButton("Login");

        //Welcome Panel
        welcomePanel = new JPanel(new BorderLayout());
        welcomeLabel = new JLabel();
        welcomeLabel.setText("Welcome to RATES!");
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 75)));
        welcomePanel.add(welcomeLabel);

        //Login Panel
        loginPanel = new JPanel(new GridLayout(4, 1));
        loginPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        loginPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        loginPanel.add(userLabel);
        loginPanel.add(usernameStr);
        loginPanel.add(pwdLabel);
        loginPanel.add(pwdStr);

        message = new JLabel();
        loginPanel.add(message);
        loginPanel.add(submit);

        // Adding the listeners to components..
        submit.addActionListener(this::actionPerformed);
        add(loginPanel, BorderLayout.CENTER);

        loginPanel.setPreferredSize(new Dimension(300, 150));

    }


    @Override
    public void actionPerformed(ActionEvent ae) {


        try {

            c = DriverManager.getConnection("jdbc:sqlite:RATES-AnthonyKonivets//src//bloba.db");
            String sql = "select * from User where userName = ? and password = ?";
            pst = c.prepareStatement(sql);

            pst.setString(1, usernameStr.getText());
            pst.setString(2, pwdStr.getText());


            ResultSet rs = pst.executeQuery();
            loggedUser = rs.getString("userNo");
            loggedUserName = rs.getString("firstName") + " " + rs.getString("lastName");

            int count = 0;
            while (rs.next()) {
                count = count + 1;
                if (count == 1) {
                    JOptionPane.showMessageDialog(null, "Login Successful");

                    CardLayoutManager main = new CardLayoutManager();
                    main.switchPanel(currentPanel, "Home Page");

                    usernameStr.setText("");
                    pwdStr.setText("");
                }
            }
            rs.close();
            pst.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Incorrect Username or Password");
        }

    }

}



