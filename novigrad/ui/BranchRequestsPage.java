package novigrad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import novigrad.R;
import novigrad.database.DatabaseReader;
import novigrad.services.DriverLicenseForm;
import novigrad.services.HealthCardForm;
import novigrad.services.PhotoID;

public class BranchRequestsPage extends AppCompatActivity {
    Button license,health,id,rate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_requests_page);
        license = findViewById(R.id.licenseReqB);
        health = findViewById(R.id.hcReqB);
        id = findViewById(R.id.idReqB);
        rate = findViewById(R.id.ratingB);
        license.setVisibility(View.INVISIBLE);
        health.setVisibility(View.INVISIBLE);
        id.setVisibility(View.INVISIBLE);
        license.setVisibility(View.INVISIBLE);
        DatabaseReader dbAccess = new DatabaseReader();


        dbAccess.getBranchServices(new DatabaseReader.BranchServices() {
            @Override
            public void getBranchServices(ArrayList<String> services) {
                for (int i = 0;i<services.size();i++){
                    System.out.println(services.get(i));
                    if (services.get(i).equals("Health Card")){
                        health.setVisibility(View.VISIBLE);
                    }else if (services.get(i).equals("Photo ID")){
                        id.setVisibility(View.VISIBLE);
                    }else if (services.get(i).equals("License")){
                        license.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        dbAccess.getServicesOfBranch(getIntent().getStringExtra("Account Name"));



        license.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchRequestsPage.this, DriverLicenseForm.class);
                intent.putExtra("Account Name",getIntent().getStringExtra("Account Name"));
                intent.putExtra("Role","Customer");
                startActivity(intent);

            }
        });
        health.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchRequestsPage.this, HealthCardForm.class);
                intent.putExtra("Account Name",getIntent().getStringExtra("Account Name"));
                intent.putExtra("Role","Customer");
                startActivity(intent);

            }
        });
        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchRequestsPage.this, PhotoID.class);
                intent.putExtra("Account Name",getIntent().getStringExtra("Account Name"));
                intent.putExtra("Role","Customer");
                startActivity(intent);

            }
        });
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BranchRequestsPage.this, BranchRate.class);
                intent.putExtra("Account Name",getIntent().getStringExtra("Account Name"));
                intent.putExtra("Role","Customer");
                startActivity(intent);
            }
        });
    }

}