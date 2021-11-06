package Unify;

import java.sql.*;
import Unify.User;

public class Main {

    public static void main(String[] args) throws SQLException {
        //UserDatabase database = new UserDatabase();
        UserDatabase database = new UserDatabase();
        //User testUser = new User();
        System.out.println(database.checkUsername("phsavov"));
        System.out.println(database.checkPassword("phsavov","123456"));
        //testUser.currentADAMarketPrice();
        HomePage home = new HomePage();
    }
}
