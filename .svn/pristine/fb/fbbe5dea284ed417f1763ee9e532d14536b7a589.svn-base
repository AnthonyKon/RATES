import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.sql.*;

public class PreviousReview extends JPanel implements ActionListener {

    private JPanel currentPanel, motherPanel;
    private JButton homeButton;
    private JLabel prvRevLbl;
    private JTextArea txtOutputTA;

    //Sql goodies
    Statement pst = null;
    Connection con = null;


    public PreviousReview(JPanel currentPanel) {
        this.currentPanel = currentPanel;

        //titled border
        TitledBorder title = BorderFactory.createTitledBorder("RATES");
        title.setTitleJustification(TitledBorder.CENTER);
        setBorder(title);

        //labels
        prvRevLbl = new JLabel("Previous Reviews: ");
        //Grid bag layout
        motherPanel = new JPanel();
        motherPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        add(motherPanel);

        //TOPIC DROPDOWN MENU
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0; //row 0
        c.gridy = 0; //column 0
        c.gridwidth = 3;
        c.insets = new Insets(5, 5, 5, 5);
        motherPanel.add(prvRevLbl, c);

        //TYPE HERE SECTION
        txtOutputTA = new JTextArea(27, 28);
        JScrollPane scrollPane = new JScrollPane(txtOutputTA);
        txtOutputTA.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        txtOutputTA.setLineWrap(true);
        txtOutputTA.setWrapStyleWord(true);
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 3;
        motherPanel.add(scrollPane, c);

        //Bottom buttons
        homeButton = new JButton("Home");
        homeButton.addActionListener(this);
        c.anchor = GridBagConstraints.FIRST_LINE_END;

        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 5;
        motherPanel.add(homeButton, c);

        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
                try {
                    con = DriverManager.getConnection("jdbc:sqlite:RATES-AnthonyKonivets//src//bloba.db");
                    String sql = "SELECT topic, activity, anonCheck, writtenReview, attachedFile, rating FROM Submission where userNo = " + LoginPanel.loggedUser;
                    pst = con.createStatement();
                    ResultSet rs = pst.executeQuery(sql);
                    System.out.println("after prep statement");

                    while (rs.next()) {

                        txtOutputTA.append("Topic: " + rs.getString("topic") + "\n");
                        txtOutputTA.append("Activity: " + rs.getString("activity") + "\n");
                        if (rs.getBoolean("anonCheck") == true) {
                            txtOutputTA.append("Reviewed by: ANONYMOUS\n");
                        } else {
                            txtOutputTA.append("Reviewed by: " + LoginPanel.loggedUserName + "\n");
                        }
                        txtOutputTA.append("Review: " + rs.getString("writtenReview") + "\n");
                        txtOutputTA.append("Rating: " + rs.getString("rating") + "\n");
                        txtOutputTA.append("Attached File:\n");
                        txtOutputTA.append(rs.getString("attachedFile"));

                        txtOutputTA.append("-------------------------------------------------------------------------\n");
                        txtOutputTA.setEditable(false);
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        if (source.getText().equals("Home")) {
            txtOutputTA.setText("");
            CardLayoutManager main = new CardLayoutManager();
            main.switchPanel(currentPanel, "Home Page");
        }
    }
}


