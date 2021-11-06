package Unify;

public class InformationController {
    // this will the interface between the gui and user

    private String username;
    private String password;
    private TestLoginPage testLoginPage;


    /**
     * Default constructor
     */
    public InformationController(){}

    public String getUserName(){
        username = testLoginPage.username;
        return username;
    }

    public String getPassword(){
        password = testLoginPage.password;
        return password;
    }


}
