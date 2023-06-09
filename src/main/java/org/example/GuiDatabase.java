package org.example;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Objects;


public class GuiDatabase {

    private JFrame mainFrame;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private InitializeDatabase database;
    private Swosh swosh;

    private boolean loggedIn = false;

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    public GuiDatabase()
    {
        prepareGUI();
    }
    public static void main(String[] args)
    {
        GuiDatabase guiDatabase = new GuiDatabase();
        guiDatabase.createDb();
        guiDatabase.createQuery();
    }
    private void setDatabase(InitializeDatabase database)
    {
        this.database = database;
    }
    private InitializeDatabase getDatabase()
    {
        return database;
    }
    private void prepareGUI()
    {

        mainFrame = new JFrame("Database visualizer");
        mainFrame.setSize(400,400);

        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(),BoxLayout.Y_AXIS));

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                System.exit(0);
            }
        });

        JLabel headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("",JLabel.CENTER);

        statusLabel.setSize(350,100);

        controlPanel = new JPanel(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(statusLabel);
        mainFrame.add(controlPanel);
        mainFrame.setVisible(true);

    }

    private void createDb()
    {
        JButton loadDbBtn = new JButton("Load Db");
        loadDbBtn.setActionCommand("ok");
        swosh = new Swosh();
        loadDbBtn.addActionListener(actionEvent -> {
            try
            {
                if(Objects.equals(actionEvent.getActionCommand(), "ok"))
                {
                    InitializeDatabase db = new InitializeDatabase("root","","localhost", 3306,"ViktorTesting",false);
                    setDatabase(db);
                    CreateQuery query = new CreateQuery();
                    query.createQuery("CREATE TABLE IF NOT EXISTS user(userid INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), email VARCHAR(50), password VARCHAR(255), personalnumber INT, " +
                            "phonenumber VARCHAR(20), address VARCHAR(100));", db, "create", "");

                    query.createQuery("CREATE TABLE IF NOT EXISTS accounts(userid INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(50), " +
                            "balance DECIMAL(65,2));", db, "create","");

                    query.createQuery("CREATE TABLE IF NOT EXISTS transactions(userid INT PRIMARY KEY AUTO_INCREMENT, date TINYTEXT, accnamefrom VARCHAR(50), " +
                            "amount DECIMAL(8,2));", db, "create","");


                    statusLabel.setText("Your DB successfully loaded");
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        controlPanel.add(loadDbBtn);
        mainFrame.setVisible(true);
    }

    private void authHandler() {

        while (loggedIn) {
            JButton logInBtn = new JButton("Log in");
            JTextArea logInText = new JTextArea("Log in");
            JTextField personNrText = new JTextField("Personal number",20);
            JPasswordField pwField = new JPasswordField("Password");

            controlPanel.add(logInText);
            controlPanel.add(personNrText);
            controlPanel.add(pwField);
            controlPanel.add(logInBtn);
            logInBtn.setActionCommand("ok");

            logInBtn.addActionListener(actionEvent -> {
                String str = logInBtn.getActionCommand();
                if(str.equals("ok")) {
                    try {
                        CreateQuery query = new CreateQuery();
                        String temp = query.createQuery("SELECT * FROM sys.database_principals WHERE personalnumber = '"+personNrText.getText()+ "' AND password = '"+ Arrays.toString(pwField.getPassword()) +"';", getDatabase(),"select", "user");
                        if(temp != null) {
                            setLoggedIn(true);
                        } else {
                            logInText.setText("Access denied!");
                            throw new NullPointerException();
                        }

                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        while (!loggedIn)
        {
            JButton logoutBtn = new JButton("Log out");
            JLabel label = new JLabel();
            controlPanel.add(logoutBtn);
            controlPanel.add(label);
            logoutBtn.setActionCommand("Logout");

            logoutBtn.addActionListener(actionEvent -> {
                String str = logoutBtn.getActionCommand();
                if(str.equals("Logout")) {
                    setLoggedIn(false);
                    label.setText("You have logged out!");
                }
            });
        }
        mainFrame.setVisible(true);
    }


    private void createQuery () {
        while (!loggedIn) {
            JTextField jTextField = new JTextField("Enter your query", 30);
            JButton createBtn = new JButton("Submit");
            JLabel label = new JLabel();
            JTextPane textPane = new JTextPane();



            controlPanel.add(jTextField);
            controlPanel.add(createBtn);
            controlPanel.add(label);
            controlPanel.add(textPane);


            createBtn.setActionCommand("Submit");


            createBtn.addActionListener(actionEvent -> {
                String str = actionEvent.getActionCommand();
                //CREATE QUERY
                if (str.equals("Submit") && jTextField.getText().contains("CREATE")) {
                    try {
                        CreateQuery createQuery = new CreateQuery();
                        createQuery.createQuery(jTextField.getText(), getDatabase(), "create", "");
                        label.setText(jTextField.getText() + ". The table got created with your query!");
                        jTextField.setText("Enter your query");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //INSERT QUERY
                } else if (str.equals("Submit") && jTextField.getText().contains("INSERT")) {
                    try {
                        CreateQuery createQuery = new CreateQuery();
                        createQuery.createQuery(jTextField.getText(), getDatabase(), "insert", "");
                        label.setText(jTextField.getText() + ". The query got inserted in the table!");
                        jTextField.setText("Enter your query");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //SELECT QUERY
                } else if (str.equals("Submit") && jTextField.getText().contains("SELECT")) {
                    try {
                        CreateQuery createQuery = new CreateQuery();
                        String resultStr = createQuery.createQuery(jTextField.getText(), getDatabase(), "select", "user");
                        label.setText(resultStr);
                        jTextField.setText("Enter your query");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }  else if (str.equals("Submit") && jTextField.getText().contains("SELECT")) {
                    try {
                        CreateQuery createQuery = new CreateQuery();
                        String resultStr = createQuery.createQuery(jTextField.getText(), getDatabase(), "select", "account");
                        textPane.setText(resultStr);
                        jTextField.setText("Enter your query");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }   else if (str.equals("Submit") && jTextField.getText().contains("SELECT")) {
                    try {
                        CreateQuery createQuery = new CreateQuery();
                        String resultStr = createQuery.createQuery(jTextField.getText(), getDatabase(), "select", "transactions");
                        textPane.setText(resultStr);
                        jTextField.setText("Enter your query");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


            mainFrame.setVisible(true);
        }
    }
}
