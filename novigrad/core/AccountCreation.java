package novigrad.core;
import android.util.Patterns;
import java.util.regex.*;
/**
//I made this with the idea that the methods will go into the MainActivity.
//Not sure if this is bad implementation, but can modify later to make it a basic class.
public class AccountCreation {

    private String acceptedPassword;

    //Learning how to use RegEx for this method
    private boolean passwordVerification(String ourPassword){
        //^ and $ act as anchors, ensuring that we check the whole string rather than stopping at a part of it
        //However, in our example, we don't need anchors. As soon as we find one occurrence of each, we can assume password is strong enough
        //?=.* checks to see if we have one occurrence of the character

        Pattern p = Pattern.compile("(?=.*[A-Z])" + "(?=.*[a-z])" + "(?=.*[0-9])" + "(?=.*[!@#$%^&*+])");
        Matcher m = p.matcher(ourPassword);
        return (m.find());
    }

    private boolean isEmailValid(){
        //Connect String userEmailInput to the appropriate UI field
        //String userInput = x.getText().toString().trim();
        if (userEmailInput.isEmpty()){
            //Notify that field is empty ("You have not entered anything")
            //x.setError("You have not entered anything");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(userEmailInput).matches()){
            //x.setError("The entered email address is not valid");
            return false;
        }else{
            //x.setError("");
            return true;
        }
    }

    private boolean isUsernameValid(){
        //Connect String userUsernameInput to the appropriate UI field
        //String userUsernameInput = x.getText().toString().trim();
        if (userUsernameInput.length() < 5 || userUsernameInput.length() > 14){
            //x.setError("Username must be between 5 and 14 characters");
            return false;
        }else{
            //x.setError("");
            return true;
        }
    }

    private boolean isPasswordValid(){
        //Connect String userPasswordInput to the appropriate UI field
        //String userPasswordInput = x.getText().toString();
        //Can modify desired strength with passwordVerification method
        if (userPasswordInput.length() < 6 || userPasswordInput.length() > 24){
            //x.setError("Password must be between 6 and 24 characters");
            return false;
        }else if (passwordVerification(userPasswordInput) == false){
            //x.setError("Password must contain each of the following: lowercase letter, uppercase letter, number 0-9, and a special character !@#$%^&*+")
            return false;
        }else{
            //x.setError("");
            acceptedPassword = userPasswordInput;
            return true;
        }
    }

    private boolean retypePassword(){
        //Connect String userRetype to the appropriate UI field
        //String userRetype = x.getText().toString();
        if (userRetype == acceptedPassword){
            return true;
        }else{
            //x.setError("Passwords do not match");
            return false;
        }
    }
}*/
