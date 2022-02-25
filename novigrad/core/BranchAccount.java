package novigrad.core;

import java.util.ArrayList;

import novigrad.services.Service;

/**
 * The branch will later need to store Working hours, address etc, we need to make a design about whether these classes
 * will represent the information about its users themselves OR whether we make another class that
 * stores them and these accounts are simply representations of sessions
 *
 * Ie have another class Branch where in the login() method of BranchAccount we call Branch("ID") which
 * creates an instance of a branch that retrieves all the information from the DB and we use this instance
 * as a session.
 *
 * We may want to hash passwords.
 *
 */
public class BranchAccount extends UserAccount {
    /**
     * Extends the UserAccount abstract classs
     * 2 instance variables, email and password, both string
     * 4 inherited methods
     * getName() returns name (subject to change)
     * getEmail() returns email
     * getPass() returns password (subject to change)
     * login() and logout() are currently unused but will be called whenever a login or logout occurs,
     * logout() will update information in the DB
     *
     *
     */
    String email;
    String pass;
    String name;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        this.Address = Address;
    }

    String Address;
    ArrayList<Service> servicesOffered;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.replace(".","-");
    } //Replaced because DB doesn't accept "." in paths and email is the path.

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public ArrayList<Service> getServicesOffered(){return servicesOffered;};
    public void addService(Service service){servicesOffered.add(service);}

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name){this.name=name;}

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }

}
