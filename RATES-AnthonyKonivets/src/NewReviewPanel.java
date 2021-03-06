import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.sql.*;

import static javax.swing.JOptionPane.showConfirmDialog;


public class NewReviewPanel extends JPanel implements ActionListener {

    private JPanel currentPanel, motherPanel;
    private JComboBox topic, activity, rating;
    private JButton submit, save, addFile, cancel, theFile;
    private JTextArea typeHere;
    private JCheckBox anonBox;
    private String anonValue;
    private Boolean anonBool = false;

    private String[] topics = {"Select Topic", "Cybersecurity", "Enterprise Information Security", "Interactive Computer Systems",
            "Computer Programming 2", "Enterprise Cloud Systems"};
    private String[] activities = {"Select Activity", "Lecture", "Tutorial", "Course Material", "Instructor", "Group Member",
            "Assignment", "Other"};
    private String[] ratings = {"1 Star", "2 Stars", "3 Stars", "4 Stars", "5 Stars"};
    JTextArea ta = new JTextArea(20, 3);
    private JFileChooser fc;
    private FileNameExtensionFilter filter;

    //Sql goodies
    PreparedStatement pst = null;
    Connection c = null;


    public NewReviewPanel(JPanel currentPanel) {
        this.currentPanel = currentPanel;

        //File Chooser
        fc = new JFileChooser();
        filter = new FileNameExtensionFilter("Text Files", "text", "txt");
        fc.setFileFilter(filter);

        //titled border
        TitledBorder title = BorderFactory.createTitledBorder("RATES");
        title.setTitleJustification(TitledBorder.CENTER);
        setBorder(title);

        //combo box
        topic = new JComboBox(topics);
        activity = new JComboBox(activities);
        rating = new JComboBox(ratings);

        topic.setSelectedIndex(0);
        activity.setSelectedIndex(0);
        rating.setSelectedIndex(0);

        //Grid bag layout
        motherPanel = new JPanel();
        motherPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        add(motherPanel);

        //TOPIC DROPDOWN MENU
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.insets = new Insets(5, 5, 5, 5);
        motherPanel.add(topic, c);

        //ACTIVITY DROPDOWN MENU
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        motherPanel.add(activity, c);

        //ANON SECTION
        anonBox = new JCheckBox("Rate Anonymously: ");
        anonBox.setHorizontalTextPosition(JCheckBox.LEADING);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        motherPanel.add(anonBox, c);

        //TYPE HERE SECTION
        typeHere = new JTextArea(20, 3);
        JScrollPane scrollPane = new JScrollPane(typeHere);
        typeHere.setBorder(BorderFactory.createLineBorder(Color.black, 1));
        typeHere.setLineWrap(true);
        typeHere.setWrapStyleWord(true);
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 3;
        motherPanel.add(scrollPane, c);

        //rating
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 4;
        motherPanel.add(rating, c);

        //the attached file view button
        theFile = new JButton("View File");
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 4;
        motherPanel.add(theFile, c);

        //Bottom buttons
        submit = new JButton("Submit");
        submit.addActionListener(this);
        save = new JButton("Save");
        save.addActionListener(this);
        cancel = new JButton("Cancel");
        cancel.addActionListener(this);
        addFile = new JButton("Add File");
        addFile.addActionListener(this);
        theFile.addActionListener(this);
        anonBox.addActionListener(a -> {
            JCheckBox source = (JCheckBox) a.getSource();
            if (source.getText().equals("Rate Anonymously: ")) {
                if (anonBox.isSelected()) {
                    anonValue = "Anonymous";
                    anonBool = true;
                } else {
                    anonBool = false;
                    anonValue = LoginPanel.loggedUserName;
                }
            }
        });
        c.anchor = GridBagConstraints.FIRST_LINE_END;

        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 4;
        motherPanel.add(addFile, c);


        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 5;
        motherPanel.add(submit, c);

        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 5;
        motherPanel.add(save, c);

        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 5;
        motherPanel.add(cancel, c);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton source = (JButton) e.getSource();

        if (source.getText().equals("Submit")) {
            try {
                c = DriverManager.getConnection("jdbc:sqlite:RATES-AnthonyKonivets//src//bloba.db");
                String sql = "INSERT INTO Submission (userNo, topic, activity, anonCheck, writtenReview, rating, attachedFile) VALUES (1,?,?,?,?,?,?) ";
                pst = c.prepareStatement(sql);

                pst.setString(1, topic.getSelectedItem().toString());
                pst.setString(2, activity.getSelectedItem().toString());
                pst.setBoolean(3, anonBox.isSelected());
                pst.setString(4, typeHere.getText());
                pst.setString(5, rating.getSelectedItem().toString());
                pst.setString(6, ta.getText());

                pst.executeUpdate();
                pst.close();

                JOptionPane.showMessageDialog(null, "Review has been submitted!");

                topic.setSelectedIndex(0);
                activity.setSelectedIndex(0);
                anonBox.setSelected(false);
                typeHere.setText("");
                rating.setSelectedIndex(0);
                ta.setText("");

            } catch (Exception b) {
                System.out.println(b);
            }
        }
        if (source.getText().equals("Save")) {

            try {
                c = DriverManager.getConnection("jdbc:sqlite:RATES-AnthonyKonivets//src//bloba.db");
                String sql = "UPDATE Save SET topic = ?, activity = ?, anonCheck = ?, writtenReview = ?, rating = ?, attachedFile = ? WHERE userNo = ?";
                pst = c.prepareStatement(sql);
                System.out.println("after prep statement");

                pst.setString(1, topic.getSelectedItem().toString());
                pst.setString(2, activity.getSelectedItem().toString());
                pst.setBoolean(3, anonBool);
                pst.setString(4, typeHere.getText());
                pst.setString(5, rating.getSelectedItem().toString());
                pst.setString(6, ta.getText());
                pst.setString(7, LoginPanel.loggedUser);

                pst.executeUpdate();
                pst.close();
                JOptionPane.showMessageDialog(null, "Review has been saved!");

            } catch (Exception b) {
                JOptionPane.showMessageDialog(null, b);
            }
        } else if (source.getText().equals("Add File")) {
            int returnVal = fc.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();

                try {
                    FileReader fr = new FileReader(file.getAbsoluteFile());
                    ta.read(fr, null);
                } catch (Exception exception) {
                    System.out.println(exception);
                }
            }

        } else if (source.getText().equals("View File")) {
            JScrollPane scrollForFileView = new JScrollPane(ta);
            scrollForFileView.setPreferredSize(new Dimension(300, 300));
            ta.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            ta.setLineWrap(true);
            ta.setWrapStyleWord(true);
            JOptionPane.showMessageDialog(null, scrollForFileView, "Attached File", JOptionPane.PLAIN_MESSAGE, null);
        } else if (source.getText().equals("Cancel")) {
            int dialogResult = showConfirmDialog(this, "Are you sure you want to cancel?\n" +
                    "Unsaved changes will be lost.");
            if (dialogResult == JOptionPane.YES_OPTION) {
                topic.setSelectedIndex(0);
                activity.setSelectedIndex(0);
                anonBox.setSelected(false);
                typeHere.setText("");
                rating.setSelectedIndex(0);
                ta.setText("");
                CardLayoutManager main = new CardLayoutManager();
                main.switchPanel(currentPanel, "Home Page");
            }
        }
    }

}
