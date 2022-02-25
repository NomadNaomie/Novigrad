package novigrad.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.PatternsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import novigrad.R;
import novigrad.core.AccountState;
import novigrad.core.BranchAccount;
import novigrad.core.CustomerAccount;
import novigrad.database.DatabaseReader;
import novigrad.database.DatabaseWriter;

/**
 * Login Screeen
 * 3 Field inputs, 2 used as of v0.1
 * 2 buttons, login and create account. Create account doesn't automatically log you in
 * Create account also automatically overrides an existing account.
 * Admin account not implemented yet.
 */

public class LoginScreen extends AppCompatActivity {
    Spinner accountTypeDropDown;
    final String[] accountTypes = {"Customer","Branch","Admin"};  //used for dropdown (spinner) choices.
    TextView errorMsg;
    EditText emailInput,passwordInput,nameInput;
    Button loginButton,createAccountButton;
    //Database IO Objects
    DatabaseWriter dbWrite;
    DatabaseReader dbAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        accountTypeDropDown = findViewById(R.id.accountTypeDropDown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,accountTypes); //Setting the Drop down to have the choices found in String [] accountTypes
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        accountTypeDropDown.setAdapter(adapter);

        dbWrite = new DatabaseWriter();
        dbAccess = new DatabaseReader();

        loginButton = findViewById(R.id.loginAccountBtn);
        createAccountButton = findViewById(R.id.createAccountBtn);

        emailInput = findViewById(R.id.editTextEmailEntry);
        passwordInput = findViewById(R.id.editTextPasswordEntry);
        nameInput = findViewById(R.id.editTextName);

        errorMsg = findViewById(R.id.userMessageText);
        //Click listener for create account, automatically overwrites any existing data
        createAccountButton.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v){
                //Checks whether the email conforms to pattern.
                if (!isEmailValid(emailInput.getText().toString())){
                    errorMsg.setText("The email you entered is invalid");
                }
                //Checks whether password conforms to pattern.
                else if (isPasswordValid(passwordInput.getText().toString())){ //THIS IS NOT WORKING
                    errorMsg.setText("The password you entered is invalid");
                }
                else{ //If
                    String accountSelection = accountTypeDropDown.getSelectedItem().toString();
                    switch (accountSelection) {
                        case "Customer": {
                            CustomerAccount newAccount = new CustomerAccount();
                            newAccount.setEmail(emailInput.getText().toString());
                            newAccount.setPassword(passwordInput.getText().toString());
                            dbWrite.customerAccountEntry(newAccount);
                            errorMsg.setText("Account Created.");
                            break;
                        }
                        case "Branch": {
                            BranchAccount newAccount = new BranchAccount();
                            newAccount.setEmail(emailInput.getText().toString());
                            newAccount.setPass(passwordInput.getText().toString());
                            dbWrite.branchAccountEntry(newAccount);
                            errorMsg.setText("Account Created.");
                            break;
                        }
                        case "Admin":
                            errorMsg.setText("Can't Create New Admin Accounts");
                    }
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (accountTypeDropDown.getSelectedItem().toString().equals("Admin")){
                    Intent adminIntent = new Intent(LoginScreen.this,AdminLanding.class);
                    startActivity(adminIntent);
                }
                if (!isEmailValid(emailInput.getText().toString())) {
                    errorMsg.setText("The email you entered is invalid");
                } else if (isPasswordValid(passwordInput.getText().toString())) { //THIS IS NOT WORKING
                    errorMsg.setText("The password you entered is invalid");
                } else {
                    String accountSelection = accountTypeDropDown.getSelectedItem().toString();
                    switch (accountSelection) {
                        case "Customer": {
                            CustomerAccount newAccount = new CustomerAccount();
                            newAccount.setName(nameInput.getText().toString());
                            newAccount.setEmail(emailInput.getText().toString());
                            newAccount.setPassword(passwordInput.getText().toString());
                            dbAccess.checkExistingCustomerAccount(newAccount);
                            break;
                        }
                        case "Branch": {
                            BranchAccount newAccount = new BranchAccount();
                            newAccount.setName(nameInput.getText().toString());
                            newAccount.setEmail(emailInput.getText().toString());
                            newAccount.setPass(passwordInput.getText().toString());
                            newAccount.setAddress("None");
                            dbAccess.checkExistingBranchAccount(newAccount);
                            break;
                        }
                    }
                }
            }
        });

        AccountState state;
        dbAccess.setListener(new DatabaseReader.Listener(){
            @SuppressLint("SetTextI18n")
            @Override
            public void statusReady(AccountState state) {
                if (state==AccountState.LOGGEDIN){
                    Intent intent = new Intent(LoginScreen.this, LoginConfirm.class);
                    if (accountTypeDropDown.getSelectedItem().toString().equals("Admin")){
                        intent.putExtra("User","SuperUser");
                        intent.putExtra("Role","Admin");
                    }else {
                        intent.putExtra("User", emailInput.getText().toString());
                        intent.putExtra("Role", accountTypeDropDown.getSelectedItem().toString());
                    }
                    startActivity(intent);
                }else if (state == AccountState.DOESNOTEXIST){
                    errorMsg.setText("Account doesn't exist");
                }else if (state==AccountState.EXISTS){
                    errorMsg.setText("Wrong password");
                }
            }
        });
    }

    //Learning how to use RegEx for this method
    public boolean passwordVerification(String ourPassword){
        //^ and $ act as anchors, ensuring that we check the whole string rather than stopping at a part of it
        //However, in our example, we don't need anchors. As soon as we find one occurrence of each, we can assume password is strong enough
        //?=.* checks to see if we have one occurrence of the character

        Pattern p = Pattern.compile("(?=.*[A-Z])" + "(?=.*[a-z])" + "(?=.*[0-9])" + "(?=.*[!@#$%^&*+])");
        Matcher m = p.matcher(ourPassword);
        return (m.find());
    }

    public boolean isEmailValid(String userEmailInput){
        //Connect String userEmailInput to the appropriate UI field
        //String userInput = x.getText().toString().trim();
        //x.setError("The entered email address is not valid");
        //x.setError("");

        if (userEmailInput.isEmpty()){
            //Notify that field is empty ("You have not entered anything")
            //x.setError("You have not entered anything");
            return false;
        }else return PatternsCompat.EMAIL_ADDRESS.matcher(userEmailInput).matches();
    }


    public boolean isPasswordValid(String userPasswordInput){
        //SOMETHING IS WRONG HERE
        //Connect String userPasswordInput to the appropriate UI field
        //String userPasswordInput = x.getText().toString();
        //Can modify desired strength with passwordVerification method
        //x.setError("Password must contain each of the following: lowercase letter, uppercase letter, number 0-9, and a special character !@#$%^&*+")
        //x.setError(""); 
        if (userPasswordInput.length() < 6 || userPasswordInput.length() > 24){
            //x.setError("Password must be between 6 and 24 characters");
            return false;
        }else return passwordVerification(userPasswordInput);
    }

}
