package Unify;

import javax.swing.*;

public class mainPage extends JFrame{
    private JPanel mainPagePanel;
    private JTabbedPane walletTabs;
    private JLabel userName;
    private JLabel userTotal;
    private JLabel cardanoPrice;
    private JLabel price;
    protected User user;

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






    }


}
