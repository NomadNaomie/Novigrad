package novigrad.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;

import novigrad.R;
import novigrad.core.CustomerAccount;
import novigrad.database.DatabaseReader;
import novigrad.database.DatabaseWriter;
import novigrad.database.ServiceListAdapter;
import novigrad.services.Service;

public class ServiceAddOrRemove extends AppCompatActivity {
    DatabaseWriter dbWrite;
    DatabaseReader dbAccess;
    ListView ServiceView;
    Spinner ServiceChooser;
    Button AddServiceBtn;
    String [] Services = {"License","Health Card","Photo ID"};
    ArrayList<String> AvailableServices = new ArrayList<String>();
    ArrayList<String> viewList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<String> viewAdapater;
        ArrayAdapter<String> AdminService;
        setContentView(R.layout.activity_service_add_or_remove);

        final String role = getIntent().getStringExtra("Account Type");
        ServiceView = findViewById(R.id.servicesOfferedView);
        ServiceChooser = findViewById(R.id.serviceDropDown);
        AddServiceBtn = findViewById(R.id.addService);

        dbAccess = new DatabaseReader();
        dbWrite = new DatabaseWriter();

        if (role.equals("Admin")){
            ArrayAdapter<String> adminAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,Services);
            adminAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ServiceChooser.setAdapter(adminAdapter);

            dbAccess.getServices(new DatabaseReader.Services() {
                @Override
                public void getServices(ArrayList<String> services) {
                    AvailableServices = services;
                    ArrayAdapter<String> AdminService = new ArrayAdapter<String>(ServiceAddOrRemove.this, android.R.layout.simple_spinner_item,AvailableServices);
                    ServiceView.setAdapter(AdminService);
                }
            });
            dbAccess.getAvailableServices();

        }else if (role.equals("Branch")){

            dbAccess.getServices(new DatabaseReader.Services() {
                @Override
                public void getServices(ArrayList<String> services) {
                    AvailableServices = services;
                    ArrayAdapter<String> branchAdapter = new ArrayAdapter<String>(ServiceAddOrRemove.this, android.R.layout.simple_spinner_item,AvailableServices);
                    branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    ServiceChooser.setAdapter(branchAdapter);
                }
            });
            dbAccess.getAvailableServices();
            dbAccess.getBranchServices(new DatabaseReader.BranchServices() {
                @Override
                public void getBranchServices(ArrayList<String> services) {
                    viewList = services;
                    ArrayAdapter<String> viewAdapater = new ArrayAdapter<String>(ServiceAddOrRemove.this,android.R.layout.simple_list_item_1,viewList);
                    ServiceView.setAdapter(viewAdapater);
                }
            });
            dbAccess.getServicesOfBranch(getIntent().getStringExtra("Account Name"));
        }
        AddServiceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getStringExtra("Account Type").equals("Admin")){
                    dbWrite.serviceEntry(ServiceChooser.getSelectedItem().toString());
                    dbAccess.getAvailableServices();
                } else if (getIntent().getStringExtra("Account Type").equals("Branch")){
                    dbWrite.addService(getIntent().getStringExtra("Account Name"),ServiceChooser.getSelectedItem().toString());
                    dbAccess.getServicesOfBranch(getIntent().getStringExtra("Account Name"));
                }
            }
        });
        ServiceView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getIntent().getStringExtra("Account Type").equals("Admin")){
                    dbWrite.deleteService((String) ServiceView.getItemAtPosition(position));
                    AvailableServices.remove(position);
                    dbAccess.getAvailableServices();
                }else if (getIntent().getStringExtra("Account Type").equals("Branch")){
                    dbWrite.removeService(getIntent().getStringExtra("Account Name"),(String) ServiceView.getItemAtPosition(position));
                    viewList.remove(position);
                    dbAccess.getServicesOfBranch(getIntent().getStringExtra("Account Name"));
                }

            }
        });
    }
}