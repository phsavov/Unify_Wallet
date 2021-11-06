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
    private JLabel passwordLabel;
    private JButton loginButton;

    private InformationController informationController;

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
                    JDialog dia = new JDialog(TestLoginPage.this);
                    JLabel l = new JLabel("this is the dialog window");
                    dia.add(l);
                    dia.setSize(420, 420);
                    dia.setVisible(true);
                    usernameField.getText();
                }
            }
        });
    }

    // Overloaded constructor to handle data transfer to the Information Controller class
    public TestLoginPage(InformationController infoc) { this.informationController = infoc; }

}