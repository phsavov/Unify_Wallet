package Unify;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {

    private JLabel homePageLabel;
    private JButton loginButton;
    private JLabel imageLogo;
    private JPanel homePagePanel;
    private JButton createAccountButton;

    /**
     * Initialize the home page window
     * @param title
     */
    public HomePage(String title) {
        super(title);   // Calls parent (JFrame) constructor

        // Setting up the JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(500, 500);
        this.setContentPane(homePagePanel);
        this.setVisible(true);
        this.pack();

        // Action when button is clicked
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // If login button is clicked
                // then close this window and open the login window
                if (e.getSource() == loginButton){
                    HomePage.super.dispose();
                    LoginPage LoginPage = new LoginPage("Log In");
                }
            }
        });

        // Action for the create account button to go to the create account window
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == createAccountButton) {
                    HomePage.super.dispose();
                    CreateNewAccount account = new CreateNewAccount("Create New Account");
                }
            }
        });
    }
    
    // Add the Unify Wallet Logo
    private void createUIComponents() {
        imageLogo = new JLabel(new ImageIcon("Unify Wallet Logo.jpg"));
    }
}