package Unify;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestHomePage extends JFrame {

    private JLabel homePageLabel;
    private JButton loginButton;
    private JLabel imageLogo;
    private JPanel homePagePanel;
    private JButton createAccountButton;
    public InformationController informationController;

    public TestHomePage(String title) {
        super(title);   // Calls parent (JFrame) constructor

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
                    TestHomePage.super.dispose();
                    TestLoginPage testLoginPage = new TestLoginPage("Test Login Page");
                }
            }
        });
    }
    
    

    private void createUIComponents() {
        imageLogo = new JLabel(new ImageIcon("Unify Wallet Logo.jpg"));
    }
}