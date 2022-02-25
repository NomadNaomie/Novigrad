package novigrad.core;


public class AdminAccount extends UserAccount {
    /**
     * Extends the UserAccount abstract classs
     * 2 instance variables, email and password, both string
     * 4 inherited methods
     * getName() returns email (subject to change)
     * getPass() returns password (subject to change)
     * login() and logout() are currently unused but will be called whenever a login or logout occurs,
     * logout() will update information in the DB
     *
     *
     */
    String email,password;
    @Override
    public String getName() {
        return email;
    }

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public String getPass() {
        return null;
    }
}
