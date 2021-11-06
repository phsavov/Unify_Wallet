package Unify;

import java.sql.*;
import Unify.User;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws SQLException {

        //UserDatabase database = new UserDatabase();
        //UserDatabase database = new UserDatabase();
        //User testUser = new User();
        //System.out.println(database.checkUsername("phsavov"));
        //System.out.println(database.checkPassword("phsavov","123456"));
        //testUser.currentADAMarketPrice();
        HomePage home = new HomePage();
        //System.out.println(database.checkUsername("phsavov"));
        //testUser.currentADAMarketPrice();

        // This is to test the test pages
        JFrame frame = new TestHomePage("This is the title of the homepage");
        frame.setVisible(true);

        InformationController informationController = new InformationController();
        JFrame testLogin = new TestLoginPage("This is the one in main.", informationController);
    }
}
