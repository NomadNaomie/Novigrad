/*package novigrad.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import novigrad.R;
import novigrad.database.DatabaseWriter;
import novigrad.services.Service;

public class ServiceCreation extends AppCompatActivity {

    String[] services = {"Health Card","License","Photo ID"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_creation);
        final TextView ServiceLabel = findViewById(R.id.ServiceCreationLabel);
        Spinner offerServices = findViewById(R.id.offerServiceSpinner);

        Button CreateService = findViewById(R.id.addServiceBtn);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        offerServices.setAdapter(adapter);

        CreateService.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
    }

*/