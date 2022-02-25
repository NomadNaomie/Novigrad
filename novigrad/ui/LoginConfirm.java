package novigrad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import novigrad.R;

import novigrad.core.BranchHours;
import novigrad.services.DriverLicenseForm;
import novigrad.services.HealthCardForm;
import novigrad.services.PhotoID;

public class LoginConfirm extends AppCompatActivity {
    /**     *
     * Button 1 Working Hours / Search
     * Button 2 Add+Remove services from Branch / View Services
     * Button 3 View Service Requests /
     * Button 4 Set Address
     */

    TextView welcome;
    Button button1,button2,button3,button4,logoutButton;
    String user, role;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_confirm);

        welcome = findViewById(R.id.textViewWelcome);

        logoutButton = findViewById(R.id.logoutButton);
        button1 = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);
        button3 = findViewById(R.id.btn3);
        button4 = findViewById(R.id.btn4);


        Intent intent = getIntent();
        user = (intent.getStringExtra("User"));
        role = (intent.getStringExtra("Role"));
        welcome.setText(getString(R.string.welcome, user, role));

        if (role.equals("Customer")){
            button4.setVisibility(View.INVISIBLE);
            button3.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.INVISIBLE);

            button4.setText("Photo ID");
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginConfirm.this, PhotoID.class);
                    intent.putExtra("Role","Customer");
                    startActivity(intent);
                }
            });
            button3.setText("Health Card Request");
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginConfirm.this, HealthCardForm.class);
                    intent.putExtra("Role","Customer");
                    startActivity(intent);
                }
            });
            button2.setText("License Request");
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginConfirm.this, DriverLicenseForm.class);
                    intent.putExtra("Role","Customer");
                    startActivity(intent);
                }
            });
            button1.setText("Search Branches");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginConfirm.this, SearchForBranches.class);
                    startActivity(intent);
                }
            });

        }else {

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginConfirm.this, LoginScreen.class);
                    startActivity(intent);
                }
            });

            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(LoginConfirm.this, BranchHours.class);
                    intent.putExtra("Account Name", user.replace(".", "-"));
                    startActivity(intent);
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(LoginConfirm.this, ServiceAddOrRemove.class);
                    intent.putExtra("Account Name", user.replace(".", "-"));
                    intent.putExtra("Account Type", role);
                    startActivity(intent);
                }
            });
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginConfirm.this, ServiceRequestAcceptReject.class);
                    intent.putExtra("Account Name", user.replace(".", "-"));
                    startActivity(intent);
                }
            });
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginConfirm.this,BranchAddressSet.class);
                    intent.putExtra("Account Name", user.replace(".", "-"));
                    startActivity(intent);

                }
            });


        }

    }


}