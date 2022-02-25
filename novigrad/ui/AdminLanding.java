package novigrad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import novigrad.R;

public class AdminLanding extends AppCompatActivity {
    Button customerListButton,branchListButton,ServicesBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_landing);

        customerListButton = findViewById(R.id.customerListBtn);
        branchListButton  = findViewById(R.id.branchListBtn);
        ServicesBtn = findViewById(R.id.ServicesBtn);

        ServicesBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent serviceListIntent = new Intent(AdminLanding.this,ServiceAddOrRemove.class);
                serviceListIntent.putExtra("Account Type","Admin");
                startActivity(serviceListIntent);
            }
        });
        customerListButton.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v){
                Intent customerListIntent = new Intent(AdminLanding.this,AccountList.class);
                customerListIntent.putExtra("Type","Customer");
                startActivity(customerListIntent);
            }
        });
        branchListButton.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v){
                Intent branchListIntent = new Intent(AdminLanding.this,AccountList.class);
                branchListIntent.putExtra("Type","Branch");
                startActivity(branchListIntent);
            }
        });
    }
}