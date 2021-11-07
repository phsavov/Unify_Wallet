package Unify;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestLoginPage extends JFrame{
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JButton loginButton;
    private JLabel passwordLable;
    public String username;
    private char[] passwordArray;
    public String password;


    public TestLoginPage(){ username = "default"; password = "defaultPass"; }

    public TestLoginPage(String title) {
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

                    // Call constructor
                    //System.out.println(informationController.getUserName());
                    //System.out.println(informationController.getPassword());

                    // Authentication of the user inside a try catch
                    try {
                        UserDatabase userDatabase = new UserDatabase();
                        boolean loggedIn = false;
                        while (!loggedIn) {
                            if (userDatabase.checkCredentials(username, password)) {
                                loggedIn = true;
                                // Pop up window confirming successful login or failed login
                                /*JDialog dia = new JDialog(TestLoginPage.this);
                                JLabel l = new JLabel("Logged in Successfully");
                                dia.add(l);
                                dia.setSize(420, 420);
                                dia.setVisible(true);*/

                                TestLoginPage.super.dispose();                              // Disposes the login page window when auth passes
                                // Creates the transaction page
                                mainPage main = new mainPage("Unify",  userDatabase.getUserInfo(username, password));

                            } else {
                                JDialog dia = new JDialog(TestLoginPage.this);
                                JLabel l = new JLabel("Login Failed");
                                dia.add(l);
                                dia.setSize(420, 420);
                                dia.setVisible(true);
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }
        });
    }

    // Overloaded constructor to handle data transfer to the Information Controller class
    //public TestLoginPage(InformationController infoc) { this.informationController = infoc; }
    public void TestLoginPage(String title, InformationController infoc) {
        //super(title);
        //this.informationController = infoc;
    }

    /** Gets the password from the password field text
     *  Returns a string object
     **/
    public String getPassword(){
        password = String.valueOf(passwordArray);
        return password;
    }


}