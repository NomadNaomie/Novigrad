package novigrad.core;


/**
 * Enumeration used for account states at login, DB writing returns one of these to indicate how the
 * process went.
 *
 */
public enum AccountState {
    EXISTS, //If the entry is found in the DB
    DOESNOTEXIST, //If entry is not in DB
    LOGGEDIN,
    CREATED, //IF entry is in DB and Passwords match
    ERROR //Any other response.

}
