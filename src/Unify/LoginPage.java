package Unify;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage extends JFrame{
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JButton loginButton;
    private JLabel passwordLable;
    private JButton createButton;

    public String username;
    private char[] passwordArray;
    public String password;

    // Default Constructor
    public LoginPage(){ username = "default"; password = "defaultPass"; }

    /**
     * Initialize the Login Page window
     * @param title: String
     */
    public LoginPage(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(1000, 1000);
        this.setContentPane(loginPanel);
        this.setVisible(true);
        this.pack();

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // send username and password to the Information Controller class
                if (e.getSource() == loginButton) {
                    username = usernameField.getText();
                    passwordArray = passwordField.getPassword();
                    password = String.valueOf(passwordArray);

                    // Authentication of the user inside a try catch
                    try {
                        UserDatabase userDatabase = new UserDatabase();
                        boolean loggedIn = false;
                        while (!loggedIn) {
                            if (userDatabase.checkCredentials(username, password)) {
                                loggedIn = true;

                                // Pop up window confirming successful login or failed login
                                JOptionPane.showMessageDialog(createButton, "Login Successful");

                                // Disposes the login page window when auth passes
                                LoginPage.super.dispose();
                                // Creates the transaction page
                                mainPage main = new mainPage("Unify",  userDatabase.getUserInfo(username, password));

                            } else {
                                // Pop up window confirming login failed
                                JOptionPane.showMessageDialog(createButton, "Login Failed");
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * Gets the password from the password field text
     * @return password: String
     */
    public String getPassword(){
        password = String.valueOf(passwordArray);
        return password;
    }
}