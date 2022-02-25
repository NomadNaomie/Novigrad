package novigrad.core;

/**
 * Abstract User Account Class,
 * Customer account, Branch Account and Admin Account all inherit from this class
 */

public abstract class UserAccount {
    public abstract String getName();
    public abstract boolean login();
    public abstract boolean logout();
    public abstract String getPass();

}
