package Unify;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class mainPage extends JFrame{
    private JPanel mainPagePanel;
    private JTabbedPane walletTabs;
    private JLabel userName;
    private JLabel userTotal;
    private JLabel cardanoPrice;
    private JLabel price;
    private JButton cancelButton;
    private JButton generateAddressButton;
    private JTextField a000TextField;
    private JTextField walletAddressTextField;
    private JTextField enterAWalletAddressTextField;
    private JTextField a000ADATextField;
    private JTextField writeAnOptionalMessageTextField;
    private JButton sendButton;
    private JPanel homeTab;
    private JPanel SendTab;
    private JPanel receiveTab;
    private JButton refreshButton;
    private JLabel messageLabel;
    private JTextField enterAmountHereTextField;
    private JButton ADDButton;
    private JPasswordField spendingPasswordField;
    private JLabel spendingPassword;
    protected User user;


    /**
     * Initialize the main page window
     * @param title: String
     * @param user: User
     */
    public mainPage(String title, User user){

        super(title);
        this.user = user;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);
        this.setSize(500, 500);
        this.setContentPane(mainPagePanel);
        userName.setText(user.getUsername()+"'s Wallet");
        userTotal.setText(String.valueOf(user.getAccountTotal())+" ADA");
        price.setText("$"+String.valueOf(user.currentADAMarketPrice()));
        this.setVisible(true);

        //TODO connect the transaction database to this gui
        // TODO make a working refresh button
        // TODO finish this class

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // this is the code for the refresh button
                // this gets the account total and the current ADA price
                if (e.getSource() == refreshButton){
                    try {
                        user.updateTotal();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    userTotal.setText(String.valueOf(user.getAccountTotal())+" ADA");
                    price.setText("$"+String.valueOf(user.currentADAMarketPrice()));
                    setVisible(true);
                }
            }
        });
        /**
         * this button is to commit to sending ADA to another users wallet
         * the code gets the information that the user typed in and then tries to complete the transaction
         * if there are any issues then it will print an error message
         */
        sendButton.addActionListener(new ActionListener() { //when a button is pushed in the GUI
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == sendButton){
                    String address = enterAWalletAddressTextField.getText();
                    double amount = Double.valueOf(a000ADATextField.getText());
                    String spendPass = String.valueOf(spendingPasswordField.getPassword());
                    if (!spendPass.equals(user.getSpendingPassword())){
                        messageLabel.setText("Wrong Spending Password"); //Error message
                        setVisible(true);
                    }
                    //TODO make optional message work with project 5
                    Transaction transaction = null;
                    try {
                        transaction = new Transaction();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        messageLabel.setText("Error with Database"); //Error message
                        setVisible(true);
                    }
                    try {
                        transaction.processSendingTransaction(user, address, amount);
                        messageLabel.setText("Transaction Processed"); //success message
                        setVisible(true);
                        userTotal.setText(String.valueOf(user.getAccountTotal())+" ADA");
                        setVisible(true);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        messageLabel.setText("Transaction Could Not Process"); //error/failure message
                        setVisible(true);
                    }
                }
            }
        });


        //This button generates an address and sets it as the wallet's address
        generateAddressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { //When the generateAddress button is pushed in the GUI
                if (e.getSource() == generateAddressButton){
                    UserDatabase db = null;
                    try {
                        db = new UserDatabase();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    String address = user.getAddress();
                    try {
                        db.updateAddress(user, address);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                    walletAddressTextField.setText(address); //Set the wallet address to the newly generated address
                    setVisible(true);
                }
            }
        });

        /**
         * this action listener is for adding more ADA to your account
         * the code does is that it gets the users input from the gui and then verifies that its
         * a number and then adds the amount inputted to the account
         */

        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == ADDButton){
                    double amount = Double.valueOf(enterAmountHereTextField.getText());
                    if (amount <= 0){
                        JOptionPane.showMessageDialog(ADDButton, "Enter a Number that is greater than 0");
                    } else {
                        try {
                            UserDatabase database = new UserDatabase();
                            if (database.addFunds(user, amount)){
                                JOptionPane.showMessageDialog(ADDButton, "Successful");
                                userTotal.setText(String.valueOf(user.getAccountTotal())+" ADA");
                                setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(ADDButton, "Error With Database");
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                    }
                }
            }
        });
    }


}
