package novigrad.services;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.URL;

import novigrad.R;
import novigrad.database.DatabaseWriter;

public class HealthCardForm extends AppCompatActivity {
        Button btn2; //1 = Upload/View Image, 2 = Submit / Accept, 3 = Hidden / Deny
        EditText FN,LN,DB,ADDR,IMG,POS;
        ServiceRequest request = new ServiceRequest();
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_card_form);
        String role  = getIntent().getStringExtra("Role");

        btn2 = findViewById(R.id.HCSubmit);
        FN = findViewById(R.id.HCFNIn);
        LN = findViewById(R.id.HCLNIn);
        DB = findViewById(R.id.HCBDIn);
        IMG = findViewById(R.id.HCPRIn);
        ADDR = findViewById(R.id.HCADDR);
        POS = findViewById(R.id.HCPSIn);

        if (role.equals("Customer")){
            btn2.setText("Submit Request");
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseWriter dbWrite = new DatabaseWriter();
                    request.setType("Health Card");
                    if (!nameValidation(FN.getText().toString()) || !nameValidation(LN.getText().toString()) || FN.getText().length()<=0 || LN.getText().length()<=0){
                        Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid First and Last name", Toast.LENGTH_LONG);
                        toast.show();
                    }else if(!dobValidation(DB.getText().toString())){
                        Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid Date of Birth", Toast.LENGTH_LONG);
                        toast.show();
                    }else if(!addressValidation(ADDR.getText().toString())){
                        Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid address", Toast.LENGTH_LONG);
                        toast.show();
                    }else if(!urlValidation(IMG.getText().toString())){
                        Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid URL\nExample: https://www.uottawa.ca", Toast.LENGTH_LONG);
                        toast.show();
                    }else{
                        request.setFirst_name(FN.getText().toString());
                        request.setLast_name(LN.getText().toString());
                        request.setDate_of_birth(DB.getText().toString());
                        request.setAddress(ADDR.getText().toString());
                        request.setLicenseType(null);
                        request.setPhoto(IMG.getText().toString() + "and " +POS.getText().toString());
                        request.setStatus("Pending");
                        dbWrite.serviceRequest(request,getIntent().getStringExtra("Account Name"));
                        Toast.makeText(HealthCardForm.this, "Request Submitted", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    public boolean nameValidation(String input){
        if (input.length() <=0){
            return false;
        }
        String inputValue[] = input.split(" ");
        if (inputValue.length > 1){
            return false;
        }
        return (input.matches("[a-zA-Z]+"));
    }
    public boolean dobValidation(String input){
        if (input.indexOf('/') == -1){
            return false;
        }
        String inputValue[] = input.split("/");
        if (inputValue.length != 3){
            return false;
        }
        if (!inputValue[0].matches("[0-9]+") || !inputValue[1].matches("[0-9]+") || !inputValue[2].matches("[0-9]+")){
            return false;
        }
        int one = Integer.parseInt(inputValue[0]);
        int two = Integer.parseInt(inputValue[1]);
        int three = Integer.parseInt(inputValue[2]);
        if (one>31 || one<=0 || two >12 || two<=0 || three<0){
            return false;
        }
        return true;
    }
    public boolean addressValidation(String input){
        if (input.length() <= 0){
            return false;
        }
        input = input.replaceAll("\\s+","");
        if (!input.matches("[0-9a-zA-Z]+")){
            return false;
        }
        if (input.matches("[0-9]+") && !input.matches("[a-zA-Z]+")){
            return false;
        }
//        if (input.matches("[0-9A-Z]+") && input.length()==6 &&
//                (!Character.isDigit(input.charAt(1)) || !Character.isDigit(input.charAt(3)) || !Character.isDigit(input.charAt(5)) ||
//        !Character.isLetter(input.charAt(0)) || !Character.isLetter(input.charAt(2)) || !Character.isLetter(input.charAt(4)))){
//            return false;
//        }
        return true;
    }
    public boolean urlValidation(String input){
        try{
            new URL(input).toURI();
        }catch(Exception e){
            return false;
        }
        return true;
    }
}