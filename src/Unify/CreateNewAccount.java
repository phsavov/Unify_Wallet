package Unify;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// TODO connect this gui to user database
public class CreateNewAccount extends JFrame{

    private JPanel createAccountPanel;
    private JButton createButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField passConfirmField;
    private JPasswordField spendPassField;
    private JPasswordField spendPassConField;
    private JLabel Header;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel passConfirmLabel;
    private JLabel spendPassLabel;
    private JLabel spendPassConLabel;
    public static String username;
    private char[] passwordArray;
    public static String password;
    private char[] passConfirmArray;
    public static String passConfirm;
    private char[] spendPassArray;
    public static String spendPassword;
    private char[] spendPassConArray;
    public static String spendPassConfirm;
    public static String mnemonicCode;


    /**
      * Default constructor
      */
    public CreateNewAccount() { username = "default"; password = "defaultPass"; spendPassword = "defaultPass"; }

    /**
     * Initialize the create new account window
     * @param title: String
     */
    public CreateNewAccount(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(1000, 1000);
        this.setContentPane(createAccountPanel);
        this.setVisible(true);
        this.pack();

        /**
         * creating an action for when the crete button is pressed
         * the gui reads in the new account info and then sends the information to the
         * database to add the account
         */
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Get user's username, password, and spending passwords + confirmations
                if (e.getSource() == createButton) {
                    username = usernameField.getText();
                    passwordArray = passwordField.getPassword();
                    passConfirmArray = passConfirmField.getPassword();
                    spendPassArray = spendPassField.getPassword();
                    spendPassConArray = spendPassConField.getPassword();
                    password = String.valueOf(passwordArray);
                    passConfirm = String.valueOf(passConfirmArray);
                    spendPassword = String.valueOf(spendPassArray);
                    spendPassConfirm = String.valueOf(spendPassConArray);
                }

                //Have user reenter passwords until they match with confirmed
                if(!password.equals(passConfirm)){
                    JOptionPane.showMessageDialog(createButton, "Passwords do not match");
                }
                else if (!spendPassword.equals(spendPassConfirm)){
                    JOptionPane.showMessageDialog(createButton, "Spend Passwords do not match");
                }
                else{
                    try {
                        // Adding a new user to the database
                        UserDatabase userDatabase = new UserDatabase();
                        User user = new User(userDatabase.nextAccountId(), username, password, spendPassword, 0);
                        userDatabase.updateUserDB(user);

                        JOptionPane.showMessageDialog(createButton, "Created account!");

                        CreateNewAccount.super.dispose();
                        HomePage homePage = new HomePage("Unify");

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }
}