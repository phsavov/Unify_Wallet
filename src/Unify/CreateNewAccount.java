package Unify;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    //public static InformationController informationController;

    public CreateNewAccount(){ username = "default"; password = "defaultPass"; spendPassword = "defaultPass";}

    public CreateNewAccount(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(1000, 1000);
        this.setContentPane(createAccountPanel);
        this.setVisible(true);
        this.pack();

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // send username and password to the Information Controller class
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

                if(!password.equals(passConfirm)){
                    JOptionPane.showMessageDialog(createButton, "Passwords do not match");
                }
                else if (!spendPassword.equals(spendPassConfirm)){
                    JOptionPane.showMessageDialog(createButton, "Spend Passwords do not match");
                }
                else{
                    //Change with adding new user to database
                    //Send user to main
                    JDialog dia = new JDialog(CreateNewAccount.this);
                    JLabel l = new JLabel("New Account Created");
                    dia.add(l);
                    dia.setSize(420, 420);
                    dia.setVisible(true);
                }
            }
        });

    }
}