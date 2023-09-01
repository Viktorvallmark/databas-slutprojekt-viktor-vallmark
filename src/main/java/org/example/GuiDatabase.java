package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.*;

import javax.swing.SwingUtilities;


public class GuiDatabase implements Runnable{

    private JFrame mainFrame;
    private JFrame loginFrame;
    private InitializeDatabase database;
    private Swosh swosh;
    private User user1;
    private CreateQuery query;

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public GuiDatabase()
    {
        super();
    }
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new GuiDatabase());
    }
    private void setDatabase(InitializeDatabase database)
    {
        this.database = database;
    }
    private InitializeDatabase getDatabase()
    {
        return database;
    }

    private void createDb() {

        try{
        InitializeDatabase db = new InitializeDatabase("root", "", "localhost", 3306, "ViktorTesting", false);
        setDatabase(db);
        query = new CreateQuery();
        query.createQuery("CREATE TABLE IF NOT EXISTS users(userid INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), email VARCHAR(50), password VARCHAR(255), personalnumber INT, " +
                "phonenumber VARCHAR(20), address VARCHAR(100));", db, "create", null);

        query.createQuery("CREATE TABLE IF NOT EXISTS accounts(userid INT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(50), " +
                "balance DECIMAL(65,2));", db, "create", null);

        query.createQuery("CREATE TABLE IF NOT EXISTS transactions(userid INT PRIMARY KEY AUTO_INCREMENT, date TINYTEXT, accnamefrom VARCHAR(50), accnameto VARCHAR(50), " +
                "amount DECIMAL(8,2));", db, "create", null);
            System.out.println("tables created successfully");
    }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        createDb();
        loginFrame = new JFrame("Swosh Login");
        loginFrame. setSize(500, 500);
        loginFrame.setLayout(new BoxLayout(loginFrame.getContentPane(), BoxLayout.Y_AXIS));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        loginFrame.add(controlPanel);
        swosh = new Swosh();

        JLabel updateYourInfoLabel = new JLabel("If you update your contact information, you can use the full functionality of your Swosh. Without full information, you can't " +
                "send or receive money from others");

        JButton logInBtn = new JButton("Log in");
        JButton registerBtn = new JButton("Register");

        JTextField ssnField = new JTextField();
        ssnField.setPreferredSize(new Dimension(100, 30));

        JPasswordField pwField = new JPasswordField();
        pwField.setPreferredSize(new Dimension(100, 30));

        JLabel ssnLabel = new JLabel("Personal number: ");
        JLabel pwLabel = new JLabel("Password: ");
        logInBtn.setActionCommand("Login");
        registerBtn.setActionCommand("Register");

        /*########################################################
        * Handles logging in
        * ########################################################
        * */

        logInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if(actionEvent.getActionCommand().equals("Login")) {
                    try {
                        if(swosh.getUserList().isEmpty()){
                            JOptionPane.showMessageDialog(loginFrame, "No such user found!");
                        }else {
                        for(User user : swosh.getUserList()) {
                            //TODO: Add a check if the user is in the database
                            if(user.getPersonalNr() == Long.parseLong(ssnField.getText()) && Arrays.equals(user.getPassword().toCharArray(), pwField.getPassword())){
                                user1 = user;

                                ssnField.setText("");
                                pwField.setText("");
                                if(user1.getPhoneNr()!=0 && !user1.getName().isBlank() && !user1.getAddress().isBlank() && !user1.getEmail().isBlank()){
                                    updateYourInfoLabel.setText("");
                                    updateYourInfoLabel.setPreferredSize(new Dimension(0,0));
                                }
                                if(user1.getEmail().isBlank()|| user1.getAddress().isBlank() || user1.getName().isBlank() || user1.getPhoneNr()==0){
                                    mainFrame.add(updateYourInfoLabel);
                                }
                                mainFrame.setVisible(true);
                                loginFrame.setVisible(false);
                            }else {
                                JOptionPane.showMessageDialog(loginFrame, "No such user found!");
                            }
                        }}

                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        /*###########################################
        * Registers the user
        *
        * ###########################################
        * */
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                if(actionEvent.getActionCommand().equals("Register")){

                        long ssn = Long.parseLong(ssnField.getText());
                        char[] pw = pwField.getPassword();
                        User user = new User(String.valueOf(pw), ssn, swosh.getUserID());
                        swosh.getUserList().add(user);
                        JOptionPane.showMessageDialog(loginFrame, "User was registered!");
                        query.createQuery("INSERT INTO users(personalnumber, password) VALUES("+user.getPersonalNr()+", "+String.valueOf(pw)+")", getDatabase(),"insert","users");
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        controlPanel.add(ssnLabel); controlPanel.add(ssnField); controlPanel.add(pwLabel); controlPanel.add(pwField); controlPanel.add(logInBtn);
        controlPanel.add(registerBtn);


        mainFrame = new JFrame("Welcome to your Swosh");
        mainFrame.setSize(400,400);

        mainFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        //TODO: Drop down menu to choose action. Add textfields to update information about User.
        JLabel optionsLabel = new JLabel("Choose an action in the drop down menu: ");
        String[] choices = {"Add account", "Delete account", "Add money", "Send money", "Print account statement","Update your information" ,"Delete your Swosh"};
        JComboBox<String> comboBox = new JComboBox<>(choices);
        JButton okBtn = new JButton("Ok");
        okBtn.setActionCommand("ok");
        ////////////////////////////////////////

        //TODO:Update user information

        //namn
        JTextField nameField = new JTextField();
        JLabel nameLabel = new JLabel("Your name: ");
        nameLabel.setLabelFor(nameField);
        nameField.setPreferredSize(new Dimension(50, 25));
        //email
        JTextField emailField = new JTextField();
        JLabel emailLabel = new JLabel("Your email: ");
        nameLabel.setLabelFor(emailField);
        emailField.setPreferredSize(new Dimension(50, 25));
        //telefonnr
        JTextField phoneField = new JTextField();
        JLabel phoneLabel = new JLabel("Your telephone number: ");
        nameLabel.setLabelFor(phoneField);
        phoneField.setPreferredSize(new Dimension(50, 25));
        //address
        JTextField addressField = new JTextField();
        JLabel addressLabel = new JLabel("Your address: ");
        nameLabel.setLabelFor(addressField);
        addressField.setPreferredSize(new Dimension(50, 25));

        /////////////////////////////////////////

        //TODO: After choosing from drop down menu
        JFrame choiceFrame = new JFrame();
        JComboBox<Integer> accChoice = new JComboBox<>();
        JLabel actionLabel = new JLabel();
        NumberFormat amountFormat = NumberFormat.getNumberInstance();
        JFormattedTextField addField = new JFormattedTextField(amountFormat);
        JButton choiceButton = new JButton();
        /////////////////////////////////////


        //TODO: Account statement screen

        JTextArea accountData = new JTextArea();
        ////////////////////////////////////////////////

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                int comboBoxChoice = comboBox.getSelectedIndex();
                try{
                    if(actionEvent.getActionCommand().equals("ok") && comboBox.getItemAt(comboBoxChoice).equalsIgnoreCase("add account")){

                        choiceFrame.revalidate();
                        choiceFrame.repaint();
                        Component[] components = choiceFrame.getComponents();
                        choiceFrame.setBounds(500, 0, 500, 400);
                        for(Component c : components) {
                            if (c instanceof JScrollPane) {
                                choiceFrame.remove(c);
                            } else if (c instanceof JComboBox<?>) {
                                choiceFrame.remove(c);
                            }
                        }
                        accountData.setVisible(false);
                        nameLabel.setPreferredSize(new Dimension(0,0));
                        nameField.setPreferredSize(new Dimension(0,0));
                        addressLabel.setPreferredSize(new Dimension(0,0));
                        addressField.setPreferredSize(new Dimension(0,0));
                        emailLabel.setPreferredSize(new Dimension(0,0));
                        emailField.setPreferredSize(new Dimension(0,0));
                        phoneLabel.setPreferredSize(new Dimension(0,0));
                        phoneField.setPreferredSize(new Dimension(0,0));
                        accChoice.setPreferredSize(new Dimension(0,0));
                        choiceFrame.revalidate();
                        choiceFrame.repaint();


                        choiceFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
                        choiceFrame.setSize(400, 400);
                        choiceFrame.setTitle("Account creation");
                        choiceFrame.add(actionLabel);
                        choiceFrame.add(addField);
                        addField.setPreferredSize(new Dimension(60, 35));
                        choiceButton.setPreferredSize(new Dimension(120,30));
                        actionLabel.setPreferredSize(new Dimension(180,30));
                        choiceButton.setText("Create account");
                        choiceButton.setActionCommand("create");
                        choiceFrame.add(choiceButton);
                        actionLabel.setText("Create an account with ID: ");
                        actionLabel.setLabelFor(addField);
                        choiceFrame.revalidate();
                        choiceFrame.repaint();
                        choiceFrame.setVisible(true);

                        for (ActionListener actionListener : choiceButton.getActionListeners()) {
                            choiceButton.removeActionListener(actionListener);
                        }

                        ActionListener createAccAction = new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {

                                for (User user : swosh.getUserList()) {
                                    if (user.equals(user1)) {
                                        user1.addAccount(user1, (Long) addField.getValue(), 0.0D);
                                        JOptionPane.showMessageDialog(choiceFrame, "Account with id: " + addField.getValue() + " has been created");
                                        accChoice.addItem(Math.toIntExact((Long) addField.getValue()));
                                    }
                                }
                                addField.setValue(null);
                            }
                        };
                        choiceButton.addActionListener(createAccAction);

                        //TODO:  make queries to update the db with new account.

                        //TODO:Make query to db about account and update User/db. DELETE ACCOUNT
                    }else if (actionEvent.getActionCommand().equals("ok") && comboBox.getItemAt(comboBoxChoice).equalsIgnoreCase("delete account"))
                    {
                        choiceFrame.revalidate();
                        choiceFrame.repaint();
                        choiceFrame.setVisible(false);
                        accountData.setVisible(false);
                        nameLabel.setPreferredSize(new Dimension(0,0));
                        nameField.setPreferredSize(new Dimension(0,0));
                        addressLabel.setPreferredSize(new Dimension(0,0));
                        addressField.setPreferredSize(new Dimension(0,0));
                        emailLabel.setPreferredSize(new Dimension(0,0));
                        emailField.setPreferredSize(new Dimension(0,0));
                        phoneLabel.setPreferredSize(new Dimension(0,0));
                        phoneField.setPreferredSize(new Dimension(0,0));
                        Component[] components = choiceFrame.getComponents();
                        choiceFrame.setBounds(500, 0, 400, 400);

                       for(Component c : components) {
                             if (c instanceof JTextArea) {
                                choiceFrame.remove(c);
                            } else if (c instanceof JScrollPane) {
                                choiceFrame.remove(c);
                            }
                        }
                        accChoice.setPreferredSize(new Dimension(50,20));
                        addField.setPreferredSize(new Dimension(0,0));
                        choiceFrame.revalidate();
                        choiceFrame.repaint();

                        for (ActionListener actionListener : choiceButton.getActionListeners()) {
                            choiceButton.removeActionListener(actionListener);
                        }

                        int choiceItemSelect = accChoice.getSelectedIndex();
                        choiceFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
                        choiceFrame.setSize(400, 400);
                        choiceFrame.setTitle("Account deletion");
                        choiceFrame.add(actionLabel);
                        choiceFrame.add(accChoice);
                        choiceButton.setText("Delete");
                        choiceButton.setPreferredSize(new Dimension(120,30));
                        choiceButton.setActionCommand("delete");
                        choiceFrame.add(choiceButton);
                        actionLabel.setText("Delete the account with ID: ");
                        actionLabel.setLabelFor(accChoice);
                        choiceFrame.revalidate();
                        choiceFrame.repaint();



                        choiceButton.addActionListener(actionEvent1 -> user1.getAccount().removeIf(account ->
                            user1.getAccount().contains(account)
                        ));
                        choiceButton.addActionListener(actionEvent1 -> accChoice.removeItemAt(choiceItemSelect));
                        choiceButton.addActionListener(actionEvent1 -> JOptionPane.showMessageDialog(choiceFrame, "Account has been deleted!"));
                        choiceFrame.revalidate();
                        choiceFrame.repaint();


                        choiceFrame.setVisible(true);


                        //TODO: Add money to selected account
                    }else if (actionEvent.getActionCommand().equals("ok") && comboBox.getItemAt(comboBoxChoice).equalsIgnoreCase("add money")) {

                        choiceFrame.setTitle("Add money to your Swosh account");
                        choiceFrame.revalidate();
                        choiceFrame.repaint();
                        nameLabel.setPreferredSize(new Dimension(0,0));
                        nameField.setPreferredSize(new Dimension(0,0));
                        addressLabel.setPreferredSize(new Dimension(0,0));
                        addressField.setPreferredSize(new Dimension(0,0));
                        emailLabel.setPreferredSize(new Dimension(0,0));
                        emailField.setPreferredSize(new Dimension(0,0));
                        phoneLabel.setPreferredSize(new Dimension(0,0));
                        phoneField.setPreferredSize(new Dimension(0,0));

                        accountData.setColumns(0);
                        accountData.setRows(0);
                        accountData.setVisible(false);

                        choiceFrame.setBounds(500, 0, 500, 400);
                        Component[] components = choiceFrame.getComponents();
                        for(Component c : components) {
                             if (c instanceof JTextArea) {
                                choiceFrame.remove(c);
                            }
                        }
                        accChoice.setPreferredSize(new Dimension(50,20));
                        choiceFrame.revalidate();
                        choiceFrame.repaint();

                        for (ActionListener actionListener : choiceButton.getActionListeners()) {
                            choiceButton.removeActionListener(actionListener);
                        }


                        int choiceItemSelect = accChoice.getSelectedIndex();
                        choiceFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
                        choiceFrame.setSize(400, 400);
                        choiceButton.setText("Add money");
                        actionLabel.setText("Enter amount you want added: ");
                        addField.setPreferredSize(new Dimension(50, 25));
                        choiceFrame.add(accChoice);
                        choiceFrame.add(actionLabel);
                        actionLabel.setLabelFor(addField);
                        choiceFrame.add(addField);
                        choiceButton.setActionCommand("add");
                        choiceButton.setPreferredSize(new Dimension(120,30));
                        choiceFrame.add(choiceButton);

                        choiceFrame.setVisible(true);
                        ActionListener addMoneyAction = new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {

                                //TODO: Doesn't update account if you have more than 1 account per user.
                                if(actionEvent.getActionCommand().equalsIgnoreCase("add")) {
                                    for(Account acc : user1.getAccount()) {
                                        if (accChoice.getItemAt(choiceItemSelect) == acc.getID()){

                                            long amount = (long) addField.getValue();
                                            acc.addBalance(amount);
                                            JOptionPane.showMessageDialog(choiceFrame, "You have added "+ amount +" to account: "+acc.getID()+ ". The total amount in the account is" +
                                                    " "+acc.getBalance());
                                        }
                                    }
                                }

                            }
                        };
                        choiceButton.addActionListener(addMoneyAction);


                    }else if (actionEvent.getActionCommand().equals("ok") && comboBox.getItemAt(comboBoxChoice).equalsIgnoreCase("Print account statement")) {

                        accountData.setWrapStyleWord(true);
                        accountData.setLineWrap(true);
                        accountData.setEditable(false);
                        accountData.setColumns(50);
                        accountData.setRows(50);
                        accountData.setVisible(true);

                        nameLabel.setPreferredSize(new Dimension(0,0));
                        nameField.setPreferredSize(new Dimension(0,0));
                        addressLabel.setPreferredSize(new Dimension(0,0));
                        addressField.setPreferredSize(new Dimension(0,0));
                        emailLabel.setPreferredSize(new Dimension(0,0));
                        emailField.setPreferredSize(new Dimension(0,0));
                        phoneLabel.setPreferredSize(new Dimension(0,0));
                        phoneField.setPreferredSize(new Dimension(0,0));

                        if(!accountData.getText().isBlank()) {
                            accountData.setText("");
                        }

                        if(user1.getAccount().isEmpty()) {
                            JOptionPane.showMessageDialog(choiceFrame, "No accounts found!");
                        }

                        accountData.append(user1.toString()+ "\n");

                        for(Account acc : user1.getAccount()) {
                            accountData.append(acc.toString() + "\n");
                        }


                        choiceFrame.setVisible(false);
                        choiceFrame.setPreferredSize(new Dimension(800,800));

                        choiceFrame.add(accountData);

                        choiceFrame.revalidate();
                        choiceFrame.repaint();

                        choiceFrame.setTitle("Account Statement for User: "+ user1.getUserID());
                        choiceFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
                        accChoice.setPreferredSize(new Dimension(0,0));
                        addField.setPreferredSize(new Dimension(0,0));
                        choiceButton.setPreferredSize(new Dimension(0,0));
                        actionLabel.setPreferredSize(new Dimension(0,0));
                        choiceFrame.setVisible(true);


                    } else if (actionEvent.getActionCommand().equals("ok") && comboBox.getItemAt(comboBoxChoice).equalsIgnoreCase("delete your swosh")) {

                        mainFrame.setVisible(false);
                        choiceFrame.setVisible(false);
                        loginFrame.setVisible(true);

                        JOptionPane.showMessageDialog(loginFrame, "You have deleted your Swosh!");
                        swosh.removeUser(user1);
                        user1 = null;
                        
                    } else if (actionEvent.getActionCommand().equals("ok") && comboBox.getItemAt(comboBoxChoice).equalsIgnoreCase("update your information")) {

                        for (ActionListener actionListener : choiceButton.getActionListeners()) {
                            choiceButton.removeActionListener(actionListener);
                        }

                        nameLabel.setPreferredSize(new Dimension(75,25));
                        nameField.setPreferredSize(new Dimension(60,30));
                        addressLabel.setPreferredSize(new Dimension(90,25));
                        addressField.setPreferredSize(new Dimension(60,30));
                        emailLabel.setPreferredSize(new Dimension(75,25));
                        emailField.setPreferredSize(new Dimension(60,30));
                        phoneLabel.setPreferredSize(new Dimension(180,25));
                        phoneField.setPreferredSize(new Dimension(60,30));

                        Component[] components = choiceFrame.getComponents();
                        for(Component c : components) {
                            if (c instanceof JTextArea) {
                                choiceFrame.remove(c);
                            }
                        }

                        accountData.setVisible(false);
                        choiceFrame.setTitle("Your information");
                        choiceFrame.setPreferredSize(new Dimension(400, 400));
                        choiceFrame.setBounds(500,0,500,500);
                        choiceFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
                        choiceButton.setPreferredSize(new Dimension(120,30));
                        actionLabel.setPreferredSize(new Dimension(0,0));
                        addField.setPreferredSize(new Dimension(0,0));
                        choiceFrame.add(nameLabel);
                        choiceFrame.add(nameField);
                        choiceFrame.add(phoneLabel);
                        choiceFrame.add(phoneField);
                        choiceFrame.add(addressLabel);
                        choiceFrame.add(addressField);
                        choiceFrame.add(emailLabel);
                        choiceFrame.add(emailField);
                        choiceButton.setText("Update");
                        choiceFrame.add(choiceButton);
                        choiceFrame.setVisible(true);
                        choiceButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                user1.setAddress(addressField.getText());
                                user1.setName(nameField.getText());
                                if(!emailField.getText().matches(EMAIL_PATTERN)){
                                    JOptionPane.showMessageDialog(choiceFrame, "Please enter a valid email!");
                                }else {
                                    user1.setEmail(emailField.getText());
                                }
                                user1.setPhoneNr(Integer.parseInt(phoneField.getText()));
                                String queryUserID = query.createQuery("SELECT userid FROM users WHERE userid=");
                                query.createQuery("UPDATE users SET name= , email= , phonenumber= , address= WHERE userid = ;");
                            }
                        });

                    } else if (actionEvent.getActionCommand().equals("ok") && comboBox.getItemAt(comboBoxChoice).equalsIgnoreCase("send money")) {
                        choiceFrame.setVisible(false);
                        for (ActionListener actionListener : choiceButton.getActionListeners()) {
                            choiceButton.removeActionListener(actionListener);
                        }

                        for(Account acc: user1.getAccount()){
                            accChoice.addItem((int) acc.getID());
                        }


                        choiceFrame.setTitle("Send money");
                        choiceFrame.setPreferredSize(new Dimension(400, 400));
                        choiceFrame.setBounds(500,0,500,500);
                        choiceFrame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
                        choiceButton.setPreferredSize(new Dimension(120,30));
                        choiceButton.setText("Send");
                        choiceFrame.add(actionLabel);
                        actionLabel.setText("Amount you want to send: ");
                        actionLabel.setLabelFor(addField);
                        choiceFrame.add(addField);
                        choiceFrame.add(nameLabel);
                        nameLabel.setText("Send money to: ");
                        nameLabel.setLabelFor(nameField);
                        choiceFrame.add(nameField);
                        choiceFrame.add(accChoice);
                        choiceFrame.add(choiceButton);
                        //TODO: Send money screen

                        //Use addfield for amount to send
                        //Use nameField for recipient
                        //Use accChoice to choose account to send from
                        //Use choiceButton to send money


                        choiceFrame.setVisible(true);


                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });




        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                System.exit(0);
            }
        });

        loginFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });

        JLabel headerLabel = new JLabel("", JLabel.CENTER);
        JLabel statusLabel = new JLabel("", JLabel.CENTER);

        statusLabel.setSize(350,100);

        controlPanel = new JPanel(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(statusLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(optionsLabel); mainFrame.add(comboBox); mainFrame.add(okBtn);
        mainFrame.setVisible(false);
        loginFrame.setVisible(true);







    }
}

