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


public class DriverLicenseForm extends AppCompatActivity {
    Button btn1,btn2,btn3; //1 = Upload/View Image, 2 = Submit / Accept, 3 = Hidden / Deny
    EditText FN,LN,DB,ADDR,IMG;
    Spinner LicenseType;
    String[] LicenseTypes = {"G","G2","G1"};
    String[] displayedType = new String[1];
    ServiceRequest request = new ServiceRequest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_license_form);
        String role  = getIntent().getStringExtra("Role");

        btn2 = findViewById(R.id.LicenseSubmit);
        FN = findViewById(R.id.licenseFN);
        LN = findViewById(R.id.licenseLN);
        DB = findViewById(R.id.licenseBD);
        IMG = findViewById(R.id.LicensePhotoURL);
        ADDR = findViewById(R.id.HCADR);
        LicenseType = findViewById(R.id.licenseType);

        if (role.equals("Branch")){
            ServiceRequest request = (ServiceRequest) getIntent().getSerializableExtra("request");
            btn2.setText("Accept Request");
            FN.setText(request.getFirst_name());
            LN.setText(request.getLast_name());
            DB.setText(request.getDate_of_birth());
            ADDR.setText(request.getAddress());
            displayedType[0] = request.getLicenseType();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,displayedType);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            LicenseType.setAdapter(adapter);

        }else if (role.equals("Customer")){
            btn2.setText("Submit Request");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,LicenseTypes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            LicenseType.setAdapter(adapter);
            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseWriter dbWrite = new DatabaseWriter();
                    request.setType("License");
                    if (!nameValidation(FN.getText().toString()) || !nameValidation(LN.getText().toString()) || FN.getText().length()<=0 || LN.getText().length()<=0){
                        Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid First and Last name", Toast.LENGTH_LONG);
                        toast.show();
                    } else if(!dobValidation(DB.getText().toString())){
                        Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid Date of Birth", Toast.LENGTH_LONG);
                        toast.show();
                    } else if(!addressValidation(ADDR.getText().toString())){
                        Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid address", Toast.LENGTH_LONG);
                        toast.show();
                    } else if(!urlValidation(IMG.getText().toString())){
                        Toast toast = Toast.makeText(getApplicationContext(), "Please enter a valid URL\nExample: https://www.uottawa.ca", Toast.LENGTH_LONG);
                        toast.show();
                    } else{
                        request.setFirst_name(FN.getText().toString());
                        request.setLast_name(LN.getText().toString());
                        request.setDate_of_birth(DB.getText().toString());
                        request.setAddress(ADDR.getText().toString());
                        request.setLicenseType(LicenseType.getSelectedItem().toString());
                        request.setPhoto(IMG.getText().toString());
                        request.setStatus("Pending");
                        dbWrite.serviceRequest(request,getIntent().getStringExtra("Account Name"));
                        Toast.makeText(DriverLicenseForm.this, "Request Submitted", Toast.LENGTH_SHORT).show();
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