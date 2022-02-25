package novigrad.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import novigrad.R;
import novigrad.database.DatabaseReader;
import novigrad.database.DatabaseWriter;
import novigrad.database.ServiceRequestAdapter;
import novigrad.services.ServiceRequest;

public class ServiceRequestAcceptReject extends AppCompatActivity {

    Button accept, reject;
    ListView serviceAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request_accept_reject);
        DatabaseReader dbAccess = new DatabaseReader();
        final DatabaseWriter dbWriter = new DatabaseWriter();


        serviceAR = findViewById(R.id.slist);


        dbAccess.setServiceRequests(new DatabaseReader.ServiceRequests() {
            @Override
            public void getRequests(final ArrayList<ServiceRequest> requests) {
                final ServiceRequestAdapter requestAdapter = new ServiceRequestAdapter(ServiceRequestAcceptReject.this,requests);
                serviceAR.setAdapter(requestAdapter);
                serviceAR.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        final int which_item = position;
                        AlertDialog.Builder ADBuild = new AlertDialog.Builder(ServiceRequestAcceptReject.this);
                        View mView = getLayoutInflater().inflate(R.layout.request_dialog,null);
                        TextView FN = mView.findViewById(R.id.firstNameReplace);
                        TextView LN = mView.findViewById(R.id.lastNameReplace);
                        TextView DB = mView.findViewById(R.id.DOBRelace);
                        TextView AD = mView.findViewById(R.id.ADDRReplace);
                        TextView TP = mView.findViewById(R.id.requestTypeReplace);
                        FN.setText(requests.get(position).getFirst_name());
                        LN.setText(requests.get(position).getLast_name());
                        DB.setText(requests.get(position).getDate_of_birth());
                        AD.setText(requests.get(position).getAddress());
                        if (requests.get(position).getType().equals("License")){TP.setText(requests.get(position).getType()+requests.get(position).getLicenseType());}
                        else{TP.setText(requests.get(position).getType());}

                        ADBuild.setView(mView);
                        AlertDialog dialog = ADBuild.create();
                        dialog.show();
                        System.out.println("Yippie");
                        return true;
                    }
                });
                serviceAR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final int which_item = position;

                        new AlertDialog.Builder(ServiceRequestAcceptReject.this)
                                .setIcon(android.R.drawable.ic_delete).setTitle("You Are About To Decline A Request").setMessage("Decline Request")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dbWriter.deleteRequest(requests.get(which_item).getId(),getIntent().getStringExtra("Account Name"));
                                        requests.remove(which_item);
                                        requestAdapter.notifyDataSetChanged();
                                    }
                                }).setNegativeButton("No",null).show();
                    }
                });
            }
        });

        dbAccess.getServiceRequests(getIntent().getStringExtra("Account Name"));
    }
}




