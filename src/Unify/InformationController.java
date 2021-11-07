package Unify;

public class InformationController {
    // this will the interface between the gui and user

    private String username;
    private String password;
    private TestLoginPage testLoginPage;


    /**
     * Default constructor
     */
    public InformationController(){

        // Seeing if we can access the username and password variables from the TestLoginPage class
        System.out.println(getUserName());
        System.out.println(getPassword());
    }

    public String getUserName(){
        username = testLoginPage.username;
        return username;
    }

    public String getPassword(){
        password = testLoginPage.getPassword();
        password = testLoginPage.password;
        return password;
    }


}
