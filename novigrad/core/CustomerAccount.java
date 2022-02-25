package novigrad.core;

/**
 * See BranchAccount docs for design dicussion
 */
public class CustomerAccount extends UserAccount {
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
    String email,password,name;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.replace(".","-"); //Replaced because "."s can't be in DB's path names and the email is the path name.
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String getName() {
        return name;
    }

    public void setName(String name){this.name = name;}

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
        return password;
    }
}
